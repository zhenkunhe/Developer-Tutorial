/*
 * LIRC base driver
 *
 * by Artur Lipowski <alipowski@interia.pl>
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/sched.h>
#include <linux/errno.h>
#include <linux/ioctl.h>
#include <linux/fs.h>
#include <linux/poll.h>
#include <linux/completion.h>
#include <linux/mutex.h>
#include <linux/wait.h>
#include <linux/unistd.h>
#include <linux/kthread.h>
#include <linux/bitops.h>
#include <linux/device.h>
#include <linux/cdev.h>

#include <media/rc-core.h>
#include <media/lirc.h>
#include <media/lirc_dev.h>

static bool debug;

#define IRCTL_DEV_NAME	"BaseRemoteCtl"
#define NOPLUG		-1
#define LOGHEAD		"lirc_dev (%s[%d]): "

static dev_t lirc_base_dev;

struct irctl {
	struct lirc_driver d;
	int attached;
	int open; // flag 用來check該irctl是否正在被open?

	struct mutex irctl_lock;
	struct lirc_buffer *buf;
	unsigned int chunk_size;

	struct cdev *cdev;

	struct task_struct *task;
	long jiffies_to_wait;
};

static DEFINE_MUTEX(lirc_dev_lock);

static struct irctl *irctls[MAX_IRCTL_DEVICES];

/* Only used for sysfs but defined to void otherwise */
static struct class *lirc_class;

/*  helper function
 *  initializes the irctl structure
 */
static void lirc_irctl_init(struct irctl *ir)
{
	mutex_init(&ir->irctl_lock);
	ir->d.minor = NOPLUG;
}

static void lirc_irctl_cleanup(struct irctl *ir)
{
	dev_dbg(ir->d.dev, LOGHEAD "cleaning up\n", ir->d.name, ir->d.minor);

	device_destroy(lirc_class, MKDEV(MAJOR(lirc_base_dev), ir->d.minor));

	if (ir->buf != ir->d.rbuf) {
		lirc_buffer_free(ir->buf);
		kfree(ir->buf);
	}
	ir->buf = NULL;
}

/*  helper function
 *  reads key codes from driver and puts them into buffer
 *  returns 0 on success
 */
static int lirc_add_to_buf(struct irctl *ir)
{
	// 找不到哪裡有設定 d.add_to_buf
	if (ir->d.add_to_buf) {
		int res = -ENODATA;
		int got_data = 0;

		/*
		 * service the device as long as it is returning
		 * data and we have space
		 */
get_data:
		res = ir->d.add_to_buf(ir->d.data, ir->buf);
		if (res == 0) {
			got_data++;
			goto get_data;
		}

		if (res == -ENODEV)
			kthread_stop(ir->task);

		return got_data ? 0 : res;
	}

	return 0;
}



/* main function of the polling thread
 */
// by onionys
// 看上面這行，好像很重要似的。
// lirc_thread 會在註冊lirc_driver的時候開始run
// 這裡會出現一個問題，就是多執行緒重覆call這個function
// 會發生什麼事?
// 據說這個是個叫做"reentrant"的問題...
// reentrant function 的設計不大簡單....
static int lirc_thread(void *irctl)
{
	struct irctl *ir = irctl;

	dev_dbg(ir->d.dev, LOGHEAD "poll thread started\n",
		ir->d.name, ir->d.minor);

	// 這裡就是主迴圈啦...
	do {
		if (ir->open) {
			// 如果指定的irctl 裝置正在被User Space open
			// 就檢查有沒有設定jiffies_to_wait值--> 每隔一陣子檢查電位值
			if (ir->jiffies_to_wait) {
				// 有設定
				set_current_state(TASK_INTERRUPTIBLE);
				schedule_timeout(ir->jiffies_to_wait); 
				// 會在這裡進入sleep並在jiffies_to_wait的時間之後再從這裡開始run
			}

			if (kthread_should_stop())
				break;

			// 從sleep醒來之後做lirc_add_to_buf()
			if (!lirc_add_to_buf(ir))
				wake_up_interruptible(&ir->buf->wait_poll);
		} else {
			set_current_state(TASK_INTERRUPTIBLE);
			schedule();
		}
	} while (!kthread_should_stop());

	dev_dbg(ir->d.dev, LOGHEAD "poll thread ended\n",
		ir->d.name, ir->d.minor);

	return 0;
}


static const struct file_operations lirc_dev_fops = {
	.owner		= THIS_MODULE,
	.read		= lirc_dev_fop_read,
	.write		= lirc_dev_fop_write,
	.poll		= lirc_dev_fop_poll,
	.unlocked_ioctl	= lirc_dev_fop_ioctl,
#ifdef CONFIG_COMPAT
	.compat_ioctl	= lirc_dev_fop_ioctl,
#endif
	.open		= lirc_dev_fop_open,
	.release	= lirc_dev_fop_close,
	.llseek		= noop_llseek,
};

static int lirc_cdev_add(struct irctl *ir)
{
	int retval = -ENOMEM;
	struct lirc_driver *d = &ir->d;
	struct cdev *cdev;

	cdev = kzalloc(sizeof(*cdev), GFP_KERNEL);
	if (!cdev)
		goto err_out;

	if (d->fops) {
		cdev_init(cdev, d->fops);
		cdev->owner = d->owner;
	} else {
		cdev_init(cdev, &lirc_dev_fops);
		cdev->owner = THIS_MODULE;
	}
	retval = kobject_set_name(&cdev->kobj, "lirc%d", d->minor);
	if (retval)
		goto err_out;

	retval = cdev_add(cdev, MKDEV(MAJOR(lirc_base_dev), d->minor), 1);
	if (retval) {
		kobject_put(&cdev->kobj);
		goto err_out;
	}

	ir->cdev = cdev;

	return 0;

err_out:
	kfree(cdev);
	return retval;
}


// 註冊lirc_driver 的工具API，非常的複雜
int lirc_register_driver(struct lirc_driver *d)
{
	// onionys
	// 其實最重要的還是這個irctl 裝置，不過對於使用lirc_dev framework的使用者
	// 而言，不用理會這東西，只是在該framework裡面是個很重要的東西
	struct irctl *ir;
	int minor;
	int bytes_in_key;
	unsigned int chunk_size;
	unsigned int buffer_size;
	int err;

	// 先是一連串的檢查，看看使用者傳進來的 lirc_driver 完不完整
	// 不完就就 OUT!
	if (!d) {
		// 傳個NULL指標進來，是來開玩笑的嗎?!
		// REJECT!
		printk(KERN_ERR "lirc_dev: lirc_register_driver: "
		       "driver pointer must be not NULL!\n");
		err = -EBADRQC;
		goto out;
	}

	if (!d->dev) {
		// 傳進來的lirc_driver 沒有附帶字元裝置，你叫我怎麼幫你申請
		// char device ?
		// REJECT !!
		printk(KERN_ERR "%s: dev pointer not filled in!\n", __func__);
		err = -EINVAL;
		goto out;
	}

	if (MAX_IRCTL_DEVICES <= d->minor) {
		// 你申請了太多的ir裝置，超過本framework 能負荷的
		// 當我資源太多? REJECT!!
		dev_err(d->dev, "lirc_dev: lirc_register_driver: "
			"\"minor\" must be between 0 and %d (%d)!\n",
			MAX_IRCTL_DEVICES - 1, d->minor);
		err = -EBADRQC;
		goto out;
	}

	if (1 > d->code_length || (BUFLEN * 8) < d->code_length) {
		// code_length 的值是lirc_driver 接收的ir code 的bit 數長度(不是很確定)
		// 準備的code_length 在範圍之外，不行 REJECT !!
		dev_err(d->dev, "lirc_dev: lirc_register_driver: "
			"code length in bits for minor (%d) "
			"must be less than %d!\n",
			d->minor, BUFLEN * 8);
		err = -EBADRQC;
		goto out;
	}

	dev_dbg(d->dev, "lirc_dev: lirc_register_driver: sample_rate: %d\n",
		d->sample_rate);
	// check 有沒設定sample_rate
	// 這裡有三種設定方式:
	//     1. 有設sample_rate --> 要實作add_to_buf()
	//     2. 沒有設sample_rate --> 要實作driver.fops.read()
	//     3. 沒有設sample_rate 也沒有實作driver.fops.read
	if (d->sample_rate) {
	// 1. 有設sample_rate --> 要實作add_to_buf()
		// 先確定 sample_rate的值沒有亂設
		if (2 > d->sample_rate || HZ < d->sample_rate) {
			dev_err(d->dev, "lirc_dev: lirc_register_driver: "
				"sample_rate must be between 2 and %d!\n", HZ);
			err = -EBADRQC;
			goto out;
		}
		// 如果有設sample_rate的話才需要另外設定add_to_buf
		if (!d->add_to_buf) {
			dev_err(d->dev, "lirc_dev: lirc_register_driver: "
				"add_to_buf cannot be NULL when "
				"sample_rate is set\n");
			err = -EBADRQC;
			goto out;
		}
		// 有sample_rate, 也有實作add_to_buf --> PASS !!!!
	} else if (!(d->fops && d->fops->read) && !d->rbuf) {
	// 2. 沒有設sample_rate 也沒有實作 driver.fops.read() 也沒有d->rbuf
	// ==> REJECT!!!!
		dev_err(d->dev, "lirc_dev: lirc_register_driver: "
			"fops->read and rbuf cannot all be NULL!\n");
		err = -EBADRQC;
		goto out;
	} else if (!d->rbuf) {
	// 3. 沒有設sample_rate 也沒有實作driver.fops.read
	// 但是有實作fops --> PASS!!!!
		if (!(d->fops && d->fops->read && d->fops->poll &&
		      d->fops->unlocked_ioctl)) {
			dev_err(d->dev, "lirc_dev: lirc_register_driver: "
				"neither read, poll nor unlocked_ioctl can be NULL!\n");
			err = -EBADRQC;
			goto out;
		}
	}
	// 到這邊會確認二次開發者實作的lirc_driver 是否有做到下面三個要求中的任一個。
	//   有設sample_rate和add_to_buf --> PASS!!
	//   沒有設sample_rate但是有實作 fop.read 並且 有 d->rbuf --> PASS!!!
	//   沒有設sample_rate但是有實作fop的所有功能，就算沒有d->rbuf  --> PASS!!


	// 不知道為何要在這邊 mutex lock住?
	// 是因為有用到local variable 嗎? -- onionys
	mutex_lock(&lirc_dev_lock);

	minor = d->minor;

	if (minor < 0) {
		/* find first free slot for driver */
		for (minor = 0; minor < MAX_IRCTL_DEVICES; minor++)
			if (!irctls[minor])
				break;
		if (MAX_IRCTL_DEVICES == minor) {
			dev_err(d->dev, "lirc_dev: lirc_register_driver: "
				"no free slots for drivers!\n");
			err = -ENOMEM;
			goto out_lock;
		}
	} else if (irctls[minor]) {
		dev_err(d->dev, "lirc_dev: lirc_register_driver: "
			"minor (%d) just registered!\n", minor);
		err = -EBUSY;
		goto out_lock;
	}

	ir = kzalloc(sizeof(struct irctl), GFP_KERNEL);
	if (!ir) {
		err = -ENOMEM;
		goto out_lock;
	}
	lirc_irctl_init(ir);
	irctls[minor] = ir;
	d->minor = minor;

	if (d->sample_rate) {
		// HZ代表一秒有幾個jiffie數, 所以這裡是算
		// 每過幾個jiffies就要去取一次時間值。
		ir->jiffies_to_wait = HZ / d->sample_rate;
	} else {
		/* it means - wait for external event in task queue */
		// 上面這行是說，使用外部event觸發的方式來做取樣? 
		// 如果取樣頻率設為0
		ir->jiffies_to_wait = 0;
	}

	/* some safety check 8-) */
	d->name[sizeof(d->name)-1] = '\0';

	bytes_in_key = BITS_TO_LONGS(d->code_length) +
			(d->code_length % 8 ? 1 : 0);
	buffer_size = d->buffer_size ? d->buffer_size : BUFLEN / bytes_in_key;
	chunk_size  = d->chunk_size  ? d->chunk_size  : bytes_in_key;

	if (d->rbuf) {
		ir->buf = d->rbuf;
	} else {
		ir->buf = kmalloc(sizeof(struct lirc_buffer), GFP_KERNEL);
		if (!ir->buf) {
			err = -ENOMEM;
			goto out_lock;
		}
		err = lirc_buffer_init(ir->buf, chunk_size, buffer_size);
		if (err) {
			kfree(ir->buf);
			goto out_lock;
		}
	}
	ir->chunk_size = ir->buf->chunk_size;

	if (d->features == 0)
		d->features = LIRC_CAN_REC_LIRCCODE;

	ir->d = *d;

	device_create(lirc_class, ir->d.dev,
		      MKDEV(MAJOR(lirc_base_dev), ir->d.minor), NULL,
		      "lirc%u", ir->d.minor);

	// 
	if (d->sample_rate) {
		/* try to fire up polling thread */
		// kthread_run
		ir->task = kthread_run(lirc_thread, (void *)ir, "lirc_dev");
		if (IS_ERR(ir->task)) {
			dev_err(d->dev, "lirc_dev: lirc_register_driver: "
				"cannot run poll thread for minor = %d\n",
				d->minor);
			err = -ECHILD;
			goto out_sysfs;
		}
	}

	// 註冊lirc_driver的時候就順便註冊了一個字元驅動裝置
	//
	err = lirc_cdev_add(ir);
	if (err)
		goto out_sysfs;

	// 程式跑到這，就表示宣告了一個可用的irctl 物件。
	//  -- irctl 在註冊 lirc_driver 的時候由框架自動產生
	ir->attached = 1;    // 這個attached 像是一個產測標簽?設1表示可以出貨了?
	mutex_unlock(&lirc_dev_lock);

	dev_info(ir->d.dev, "lirc_dev: driver %s registered at minor = %d\n",
		 ir->d.name, ir->d.minor);
	return minor;

out_sysfs:
	device_destroy(lirc_class, MKDEV(MAJOR(lirc_base_dev), ir->d.minor));
out_lock:
	mutex_unlock(&lirc_dev_lock);
out:
	return err;
}
EXPORT_SYMBOL(lirc_register_driver);
// 這個是要給那些喜憨的Framework 使用者用的API
// 所以要export 出去




// 
int lirc_unregister_driver(int minor)
{
	struct irctl *ir;
	struct cdev *cdev;

	if (minor < 0 || minor >= MAX_IRCTL_DEVICES) {
		printk(KERN_ERR "lirc_dev: %s: minor (%d) must be between "
		       "0 and %d!\n", __func__, minor, MAX_IRCTL_DEVICES - 1);
		return -EBADRQC;
	}

	ir = irctls[minor];
	if (!ir) {
		printk(KERN_ERR "lirc_dev: %s: failed to get irctl struct "
		       "for minor %d!\n", __func__, minor);
		return -ENOENT;
	}

	cdev = ir->cdev;

	mutex_lock(&lirc_dev_lock);

	if (ir->d.minor != minor) {
		printk(KERN_ERR "lirc_dev: %s: minor (%d) device not "
		       "registered!\n", __func__, minor);
		mutex_unlock(&lirc_dev_lock);
		return -ENOENT;
	}

	/* end up polling thread */
	if (ir->task)
		kthread_stop(ir->task);

	dev_dbg(ir->d.dev, "lirc_dev: driver %s unregistered from minor = %d\n",
		ir->d.name, ir->d.minor);

	ir->attached = 0;
	if (ir->open) {
		dev_dbg(ir->d.dev, LOGHEAD "releasing opened driver\n",
			ir->d.name, ir->d.minor);
		wake_up_interruptible(&ir->buf->wait_poll);
		mutex_lock(&ir->irctl_lock);
		ir->d.set_use_dec(ir->d.data);
		module_put(cdev->owner);
		mutex_unlock(&ir->irctl_lock);
	} else {
		lirc_irctl_cleanup(ir);
		cdev_del(cdev);
		kfree(cdev);
		kfree(ir);
		irctls[minor] = NULL;
	}

	mutex_unlock(&lirc_dev_lock);

	return 0;
}
EXPORT_SYMBOL(lirc_unregister_driver);

// 動態: open device 時一切動作的進入點...
// 這個Framework 平時不會占用irq資源，就算使用者已經把lirc_driver註冊進來了也是一樣
// lirc_driver的註冊只是產生一個char dev做為窗口給使User Space 的使用者
// 當User Space 的使用者open這個char dev 時，Framework才會去申請irq資源。
// 聽說這個手法是叫做lazy loading 的一種 Design Pattern
int lirc_dev_fop_open(struct inode *inode, struct file *file)
{
	// 這個時候，什麼都不知道，頂多可以從inode知道major minor number
	struct irctl *ir;
	struct cdev *cdev;
	int retval = 0;

	if (iminor(inode) >= MAX_IRCTL_DEVICES) {
		printk(KERN_WARNING "lirc_dev [%d]: open result = -ENODEV\n",
		       iminor(inode));
		return -ENODEV;
	}

	// 這裡的mutex_lock還不太清楚是在搞什麼...
	if (mutex_lock_interruptible(&lirc_dev_lock))
		return -ERESTARTSYS;

	// open 之後只能從介面得到major minor number
	// 想要得到對應的 irctl 物件靠該lirc_dev Framework 維護的 irctls list
	// 這裡刻意設計成該list 的index 對應minor number
	// 所以我們有minor number 就可以取得對應的irctl 物件
	// 而minor number的最大值，在申請char dev 的時候就應該決定了()
	ir = irctls[iminor(inode)];
	if (!ir) {
		retval = -ENODEV;
		goto error;
	}

	dev_dbg(ir->d.dev, LOGHEAD "open called\n", ir->d.name, ir->d.minor);

	if (ir->d.minor == NOPLUG) {
		retval = -ENODEV;
		goto error;
	}

	// 照理說此時的ir->open其值應該為0
	// 表示說ir->open 是一個 flag 用來check該irctl是否正在被open?
	if (ir->open) {
		retval = -EBUSY;
		goto error;
	}

	// check 有沒有人正在open該irctl的字元驅動裝置
	// 一次只能有一個人open
	if (ir->d.rdev) {
		// rc_open 看起來像是取得目前該字元驅動程式正在被幾個人open
		retval = rc_open(ir->d.rdev);
		// 如果有人正在open 該字元裝置...就是error
		if (retval)
			// 會到這個地步完全是因為open失敗，所以直接到error點
			goto error;
	}

	cdev = ir->cdev;
	// 把該模組的使用計數加一，可以防止該模組在使用中的時候不小心被別人卸載
	if (try_module_get(cdev->owner)) {
		// 指定使用的irctl 的 open計數加一
		ir->open++;
		// 這個是由二次開發的人來做的--要負責寫出 lirc_rpi.c
		//
		// set_use_inc() 做了什麼? 向kernel 申請了一個irq 開始處理IR 訊號的觸發
		// 並設定IR訊號的時間參數, 成功就回傳0，失敗了retval 會有值
		retval = ir->d.set_use_inc(ir->d.data);

		if (retval) {
			// set_use_inc() 申請失敗，模組使用計數減一
			module_put(cdev->owner);
			// ir->open 是這個irctl裝置是否被人打開使用的flag
			// 因為申請失敗，所以減一
			ir->open--;
		} else {
			// set_use_inc() 申請成功，清除ir 裡面的lirc_buffer的資料
			lirc_buffer_clear(ir->buf);
		}

		// ir->task 好像一直是NULL
		// ir->task 是在lirc_rpi.c裡面設定的，Framework 使用者的事
		// 因為這行就算是沒有設定task，程式也會往下跑
		if (ir->task)
			wake_up_process(ir->task);
	}

error:
	if (ir)
		dev_dbg(ir->d.dev, LOGHEAD "open result = %d\n",
			ir->d.name, ir->d.minor, retval);

	mutex_unlock(&lirc_dev_lock);

	nonseekable_open(inode, file);

	return retval;
}
EXPORT_SYMBOL(lirc_dev_fop_open);

int lirc_dev_fop_close(struct inode *inode, struct file *file)
{
	struct irctl *ir = irctls[iminor(inode)];
	struct cdev *cdev;

	if (!ir) {
		printk(KERN_ERR "%s: called with invalid irctl\n", __func__);
		return -EINVAL;
	}

	cdev = ir->cdev;

	dev_dbg(ir->d.dev, LOGHEAD "close called\n", ir->d.name, ir->d.minor);

	WARN_ON(mutex_lock_killable(&lirc_dev_lock));

	if (ir->d.rdev)
		rc_close(ir->d.rdev);

	ir->open--;
	if (ir->attached) {
		ir->d.set_use_dec(ir->d.data);
		module_put(cdev->owner);
	} else {
		lirc_irctl_cleanup(ir);
		cdev_del(cdev);
		irctls[ir->d.minor] = NULL;
		kfree(cdev);
		kfree(ir);
	}

	mutex_unlock(&lirc_dev_lock);

	return 0;
}
EXPORT_SYMBOL(lirc_dev_fop_close);

unsigned int lirc_dev_fop_poll(struct file *file, poll_table *wait)
{
	struct irctl *ir = irctls[iminor(file_inode(file))];
	unsigned int ret;

	if (!ir) {
		printk(KERN_ERR "%s: called with invalid irctl\n", __func__);
		return POLLERR;
	}

	dev_dbg(ir->d.dev, LOGHEAD "poll called\n", ir->d.name, ir->d.minor);

	if (!ir->attached)
		return POLLERR;

	poll_wait(file, &ir->buf->wait_poll, wait);

	if (ir->buf)
		if (lirc_buffer_empty(ir->buf))
			ret = 0;
		else
			ret = POLLIN | POLLRDNORM;
	else
		ret = POLLERR;

	dev_dbg(ir->d.dev, LOGHEAD "poll result = %d\n",
		ir->d.name, ir->d.minor, ret);

	return ret;
}
EXPORT_SYMBOL(lirc_dev_fop_poll);

long lirc_dev_fop_ioctl(struct file *file, unsigned int cmd, unsigned long arg)
{
	__u32 mode;
	int result = 0;
	struct irctl *ir = irctls[iminor(file_inode(file))];

	if (!ir) {
		printk(KERN_ERR "lirc_dev: %s: no irctl found!\n", __func__);
		return -ENODEV;
	}

	dev_dbg(ir->d.dev, LOGHEAD "ioctl called (0x%x)\n",
		ir->d.name, ir->d.minor, cmd);

	if (ir->d.minor == NOPLUG || !ir->attached) {
		dev_dbg(ir->d.dev, LOGHEAD "ioctl result = -ENODEV\n",
			ir->d.name, ir->d.minor);
		return -ENODEV;
	}

	mutex_lock(&ir->irctl_lock);

	switch (cmd) {
	case LIRC_GET_FEATURES:
		result = put_user(ir->d.features, (__u32 __user *)arg);
		break;
	case LIRC_GET_REC_MODE:
		if (!(ir->d.features & LIRC_CAN_REC_MASK)) {
			result = -ENOSYS;
			break;
		}

		result = put_user(LIRC_REC2MODE
				  (ir->d.features & LIRC_CAN_REC_MASK),
				  (__u32 __user *)arg);
		break;
	case LIRC_SET_REC_MODE:
		if (!(ir->d.features & LIRC_CAN_REC_MASK)) {
			result = -ENOSYS;
			break;
		}

		result = get_user(mode, (__u32 __user *)arg);
		if (!result && !(LIRC_MODE2REC(mode) & ir->d.features))
			result = -EINVAL;
		/*
		 * FIXME: We should actually set the mode somehow but
		 * for now, lirc_serial doesn't support mode changing either
		 */
		break;
	case LIRC_GET_LENGTH:
		result = put_user(ir->d.code_length, (__u32 __user *)arg);
		break;
	case LIRC_GET_MIN_TIMEOUT:
		if (!(ir->d.features & LIRC_CAN_SET_REC_TIMEOUT) ||
		    ir->d.min_timeout == 0) {
			result = -ENOSYS;
			break;
		}

		result = put_user(ir->d.min_timeout, (__u32 __user *)arg);
		break;
	case LIRC_GET_MAX_TIMEOUT:
		if (!(ir->d.features & LIRC_CAN_SET_REC_TIMEOUT) ||
		    ir->d.max_timeout == 0) {
			result = -ENOSYS;
			break;
		}

		result = put_user(ir->d.max_timeout, (__u32 __user *)arg);
		break;
	default:
		result = -EINVAL;
	}

	dev_dbg(ir->d.dev, LOGHEAD "ioctl result = %d\n",
		ir->d.name, ir->d.minor, result);

	mutex_unlock(&ir->irctl_lock);

	return result;
}
EXPORT_SYMBOL(lirc_dev_fop_ioctl);

ssize_t lirc_dev_fop_read(struct file *file,
			  char __user *buffer,
			  size_t length,
			  loff_t *ppos)
{
	struct irctl *ir = irctls[iminor(file_inode(file))];
	unsigned char *buf;
	int ret = 0, written = 0;
	DECLARE_WAITQUEUE(wait, current);

	if (!ir) {
		printk(KERN_ERR "%s: called with invalid irctl\n", __func__);
		return -ENODEV;
	}

	dev_dbg(ir->d.dev, LOGHEAD "read called\n", ir->d.name, ir->d.minor);

	buf = kzalloc(ir->chunk_size, GFP_KERNEL);
	if (!buf)
		return -ENOMEM;

	if (mutex_lock_interruptible(&ir->irctl_lock)) {
		ret = -ERESTARTSYS;
		goto out_unlocked;
	}
	if (!ir->attached) {
		ret = -ENODEV;
		goto out_locked;
	}

	if (length % ir->chunk_size) {
		ret = -EINVAL;
		goto out_locked;
	}

	/*
	 * we add ourselves to the task queue before buffer check
	 * to avoid losing scan code (in case when queue is awaken somewhere
	 * between while condition checking and scheduling)
	 */
	add_wait_queue(&ir->buf->wait_poll, &wait);
	set_current_state(TASK_INTERRUPTIBLE);

	/*
	 * while we didn't provide 'length' bytes, device is opened in blocking
	 * mode and 'copy_to_user' is happy, wait for data.
	 */
	while (written < length && ret == 0) {
		if (lirc_buffer_empty(ir->buf)) {
			/* According to the read(2) man page, 'written' can be
			 * returned as less than 'length', instead of blocking
			 * again, returning -EWOULDBLOCK, or returning
			 * -ERESTARTSYS */
			if (written)
				break;
			if (file->f_flags & O_NONBLOCK) {
				ret = -EWOULDBLOCK;
				break;
			}
			if (signal_pending(current)) {
				ret = -ERESTARTSYS;
				break;
			}

			mutex_unlock(&ir->irctl_lock);
			schedule();
			set_current_state(TASK_INTERRUPTIBLE);

			if (mutex_lock_interruptible(&ir->irctl_lock)) {
				ret = -ERESTARTSYS;
				remove_wait_queue(&ir->buf->wait_poll, &wait);
				set_current_state(TASK_RUNNING);
				goto out_unlocked;
			}

			if (!ir->attached) {
				ret = -ENODEV;
				break;
			}
		} else {
			lirc_buffer_read(ir->buf, buf);
			ret = copy_to_user((void __user *)buffer+written, buf,
					   ir->buf->chunk_size);
			if (!ret)
				written += ir->buf->chunk_size;
			else
				ret = -EFAULT;
		}
	}

	remove_wait_queue(&ir->buf->wait_poll, &wait);
	set_current_state(TASK_RUNNING);

out_locked:
	mutex_unlock(&ir->irctl_lock);

out_unlocked:
	kfree(buf);
	dev_dbg(ir->d.dev, LOGHEAD "read result = %s (%d)\n",
		ir->d.name, ir->d.minor, ret ? "<fail>" : "<ok>", ret);

	return ret ? ret : written;
}
EXPORT_SYMBOL(lirc_dev_fop_read);

void *lirc_get_pdata(struct file *file)
{
	return irctls[iminor(file_inode(file))]->d.data;
}
EXPORT_SYMBOL(lirc_get_pdata);


ssize_t lirc_dev_fop_write(struct file *file, const char __user *buffer,
			   size_t length, loff_t *ppos)
{
	struct irctl *ir = irctls[iminor(file_inode(file))];

	if (!ir) {
		printk(KERN_ERR "%s: called with invalid irctl\n", __func__);
		return -ENODEV;
	}

	dev_dbg(ir->d.dev, LOGHEAD "write called\n", ir->d.name, ir->d.minor);

	if (!ir->attached)
		return -ENODEV;

	return -EINVAL;
}
EXPORT_SYMBOL(lirc_dev_fop_write);


static int __init lirc_dev_init(void)
{
	int retval;

	lirc_class = class_create(THIS_MODULE, "lirc");
	if (IS_ERR(lirc_class)) {
		retval = PTR_ERR(lirc_class);
		printk(KERN_ERR "lirc_dev: class_create failed\n");
		goto error;
	}

	retval = alloc_chrdev_region(&lirc_base_dev, 0, MAX_IRCTL_DEVICES,
				     IRCTL_DEV_NAME);
	if (retval) {
		class_destroy(lirc_class);
		printk(KERN_ERR "lirc_dev: alloc_chrdev_region failed\n");
		goto error;
	}


	printk(KERN_INFO "lirc_dev: IR Remote Control driver registered, "
	       "major %d \n", MAJOR(lirc_base_dev));

error:
	return retval;
}



static void __exit lirc_dev_exit(void)
{
	class_destroy(lirc_class);
	unregister_chrdev_region(lirc_base_dev, MAX_IRCTL_DEVICES);
	printk(KERN_INFO "lirc_dev: module unloaded\n");
}

module_init(lirc_dev_init);
module_exit(lirc_dev_exit);

MODULE_DESCRIPTION("LIRC base driver module");
MODULE_AUTHOR("Artur Lipowski");
MODULE_LICENSE("GPL");

module_param(debug, bool, S_IRUGO | S_IWUSR);
MODULE_PARM_DESC(debug, "Enable debugging messages");

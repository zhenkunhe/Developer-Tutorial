#include <linux/module.h>
#include <linux/slab.h>
#include <linux/kobject.h>
//#include <linux/syscalls.h> 
//#include <linux/fcntl.h> 

#include <linux/kernel.h> 
#include <linux/mm.h> 
#include <linux/init.h>
#include <linux/fs.h>
#include <linux/uaccess.h>
//static char buf[] = "yy";
//static char buf1[20];

char serial_number[30];

struct my_kobj {    //内嵌kobject的结构
	int val;
	struct kobject kobj;
};

struct my_kobj *obj1;//, *obj2;
struct kset *my_kset;
struct kobj_type my_type;

struct attribute name_attr = {
	.name = "sn", //文件名称
	.mode = 0444,  //指定文件的訪问权限
};

//struct attribute val_attr = {
//	.name = "val", //文件名称
//	.mode = 0666, //指定文件的訪问权限
//};

struct attribute *my_attrs[] = {
	&name_attr, 
//	&val_attr,
	NULL,
};
/*
结构体struct attribute里的name变量用来指定文件名称，mode变量用来指定文件的訪问权限。
这里须要着重指出的是。数组my_attrs的最后一项一定要赋为NULL，否则会造成内核oops。

*/

ssize_t my_show(struct kobject *kobj, struct attribute *attr, char *buffer)
{
	struct my_kobj *obj = container_of(kobj, struct my_kobj, kobj);
	ssize_t count = 0;

	if (strcmp(attr->name, "sn") == 0) {
		//count = sprintf(buffer, "%s\n", kobject_name(kobj));
		count = sprintf(buffer, "%s\n", serial_number);
	}// else if (strcmp(attr->name, "val") == 0) {
	//	count = sprintf(buffer, "%d\n", obj->val);
	//}

	return count;
}

//ssize_t my_store(struct kobject *kobj, struct attribute *attr, const char *buffer, size_t size)
//{
//	struct my_kobj *obj = container_of(kobj, struct my_kobj, kobj);
//
//	if (strcmp(attr->name, "val") == 0) {
//		sscanf(buffer, "%d", &obj->val);
//	}
//
//	return size;
//}

struct sysfs_ops my_sysfsops = {
	.show = my_show,
	.store = NULL,
};

void obj_release(struct kobject *kobj)
{
	struct my_kobj *obj = container_of(kobj, struct my_kobj, kobj);
	printk(KERN_INFO "obj_release %s\n", kobject_name(&obj->kobj));
	kfree(obj);
}

void fileread(const char * filename)
{
    struct file *fp;
    mm_segment_t fs;
    loff_t pos;
    printk("hello enter : %s\n",filename);
    fp = filp_open(filename, O_RDONLY, 0);
    printk("file_open : %d\n",fp);
    if (IS_ERR(fp)) {
        printk("create file error\n");
        return -1;
    }
    fs = get_fs();
    set_fs(KERNEL_DS);
    //pos = 0;
    //vfs_write(fp, buf, sizeof(buf), &pos);
    pos = 0;
    int ret=0;
    ret=vfs_read(fp, serial_number, sizeof(serial_number), &pos);
    filp_close(fp, NULL);
    set_fs(fs);
    serial_number[ret]=0x0;
    printk("read : [%s] , %d, %d\n", serial_number,ret,pos);
}

static int __init mykset_init(void)
{
	printk(KERN_INFO "sn module init\n");


	fileread("/sys/class/dmi/id/product_serial");	
	//FILE *pFile;
	//pFile=fopen("/sys/class/dmi/id/product_serial","r");
	//if( NULL == pFile ){
	//	printk( "open /sys/class/dmi/id/product_serial fail" );
	//	return -ENOMEM;
	//}	
	//fclose(pFile);
	
	my_kset = kset_create_and_add("platform", NULL, NULL);
	if (!my_kset) {
		return -ENOMEM;
	}

	obj1 = kzalloc(sizeof(struct my_kobj), GFP_KERNEL);
	if (!obj1) {
		kset_unregister(my_kset);
		return -ENOMEM;
	}
	obj1->val = 1;

	//obj2 = kzalloc(sizeof(struct my_kobj), GFP_KERNEL);
	//if (!obj2) {
	//	kset_unregister(my_kset);
	//	kfree(obj1);
	//	return -ENOMEM;
	//}
	//obj2->val = 2;

	obj1->kobj.kset = my_kset;
	//obj2->kobj.kset = my_kset;

	my_type.release = obj_release;
	my_type.default_attrs = my_attrs;
	my_type.sysfs_ops = &my_sysfsops;

	kobject_init_and_add(&obj1->kobj, &my_type, NULL, "dmi_info");/*函数来初始化kobject并把它增加到设备模型的体系架构*/
	//kobject_init_and_add(&obj2->kobj, &my_type, NULL, "mykobj2");
/*
	kobject_init用来初始化kobject结构，kobject_add用来把kobj增加到设备模型之中。
	在实作中，我们先对obj1进行初始化和增加的动作。调用參数里。parent被赋为NULL。表示obj1没有父对象，反映到sysfs里。
	my_kobj1的文件夹会出如今/sys下，obj2的父对象设定为obj1，那么my_kobj2的文件夹会出如今/sys/my_kobj1以下。


	前面提到，kobject也提供了引用计数的功能，尽管本质上是利用kref，但也提供了另外的接口供用户使用。
	kobject_init_and_add和kobject_init这两个函数被调用后，kobj的引用计数会初始化为1，
	所以在module_exit时要记得用kobject_put来释放引用计数。


*/
	return 0;
}

static void __exit mykset_exit(void)
{
	printk(KERN_INFO "sn module exit\n");

	kobject_del(&obj1->kobj);/*先子对象，后父对象*/
	kobject_put(&obj1->kobj);

	//kobject_del(&obj2->kobj);
	//kobject_put(&obj2->kobj);

	kset_unregister(my_kset);

	return;
}
/*
kobject_del的作用是把kobject从设备模型的那棵树里摘掉，同一时候sysfs里对应的文件夹也会删除。
这里须要指出的是，释放的顺序应该是先子对象。后父对象。


由于kobject_init_and_add和kobject_add这两个函数会调用kobject_get来增加父对象的引用计数，
所以kobject_del须要调用kobject_put来降低父对象的引用计数。在本例中，假设先通过kobject_put来释放obj1。
那kobject_del(&obj2->kobj)就会出现内存错误。
*/

module_init(mykset_init);
module_exit(mykset_exit);

MODULE_LICENSE("GPL");

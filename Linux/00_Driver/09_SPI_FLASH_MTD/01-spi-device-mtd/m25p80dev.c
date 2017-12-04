#include <linux/init.h>
#include <linux/device.h>
#include <linux/dma-mapping.h>
#include <linux/serial_8250.h>
#include <linux/platform_device.h>
#include <linux/syscore_ops.h>
#include <linux/interrupt.h>
#include <linux/amba/bus.h>
#include <linux/amba/clcd.h>
#include <linux/clockchips.h>
#include <linux/cnt32_to_63.h>
#include <linux/io.h>
#include <linux/module.h>
#include <linux/spi/spi.h>
#include <linux/w1-gpio.h>
#include <linux/pps-gpio.h>

#include <linux/version.h>
#include <linux/clkdev.h>
//#include <asm/system.h>
#include <mach/hardware.h>
#include <asm/irq.h>
#include <linux/leds.h>
#include <asm/mach-types.h>
#include <linux/sched_clock.h>

#include <asm/mach/arch.h>
//#include <asm/mach/flash.h>
#include <asm/mach/irq.h>
#include <asm/mach/time.h>
#include <asm/mach/map.h>

#include <mach/timex.h>
//#include <mach/dma.h>
//#include <mach/vcio.h>
#include <mach/system.h>

#include <linux/delay.h>

#include <linux/mtd/cfi.h>
#include <linux/mtd/mtd.h>
#include <linux/mtd/partitions.h>
#include<linux/spi/flash.h>

#if 1
  #define dprint(fmt,s...) printk("m25p80dev_my: %s,%d:"fmt,__FUNCTION__,__LINE__,##s)
#else
  #define dprint(fmt,s...)
#endif  

struct spi_master * my_spi_master = NULL;
struct spi_device * my_spi_device = NULL;

static struct mtd_partition partition_info[] = {
//	{
//		.name    = "Boot Strap",
//		.offset  =  0x0,
//		.size    =  0x100000,
//	},
//	{
//		.name    = "ARM-BOOT",
//		.offset  = 0x100000,
//		.size    = 0x100000,
//	},
	{
		.name    = "DATA1",
		.offset  = 0x0,
		.size    = 0x40000,
	},
	{
		.name     = "DATA2",
		.offset   = 0x40000,
		.size     = 0x40000,
	},
};

static struct flash_platform_data spi_flashdata = { 
	.name="mx25l4005a",
	.type="mx25l4005a",
	.parts=partition_info,
	.nr_parts=ARRAY_SIZE(partition_info),
};


static struct spi_board_info my_spi_board[] = {
	  [0] = 
	{ 
		.modalias = "m25p80", 
		.max_speed_hz = 500000, 
		.platform_data = &spi_flashdata,         
		.bus_num = 0,
		.chip_select = 0,                  
		.mode = SPI_MODE_0,
	}, 

}; 


static int __init m25p80_spi_dev_init(void)
{
	my_spi_master = spi_busnum_to_master(0);
	if(my_spi_master == NULL){
		printk("Request spi master 0 failed!!\n");
		return -1;
	}
	  
	my_spi_device = spi_new_device(my_spi_master, my_spi_board);	
	put_device(&my_spi_master->dev);
	if(!my_spi_device){
		printk("spi_new_device() returned NULL\n");
		return -1;
	}
		
	dprint("init success \n");
	return 0;
}

static void __exit m25p80_spi_dev_exit(void)
{
	if (my_spi_device) {
		device_del(&my_spi_device->dev);
		kfree(my_spi_device);
	}
  
	dprint(" exit module: bye bye\n");
}

module_init(m25p80_spi_dev_init);
module_exit(m25p80_spi_dev_exit);

MODULE_LICENSE("GPL");

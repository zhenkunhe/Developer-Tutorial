// Simple Character Device Driver Module for Raspberry Pi.

#include <linux/module.h>   
#include <linux/string.h>    
#include <linux/fs.h>      
#include <asm/uaccess.h>
#include <linux/init.h>
#include <linux/cdev.h>

#include <linux/device.h>
#include <linux/kernel.h>
#include <linux/errno.h>
#include <linux/io.h>
#include <linux/sched.h>
#include <linux/interrupt.h>

#include <linux/list.h>
#include <linux/irq.h>
#include <linux/slab.h>
#include <linux/gpio.h>
#include <linux/platform_device.h>
#include <mach/platform.h>
#include <mach/gpio.h>
#include <linux/time.h>
#include <linux/delay.h>


#define MY_DUMMY_PLATFORM_DEV_NAME "my_dummy_device"

/* *********************************************
 * PLATFORM RESOURCE and DEVICE
 * location : arch/arm/mach-2708.c
 * *********************************************
 * */

// -- PLATFORM RESOURCES
static struct resource MY_DUMMY_resources[] = {
	{
		.start = NULL,
		.end = NULL,
		.flags = IORESOURCE_MEM,
	}
};

// -- PLATFORM DEVICE 
static struct platform_device MY_DUMMY_device = {
	.name = MY_DUMMY_PLATFORM_DEV_NAME,
	.id = -1,
	.num_resources = 1,
	.resource = MY_DUMMY_resources,
};



///////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////




/* *********************************************
 * PLATFORM DRIVER 
 * location : arch/driver/
 * *********************************************
 * */

// -- PROBE 
static int MY_DUMMY_probe(struct platform_device *pdev)
{
	struct resource *regs;
	//int irq = -ENOMEM;

	regs = platform_get_resource(pdev, IORESOURCE_MEM, 0);
	if (!regs) {
		dev_err(&pdev->dev, "could not get IO memory\n");
		return -ENXIO;
	}

	//irq = platform_get_irq(pdev, 0);
	//if (irq < 0) {
	//	dev_err(&pdev->dev, "could not get IRQ\n");
	//	return irq;
	//}
	return 0;
}

// -- REMOVE
static int MY_DUMMY_remove(struct platform_device *pdev)
{
	return 0;
}

static struct platform_driver MY_DUMMY_driver = {
	.driver		= {
		.name	= MY_DUMMY_PLATFORM_DEV_NAME,
		.owner	= THIS_MODULE,
	},
	.probe		= MY_DUMMY_probe,
	.remove		= MY_DUMMY_remove,
};


// -- Module INIT
static int __init MY_DUMMY_init(void)
{

	
	//
	// -- platform_device_register() is the code in the /arch/arm/mach-bcm2708.c
	//
	int ret;
	ret = platform_device_register(&MY_DUMMY_device);


	//
	// -- platform_driver_probe() is the code in the /driver/
	//
	return platform_driver_probe(&MY_DUMMY_driver, MY_DUMMY_probe);
}

static void __exit MY_DUMMY_exit(void)
{

	//
	// -- platform_driver_unregister() is the code in the /driver/
	//
	platform_driver_unregister(&MY_DUMMY_driver);

	//
	// -- platform_device_register() is the code in the /arch/arm/mach-bcm2708.c
	//
	platform_device_unregister(&MY_DUMMY_device);
}

module_init(MY_DUMMY_init);
module_exit(MY_DUMMY_exit);

//MODULE_DESCRIPTION("Platform device and driver example");
//MODULE_AUTHOR("ITTraining");
MODULE_LICENSE("GPL v2");
//MODULE_ALIAS("platform:" MY_DUMMY_PLATFORM_DEV_NAME);

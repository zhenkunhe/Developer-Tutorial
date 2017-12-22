/*************************************************************
GPIO.H
IDE: ADS 1.2
*************************************************************/

#ifndef GPIO_H
#define GPIO_H

#ifndef REG32
#define REG32(A) (*(volatile unsigned long *)(A))
#endif

#define	GPIO_BASE	0x3F200000

#define	GPIO_GPFSEL0	REG32(GPIO_BASE + 0x00)
#define	GPIO_GPFSEL1	REG32(GPIO_BASE + 0x04)
#define	GPIO_GPFSEL2	REG32(GPIO_BASE + 0x08)
#define	GPIO_GPFSEL3	REG32(GPIO_BASE + 0x0C)
#define	GPIO_GPFSEL4	REG32(GPIO_BASE + 0x10)
#define	GPIO_GPFSEL5	REG32(GPIO_BASE + 0x14)

#define	GPIO_GPSET0	REG32(GPIO_BASE + 0x1C)
#define	GPIO_GPSET1	REG32(GPIO_BASE + 0x20)

#define	GPIO_GPCLR0	REG32(GPIO_BASE + 0x28)
#define	GPIO_GPCLR1	REG32(GPIO_BASE + 0x2C)

#define	GPIO_GPLEV0	REG32(GPIO_BASE + 0x34)
#define	GPIO_GPLEV1	REG32(GPIO_BASE + 0x38)

#define	GPIO_GPEDS0	REG32(GPIO_BASE + 0x40)
#define	GPIO_GPEDS1	REG32(GPIO_BASE + 0x44)

#define	GPIO_GPREN0	REG32(GPIO_BASE + 0x4C)
#define	GPIO_GPREN1	REG32(GPIO_BASE + 0x50)

#define	GPIO_GPPEN0	REG32(GPIO_BASE + 0x58)
#define	GPIO_GPPEN1	REG32(GPIO_BASE + 0x5C)

#define	GPIO_GPHEN0	REG32(GPIO_BASE + 0x64)
#define	GPIO_GPHEN1	REG32(GPIO_BASE + 0x68)

#define	GPIO_GPLEN0	REG32(GPIO_BASE + 0x70)
#define	GPIO_GPLEN1	REG32(GPIO_BASE + 0x74)

#define	GPIO_GPAREN0	REG32(GPIO_BASE + 0x7C)
#define	GPIO_GPAREN1	REG32(GPIO_BASE + 0x80)

#define	GPIO_GPAFEN0	REG32(GPIO_BASE + 0x88)
#define	GPIO_GPAFEN1	REG32(GPIO_BASE + 0x8C)


#define	GPIO_GPPUD	REG32(GPIO_BASE + 0x94)
#define	GPIO_GPPUDCLK0	REG32(GPIO_BASE + 0x98)
#define	GPIO_GPPUDCLK1	REG32(GPIO_BASE + 0x9C)

#endif
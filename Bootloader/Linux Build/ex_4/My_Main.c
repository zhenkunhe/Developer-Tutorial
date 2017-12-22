/*
** Copyright (C) oct@mail2000.com.tw, 2004. All rights reserved.
*/

#include "gpio.h"

void My_Main(void)
{
	volatile int tmp;

	GPIO_GPFSEL4 |= (0x1 << 21);
	GPIO_GPFSEL4 &= ~(0x6 << 21); 
	// set bit 21~23 to 0b001, so GPIO 47 is an output pin
	GPIO_GPCLR1 |= (0x1 << 15); // set GPIO 47 output low (LED off)	

	while(1)
	{
		GPIO_GPSET1 |= (0x1 << 15);	// set GPIO 47 output high (LED on)
		for (tmp=0; tmp<3000000; tmp++);

		GPIO_GPCLR1 |= (0x1 << 15); 	// set GPIO 47 output low (LED off)
		for (tmp=0; tmp<3000000; tmp++);

	}

}


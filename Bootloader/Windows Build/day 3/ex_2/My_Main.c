/*
** Copyright (C) oct@mail2000.com.tw, 2004. All rights reserved.
*/

#include "gpio.h"
#include "interrupt.h"
#include "timer.h"

#define INTREVAL 1000000/2

int led_status=0;

void My_Main(void)
{
	GPIO_GPFSEL4 |= (0x1 << 21);
	GPIO_GPFSEL4 &= ~(0x6 << 21); 
	// set bit 21~23 to 0b001, so GPIO 47 is an output pin
	GPIO_GPCLR1 |= (0x1 << 15); // set GPIO 47 output low (LED off)

	INT_DISIRQ1 = 0xFFFFFFFF;
	INT_DISIRQ2 = 0xFFFFFFFF;
	INT_DISBIRQ = 0xFF;
	// disable all interrupts	

	STIMER_CTRL = 0x2;
	// clear system timer cmp1 pending
	STIMER_CMP1 = STIMER_COUNT_L + INTREVAL;
	// set interval
	
	INT_ENIRQ1 = 0x2;
	// enable system timer 1 interrupt
	
	__asm
	{
		MRS	r0, CPSR	// enable IRQ
		BIC	r0, r0, #0x80
		MSR	CPSR_c, r0
	}
	
	while(1) {}  // block here
}

void ISR_IRQ(void)
{
	if(INT_IRQ_PEND1 & 0x2) // system timer cmp1 interrupt
	{
		STIMER_CMP1 = STIMER_COUNT_L + INTREVAL;
		// set interval
		if(led_status == 0)
		{
			GPIO_GPSET1 |= (0x1 << 15);	// set GPIO 47 output high (LED on)
			led_status = 1;
		}
		else
		{
			GPIO_GPCLR1 |= (0x1 << 15); 	// set GPIO 47 output low (LED off)
			led_status = 0;
		}
		STIMER_CTRL = 0x2;
		// clear system timer cmp1 pending
	}

}

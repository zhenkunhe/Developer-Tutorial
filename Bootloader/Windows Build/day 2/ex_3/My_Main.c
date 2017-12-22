/*
** Copyright (C) oct@mail2000.com.tw, 2004. All rights reserved.
*/



void ISR_IRQ(void)
{
	*(volatile unsigned long *)0x20000C = 1; // clear interrupt
}

void My_Main(void)
{
	*(volatile unsigned long *)0x200000 = 1; // generate interrupt
	while(1) {} // block here

}



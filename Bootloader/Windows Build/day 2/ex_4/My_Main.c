/*
** Copyright (C) oct@mail2000.com.tw, 2004. All rights reserved.
*/

void ISR_FIQ(void)
{
	*(volatile unsigned long *)0x20000C = 2; // clear interrupt
}

void My_Main(void)
{
	*(volatile unsigned long *)0x200000 = 2; // generate interrupt
	while(1) {} // block here

}



/*
** Copyright (C) oct@mail2000.com.tw, 2004. All rights reserved.
*/
int SWInum = 0;

void ISR_SWI(int num)
{
	SWInum = num;
}

void My_Main(void)
{
	__asm
	{
		SWI	8
	}
	while(1) {}
}


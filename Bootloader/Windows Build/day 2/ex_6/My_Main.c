/*
** Copyright (C) oct@mail2000.com.tw, 2004. All rights reserved.
*/

int result = 0;
extern int call_to_asm(int m, int s1, int s2, int s3, int s4, int s5);

int call_by_asm(int para1, int para2, int para3, int para4, int para5, int para6)
{
	return (para1 + para2 + para3 + para4 + para5 + para6);
}

void My_Main(void)
{
	result = call_to_asm(20, 1, 2, 3, 4, 5);
	while(1) {}
}


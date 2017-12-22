/*
** Copyright (C) oct@mail2000.com.tw, 2004. All rights reserved.
*/

int my_table[64];

void My_Main(void)
{
	int i;
	int local[64];
	
	for(i=0; i<64; i++)
	{
		my_table[i] = i;
		local[i] = i+1;
		
	}

	
	while(1) {} // block here

}


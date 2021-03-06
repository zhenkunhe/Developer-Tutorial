/*
** Copyright (C) oct@mail2000.com.tw, 2004. All rights reserved.
*/

#include "mailbox.h"

int mailbox_write(unsigned int channel, unsigned long data_addr)
{
	if(!(MB_STATUS & 0x1))    // make sure mailbox is not full
	{
		__asm    
		{
			MOV	r0, #0
			MCR	p15, 0, r0, c7, c10, 5    // Data Memory barrier on ARMv6
		}
		
		MB_WRITE = ((data_addr & 0xFFFFFFF0) | (channel & 0xF));

		return 1;    // success
	}

	return 0;    // fail
}

unsigned long mailbox_read(unsigned int channel)
{
	unsigned long data;

	if(!(MB_STATUS & 0x2))    // make sure mailbox is not empty
	{
		__asm    
		{
			MOV	r0, #0
			MCR	p15, 0, r0, c7, c10, 5    // Data Memory barrier on ARMv6
		}

		data = MB_READ;

		if((data & 0xF) == channel)
			return (data & 0xFFFFFFF0);
		else
			return 0;    // fail
	}

	return 0;    // fail
}


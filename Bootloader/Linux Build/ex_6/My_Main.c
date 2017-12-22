/*
** Copyright (C) oct@mail2000.com.tw, 2004. All rights reserved.
*/

#include "gpio.h"

//#define SHOW_PHOTO	1

#ifdef SHOW_PHOTO
#include "taipei_img.h"
#endif

extern int mailbox_write(unsigned int channel, unsigned long data_addr);
extern unsigned long mailbox_read(unsigned int channel);

volatile unsigned long mailbuffer[256] __attribute__((aligned (16)));

void My_Main(void)
{
	unsigned long mail_read;
	unsigned long framebuffer_addr;
	unsigned long framebuffer_size;
	unsigned int fb_x, fb_y;
	int i;

	GPIO_GPFSEL4 |= (0x1 << 21);
	GPIO_GPFSEL4 &= ~(0x6 << 21); 
	// set bit 21~23 to 0b001, so GPIO 47 is an output pin
	GPIO_GPCLR1 |= (0x1 << 15); // set GPIO 47 output low (LED off)

	// Get the display size
	mailbuffer[0] = 8 * 4;		// header+ tags = 8x4 byttes
	mailbuffer[1] = 0;		// Request

	mailbuffer[2] = 0x00044003;	// Test physical (display) width/height
	mailbuffer[3] = 8;		// value buffer size 8
	mailbuffer[4] = 8;		// value length 8
	mailbuffer[5] = 0;		// for tutor monitor
	mailbuffer[6] = 0;		// height in pixels, GPU to answer

	mailbuffer[7] = 0;		// end of tag

	if(mailbox_write(8, (unsigned long)&mailbuffer))	// write channel 8
	{
		while((mail_read = mailbox_read(8)) == 0);
	}

	if(mailbuffer[1] != 0x80000000)
	{
		GPIO_GPSET1 |= (0x1 << 15);	// set GPIO 47 output high (LED on)
	}

	fb_x = mailbuffer[5];
	fb_y = mailbuffer[6];


	mailbuffer[0] = 22 * 4;		// header+ tage = 22*4 bytes
	mailbuffer[1] = 0;		// request

	mailbuffer[2] = 0x00048003;	// Set physical (display) width/height
	mailbuffer[3] = 8;		// value buffer size 8
	mailbuffer[4] = 8;		// value length 8
	mailbuffer[5] = fb_x;		// width = fb_x
	mailbuffer[6] = fb_y;		// height = fb_y

	mailbuffer[7] = 0x00048004;	// Set virtual (buffer) width/height
	mailbuffer[8] = 8;		// value buffer size 8
	mailbuffer[9] = 8;		// value length 8
#ifdef SHOW_PHOTO
	mailbuffer[10] = 1024;		// width = 1024;
	mailbuffer[11] = 768;		// height = 768;
#else
	mailbuffer[10] = fb_x;		// width = fb_x
	mailbuffer[11] = fb_y;		// height = fb_y
#endif

	mailbuffer[12] = 0x00048005;	// Set depth
	mailbuffer[13] = 4;		// value buffer size 4
	mailbuffer[14] = 4;		// value length 4
	mailbuffer[15] = 16;		// depth = 16 (RGB 565)

	mailbuffer[16] = 0x00040001;	// Allocate buffer
	mailbuffer[17] = 8;		// value buffer size 4
	mailbuffer[18] = 4;		// value length 4
	mailbuffer[19] = 16;		// alignment = 16
	mailbuffer[20] = 0;		// frame buffer size in bytes, GPU to answer

	mailbuffer[21] = 0;		// end of tag

	if(mailbox_write(8, (unsigned long)&mailbuffer))	// write channel 8
	{
		while((mail_read = mailbox_read(8)) == 0);
	}

	if(mailbuffer[1] != 0x80000000)
	{
		GPIO_GPSET1 |= (0x1 << 15);	// set GPIO 47 output high (LED on)
	}

	framebuffer_addr = mailbuffer[19];
	framebuffer_size = mailbuffer[20];

	if((framebuffer_addr ==0) || (framebuffer_size == 0))
	{
		GPIO_GPSET1 |= (0x1 << 15);	// set GPIO 47 output high (LED on)
	}

	for(i=0; i<(framebuffer_size/2); i++)
	{
#ifdef SHOW_PHOTO
		*(volatile unsigned short *)(framebuffer_addr + (i<<1)) = taipei_img[i];

#else
		if(i<(framebuffer_size/2)/3)
		{
			*(volatile unsigned short *)(framebuffer_addr + (i<<1)) = 0xF800; // red
		}
		else if(i<(framebuffer_size/2)*2/3)
		{
			*(volatile unsigned short *)(framebuffer_addr + (i<<1)) = 0x07E0; // green
		}
		else
		{
			*(volatile unsigned short *)(framebuffer_addr + (i<<1)) = 0x001F; // blue
		}
#endif
		
	}


	while(1) {}  // block here
}


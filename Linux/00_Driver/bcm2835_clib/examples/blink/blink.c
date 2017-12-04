// blink.c
//
// Example program for bcm2835 library
// Blinks a pin on an off every 0.5 secs
//
// After installing bcm2835, you can build this 
// with something like:
// gcc -o blink blink.c -l bcm2835
// sudo ./blink
//
// Or you can test it before installing with:
// gcc -o blink -I ../../src ../../src/bcm2835.c blink.c
// sudo ./blink
//
// Author: Mike McCauley
// Copyright (C) 2011 Mike McCauley
// $Id: RF22.h,v 1.21 2012/05/30 01:51:25 mikem Exp $

#include <bcm2835.h>

// Blinks on RPi Plug P1 pin 11 (which is GPIO pin 17)
#define LED1  27
#define LED2  22
 
int main(int argc, char **argv)
{
    // If you call this, it will not actually access the GPIO
    // Use for testing
//    bcm2835_set_debug(1);

    if (!bcm2835_init())
	return 1;

    // Set the pin to be an output
    bcm2835_gpio_fsel(LED1, BCM2835_GPIO_FSEL_OUTP);
    bcm2835_gpio_fsel(LED2, BCM2835_GPIO_FSEL_OUTP);

    // Blink
    while (1)
    {
	// Turn it on
	bcm2835_gpio_write(LED1, HIGH);
	bcm2835_gpio_write(LED2, LOW);
	
	// wait a bit
	bcm2835_delay(500);
	
	// turn it off
	bcm2835_gpio_write(LED1, LOW);
	bcm2835_gpio_write(LED2, HIGH);
	
	// wait a bit
	bcm2835_delay(500);
    }
    bcm2835_close();
    return 0;
}


#include<stdint.h>
#include<bcm2835.h>
#include<unistd.h>

#ifndef __LIB_LCD1602_H
#define __LIB_LCD1602_H
//
// BCM Pin setup
// 
#define LCD_D4    RPI_BPLUS_GPIO_J8_35 
#define LCD_D5    RPI_BPLUS_GPIO_J8_33 
#define LCD_D6    RPI_BPLUS_GPIO_J8_31 
#define LCD_D7    RPI_BPLUS_GPIO_J8_29 
#define LCD_RS    RPI_BPLUS_GPIO_J8_38 
#define LCD_RW    RPI_BPLUS_GPIO_J8_40 
#define LCD_E     RPI_BPLUS_GPIO_J8_37 

#define RW_ON     bcm2835_gpio_write(LCD_RW,HIGH)
#define RW_OFF    bcm2835_gpio_write(LCD_RW,LOW) 
#define RS_ON     bcm2835_gpio_write(LCD_RS,HIGH)
#define RS_OFF    bcm2835_gpio_write(LCD_RS,LOW) 
#define E_ON      bcm2835_gpio_write(LCD_E,HIGH) 
#define E_OFF     bcm2835_gpio_write(LCD_E,LOW)  

///////////////////////////
// FUNCTION DELCARATION
///////////////////////////
void    lcd_init();
// WRITE FUNCTION 
void    lcd_write_data(uint8_t _byte);
void    lcd_write_cmd(uint8_t _byte);
// READ FUNCTION
uint8_t lcd_read_data();
uint8_t lcd_read_busy_data();
void    lcd_wait_busy();
// PRINT CHAR, STRING ABOUT FUNCTION
void    lcd_pos(   uint8_t line, uint8_t row);
void    lcd_putc( uint8_t ch);
void    lcd_putStr(uint8_t line, uint8_t row, char *str);
void    lcd_build_self_char(uint8_t _pos, uint8_t * _ptr);
// other
void    lcd_set_pin_output();
void    lcd_set_pin_input();





///////////////////////////
// FUNCTION IMPLEMENT
///////////////////////////
void lcd_init(){

	// pin setup using BCM pin number
	bcm2835_gpio_fsel(LCD_RS,BCM2835_GPIO_FSEL_OUTP);
	bcm2835_gpio_fsel(LCD_RW,BCM2835_GPIO_FSEL_OUTP);
	bcm2835_gpio_fsel(LCD_E ,BCM2835_GPIO_FSEL_OUTP);
	bcm2835_gpio_fsel(LCD_D4,BCM2835_GPIO_FSEL_OUTP);
	bcm2835_gpio_fsel(LCD_D5,BCM2835_GPIO_FSEL_OUTP);
	bcm2835_gpio_fsel(LCD_D6,BCM2835_GPIO_FSEL_OUTP);
	bcm2835_gpio_fsel(LCD_D7,BCM2835_GPIO_FSEL_OUTP);

	RS_OFF;
	RW_OFF;
	bcm2835_gpio_write(LCD_D7, LOW);
	bcm2835_gpio_write(LCD_D6, LOW);
	bcm2835_gpio_write(LCD_D5, LOW);
	bcm2835_gpio_write(LCD_D4, LOW);
	usleep(40000);

	// START
	E_ON;  usleep(1);  E_OFF;

	// write 0010 : set the 4 bit wirte mode
	// initialization 
	// for 4 bit mode
	bcm2835_gpio_write(LCD_D5, HIGH);usleep(5);
	E_ON;  usleep(1);  E_OFF;
	usleep(6000); 

	E_ON;  usleep(1);  E_OFF;
	usleep(600); 

	E_ON;  usleep(1);  E_OFF;
	usleep(200); 
	// END initialization 

	lcd_write_cmd(0x28);
	usleep(100); // sleep for 5ms
	lcd_write_cmd(0x0c);
	usleep(100); // sleep for 5ms
	lcd_write_cmd(0x01);
	usleep(100); // sleep for 5ms
}

void lcd_set_pin_output(){
	bcm2835_gpio_fsel(LCD_D4,BCM2835_GPIO_FSEL_OUTP);
	bcm2835_gpio_fsel(LCD_D5,BCM2835_GPIO_FSEL_OUTP);
	bcm2835_gpio_fsel(LCD_D6,BCM2835_GPIO_FSEL_OUTP);
	bcm2835_gpio_fsel(LCD_D7,BCM2835_GPIO_FSEL_OUTP);
}

void lcd_set_pin_input(){
	bcm2835_gpio_fsel(LCD_D4,BCM2835_GPIO_FSEL_INPT);
	bcm2835_gpio_fsel(LCD_D5,BCM2835_GPIO_FSEL_INPT);
	bcm2835_gpio_fsel(LCD_D6,BCM2835_GPIO_FSEL_INPT);
	bcm2835_gpio_fsel(LCD_D7,BCM2835_GPIO_FSEL_INPT);
}

void lcd_write_data(uint8_t _byte){
	lcd_wait_busy();
	lcd_set_pin_output();
	// output high 4 bits
	RW_OFF; //  write mode
	RS_ON;  //  data mode

	bcm2835_gpio_write(LCD_D4, ( _byte & (1<<4) )?(HIGH):(LOW));
	bcm2835_gpio_write(LCD_D5, ( _byte & (1<<5) )?(HIGH):(LOW));
	bcm2835_gpio_write(LCD_D6, ( _byte & (1<<6) )?(HIGH):(LOW));
	bcm2835_gpio_write(LCD_D7, ( _byte & (1<<7) )?(HIGH):(LOW));
	E_ON;  usleep(2);  E_OFF;

	bcm2835_gpio_write(LCD_D4, ( _byte & (1<<0) )?(HIGH):(LOW));
	bcm2835_gpio_write(LCD_D5, ( _byte & (1<<1) )?(HIGH):(LOW));
	bcm2835_gpio_write(LCD_D6, ( _byte & (1<<2) )?(HIGH):(LOW));
	bcm2835_gpio_write(LCD_D7, ( _byte & (1<<3) )?(HIGH):(LOW));

	E_ON;  usleep(2);  E_OFF;
}

uint8_t lcd_read_data(){
	uint8_t data = 0;
	lcd_set_pin_input();
	RW_ON; RS_ON;
	usleep(1);

	E_ON; usleep(3);

	data += (( bcm2835_gpio_lev(LCD_D7) )?(1):(0)) << 7;
	data += (( bcm2835_gpio_lev(LCD_D6) )?(1):(0)) << 6;
	data += (( bcm2835_gpio_lev(LCD_D5) )?(1):(0)) << 5;
	data += (( bcm2835_gpio_lev(LCD_D4) )?(1):(0)) << 4;
	E_OFF;

	E_ON; usleep(3);
	data += (( bcm2835_gpio_lev(LCD_D7) )?(1):(0)) << 3;
	data += (( bcm2835_gpio_lev(LCD_D6) )?(1):(0)) << 2;
	data += (( bcm2835_gpio_lev(LCD_D5) )?(1):(0)) << 1;
	data += (( bcm2835_gpio_lev(LCD_D4) )?(1):(0)) << 0;
	E_OFF;
	return data;
}

void lcd_write_cmd(uint8_t _byte){
	lcd_wait_busy();
	lcd_set_pin_output();
	// output high 4 bits
	RW_OFF; //  write mode
	RS_OFF;  //  write data mode

	bcm2835_gpio_write(LCD_D4, ( _byte & (1<<4) )?(HIGH):(LOW));
	bcm2835_gpio_write(LCD_D5, ( _byte & (1<<5) )?(HIGH):(LOW));
	bcm2835_gpio_write(LCD_D6, ( _byte & (1<<6) )?(HIGH):(LOW));
	bcm2835_gpio_write(LCD_D7, ( _byte & (1<<7) )?(HIGH):(LOW));
	E_ON;  usleep(2);  E_OFF;

	bcm2835_gpio_write(LCD_D4, ( _byte & (1<<0) )?(HIGH):(LOW));
	bcm2835_gpio_write(LCD_D5, ( _byte & (1<<1) )?(HIGH):(LOW));
	bcm2835_gpio_write(LCD_D6, ( _byte & (1<<2) )?(HIGH):(LOW));
	bcm2835_gpio_write(LCD_D7, ( _byte & (1<<3) )?(HIGH):(LOW));
	E_ON;  usleep(2);  E_OFF;
}

uint8_t lcd_read_busy_data(){
	uint8_t data = 0;
	lcd_set_pin_input();
	RW_ON; RS_OFF;
	usleep(1);

	E_ON; usleep(3);
	data += ((bcm2835_gpio_lev(LCD_D7))?(1):(0)) << 7;
	data += ((bcm2835_gpio_lev(LCD_D6))?(1):(0)) << 6;
	data += ((bcm2835_gpio_lev(LCD_D5))?(1):(0)) << 5;
	data += ((bcm2835_gpio_lev(LCD_D4))?(1):(0)) << 4;
	E_OFF;

	E_ON; usleep(3);
	data += ((bcm2835_gpio_lev(LCD_D7))?(1):(0)) << 3;
	data += ((bcm2835_gpio_lev(LCD_D6))?(1):(0)) << 2;
	data += ((bcm2835_gpio_lev(LCD_D5))?(1):(0)) << 1;
	data += ((bcm2835_gpio_lev(LCD_D4))?(1):(0)) << 0;
	E_OFF;
	return data;
}

void lcd_wait_busy(){
	uint8_t data = 0;
	uint8_t mask = 1 << 7;
	while(1){
		data = lcd_read_busy_data();
		//printf("busy flag: %d\n", data);
		if( (data & mask) == 0 ) break;
		usleep(1);
	}
}

void lcd_pos(uint8_t line, uint8_t row){
	if(line == 0) lcd_write_cmd(0x80 + row);
	else if(line == 1)  lcd_write_cmd(0xc0 + row);
}

void lcd_putc(uint8_t ch){
	lcd_write_data(ch);
}

void lcd_putStr(uint8_t line, uint8_t row, char *str){
	lcd_pos(line, row);
	while(*str != '\0'){
		lcd_write_data(*str);
		str++;
	}
}

void lcd_build_self_char(uint8_t _pos, uint8_t * _ptr){
	int i ;
	if(_pos < 8){
		lcd_write_cmd(0x40 + _pos * 8);
		for( i = 0;i<8 ;i++ ){
			lcd_write_data(_ptr[i]);
		}
		lcd_write_cmd(0x80);
	}
}

#endif

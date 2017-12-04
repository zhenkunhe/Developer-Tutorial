#!/usr/bin/env python

import smbus
from time import sleep
import subprocess

LCD_I2C_ADDR = 0x3c

class jlx1602a:
	def __init__(self, slave_addr=0x3c , _dev = 1): #0 = /dev/i2c-0;  1 = /dev/i2c-1
		self.bus = smbus.SMBus(_dev)
		self.addr = slave_addr
		self.bus.write_i2c_block_data(self.addr , 0x00, [0x38 , 0x0c , 0x01, 0x06])
		sleep(0.00001)

	def printStr(self, _str):
		ch_list = [ord(i) for i in _str]
		self.bus.write_i2c_block_data(self.addr, 0x40, ch_list)
		
	def clear(self):
		self.bus.write_i2c_block_data(self.addr, 0x80, [0x01])
	
	def move(self,line,col):
		_pos = 0x80 + (line-1) * 0x40 + (col-1)
		self.bus.write_i2c_block_data(self.addr, 0x80, [_pos] )

	def putc(self, ch):
		_ch = ord(ch)
		self.bus.write_i2c_block_data(self.addr, 0x40, [_ch])


def get_eth0_addr(res):
	lines = res.split('\n')
	for i in range(lines.__len__()):
		if( 'eth0' in lines[i]):
			for j in lines[i+1].split():
				if( 'addr:' in j):
					return j.split(":")[1]
	return 'None'

if __name__ == "__main__":
	while(True):
		sleep(2.0)
		res = subprocess.check_output("ifconfig")
		lcd = jlx1602a(LCD_I2C_ADDR,1)
		lcd.move(1,2)
		lcd.printStr(get_eth0_addr(res))
		print get_eth0_addr(res)

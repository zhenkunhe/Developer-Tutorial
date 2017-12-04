#!/usr/bin/env python

import smbus
from time import sleep

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
	
	def home(self):
		self.bus.write_i2c_block_data(self.addr, 0x80, [0x02])
	
	def move(self,line,col):
		_pos = 0x80 + (line-1) * 0x40 + (col-1)
		self.bus.write_i2c_block_data(self.addr, 0x80, [_pos] )

	def putc(self, ch):
		_ch = ord(ch)
		self.bus.write_i2c_block_data(self.addr, 0x40, [_ch])


if __name__ == '__main__':
	myLcd = jlx1602a(0x3c,1)
	myLcd.move(1,3)
	myLcd.printStr("Hello World")
	myLcd.move(2,2)
	myLcd.printStr('Ittraining')

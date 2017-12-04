import pylcdlib
lcd = pylcdlib.lcd(0x29,1,1)
#lcd.lcd_putc('H')
lcd.lcd_write(0x00)

#lcd.lcd_clear()
lcd.lcd_puts("Hi!Ittraining",1)  #display "Raspberry Pi" on line 1
lcd.lcd_puts("I'm joseph chen",2)  #display "Take a byte!" on line 2
lcd.lcd_back(1)

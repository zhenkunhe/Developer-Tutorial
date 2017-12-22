;  BL example
;  IDE: ADS 1.2
;  Programmer: oct@mail2000.com.tw

	AREA	Start, CODE, READONLY	; declare the name of the code segment
	CODE32				; ARM code (32-bit mode)

	ENTRY				; mark the entry point of the segment

start
	MOV	r0, #0			; set value for registers r0~r2
	MOV	r1, #1
	MOV	r2, #2
	BL	fun1			; call the function "fun1", with link
	B	fun2			; call the function "fun2", without link

fun1
	MOV	r0, #0x11
	MOV	pc, lr			; return

fun2
	MOV	r0, #0x22
	MOV	pc, lr
	BL	.			; block here

	END
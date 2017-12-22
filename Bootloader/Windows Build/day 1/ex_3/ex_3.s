;  Change mode example
;  IDE: ADS 1.2
;  Programmer: oct@mail2000.com.tw

	AREA	Start, CODE, READONLY	; declare the name of the code segment
	CODE32				; ARM code (32-bit mode)

	ENTRY				; mark the entry point of the segment

start
	; set to IRQ mode
	MRS	r0, CPSR
	MOV	r0, #0x12	; mode 0b10010 ==> IRQ mode
	MSR	CPSR_c, r0

	; return to SVC mode	
	MRS	r0, CPSR
	MOV	r0, #0x13	; mode 0b10011 ==> SVC mode
	MSR	CPSR_c, r0
	
	B	.		; block here

	END

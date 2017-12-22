;  MMU example
;  IDE: ADS 1.2
;  Programmer: oct@mail2000.com.tw

	AREA	Setup, CODE, READONLY		; declare the name of the code segment
	CODE32					; ARM code (32-bit mode)

	ENTRY					; mark the entry point of the segment
		
	IMPORT	My_Main				; import the entry of C language
	IMPORT	||Image$$RAM_RUN$$ZI$$Base||	; import the ZI section base address
	IMPORT	||Image$$RAM_RUN$$ZI$$Length||	; import the ZI section length
	IMPORT	||Image$$RAM_RUN$$ZI$$Limit||	; import the ZI section end address


Vect
	B	Reset_Hdl		; branch to Reset handler
	B	Undef_Hdl		; branch to Undefined Instruction handler
	B	SWI_Hdl			; branch to Software Interrupt handler
	B	PreAbt_Hdl		; branch to Prefetch Abort handler
	B	DataAbt_Hdl		; branch to Data Abort handler
	NOP
	B	IRQ_Hdl			; branch to IRQ handler
	B	FIQ_Hdl			; branch to FIQ handler

Undef_Hdl
	B	.			; block here
SWI_Hdl
	B	.			; block here
PreAbt_Hdl
	B	.			; block here
DataAbt_Hdl
	B	.			; block here
IRQ_Hdl
	B	.			; block here
FIQ_Hdl
	B	.			; block here


Reset_Hdl
	B	InitHW
InitHW
	; add your H/W initialization code here
	B	DetectWH
DetectWH
	; add your H/W detect code here
	B	GotoModeN
GotoModeN
	; add your mode execution code here
	B	InitMem
InitMem
	; add your memory initialization code here
	B	InitBSS

		
InitBSS
	LDR	r0, =||Image$$RAM_RUN$$ZI$$Base||
	LDR	r1, =||Image$$RAM_RUN$$ZI$$Length||
	LDR	r2, =||Image$$RAM_RUN$$ZI$$Limit||
	MOV	r3, #0
initbsslp
	STR	r3, [r0], #4		; store 0(r3) to memory address r0,
					; and add 4 to r0
	CMP	r0, r2			; compare r0 and ZI section end address (r2)
	BLT	initbsslp		; branch to initbsslp if r0 less then r2

InitStack
	MOV	sp, #0x10000	; setup stack pointer

	BL	My_Main			; branch to c function


	END
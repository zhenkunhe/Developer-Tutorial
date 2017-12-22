;  Raspberry Pi Bare Metal - HDMI Framebuffer
;  IDE: ADS 1.2
;  Programmer: oct@mail2000.com.tw

	AREA	Init, CODE, READONLY	; declare the name of the code segment
	CODE32				; ARM code (32-bit mode)

	EXTERN	My_Main
	
	IMPORT	||Image$$RAM_RUN$$ZI$$Base||
	IMPORT	||Image$$RAM_RUN$$ZI$$Length||
	IMPORT	||Image$$RAM_RUN$$ZI$$Limit||

	ENTRY				; mark the first instruction to call

Vect					; exception vector
	B	start
	B	.
	B	.
	B	.
	B	.
	NOP
	B	.
	B	.

start
	B	InitHW
InitHW
	B	InitMem
InitMem
	B	HWDetect
HWDetect
	B	InitStack
InitStack
	MOV	sp, #0x100000	; setup stack pointer
	B	InitBSS
InitBSS
	LDR	r0, =||Image$$RAM_RUN$$ZI$$Base||
	LDR	r1, =||Image$$RAM_RUN$$ZI$$Length||
	LDR	r2, =||Image$$RAM_RUN$$ZI$$Limit||
	MOV	r3, #0
initbsslp
	STR	r3, [r0], #4
	CMP	r0, r2
	BLT	initbsslp

	MRS	r0, CPSR	; disable IRQ & FIQ
	ORR	r0, r0, #0xC0
	MSR	CPSR_c, r0

	BL	My_Main		; branch to c function

	END
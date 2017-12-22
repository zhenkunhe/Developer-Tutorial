//  Raspberry Pi Bare Metal - HDMI Framebuffer
//  IDE: GNU
//  Programmer: oct@mail2000.com.tw

	.text

Vect:					// exception vector
	B	Reset
	B	.
	B	.
	B	.
	B	.
	NOP
	B	.
	B	.

Reset:
	B	InitHW
InitHW:
	B	InitMem
InitMem:
	B	HWDetect
HWDetect:
	B	InitStack
InitStack:
	MOV	sp, #0x100000	// setup stack pointer
	B	InitBSS
InitBSS:
	LDR	r0, =IMAGE_ZI_BASE
	LDR	r2, =IMAGE_ZI_LIMIT
	MOV	r3, #0
initbsslp:
	STR	r3, [r0], #4
	CMP	r0, r2
	BLT	initbsslp

	MRS	r0, CPSR	// disable IRQ & FIQ
	ORR	r0, r0, #0xC0
	MSR	CPSR_c, r0

	BL	My_Main		// branch to c function


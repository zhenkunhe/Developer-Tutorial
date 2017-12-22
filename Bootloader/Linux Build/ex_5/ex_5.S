//  Raspberry Pi Bare Metal - Timer
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
	B	IRQ_Handle		// branch to IRQ_Handle
	B	.

IRQ_Handle:	// IRQ Handler
	STMDB	r13!, {R0-R12, lr}
	MOV	r0, #0
	MCR	p15, 0, r0, c7, c10, 5 // Data Memory barrier on ARMv6
	BL	ISR_IRQ
	MOV	r0, #0
	MCR	p15, 0, r0, c7, c10, 5 // Data Memory barrier on ARMv6
	LDMIA	r13!, {R0-R12, lr}
	SUBS	pc, lr, #4

Reset:
	B	InitHW
InitHW:
	B	InitMem
InitMem:
	B	HWDetect
HWDetect:
	B	InitStack
InitStack:
	// set to IRQ mode
	MRS	r0, CPSR
	MOV	r0, #0x12	// mode 0b10010 ==> IRQ mode
	MSR	CPSR_c, r0
	MOV	sp, #0x0C0000	// setup sp_irq stack pointer

	// return to SVC mode
	MRS	r0, CPSR
	MOV	r0, #0x13	// mode 0b10011 ==> SVC mode
	MSR	CPSR_c, r0
	MOV	sp, #0x100000	// setup sp_svc stack pointer

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

	

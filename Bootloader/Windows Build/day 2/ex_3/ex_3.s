;  IRQ Handler example
;  IDE: ADS 1.2
;  Programmer: oct@mail2000.com.tw

	AREA	Init, CODE, READONLY	; declare the name of the code segment
	CODE32	 			; ARM code (32-bit mode)
	
	EXTERN	My_Main
	EXTERN	ISR_IRQ
	
	IMPORT	||Image$$RAM_RUN$$ZI$$Base||
	IMPORT	||Image$$RAM_RUN$$ZI$$Length||
	IMPORT	||Image$$RAM_RUN$$ZI$$Limit||

	ENTRY				; mark the first instruction to call

Vect	; exception vector
	B	start
	B	.
	B	.
	B	.
	B	.
	NOP
	B	IRQ_Handle	; branch to IRQ_Handle
	B	.

IRQ_Handle	; IRQ Handler
	STMDB	r13!, {R0-R12, lr}
	BL	ISR_IRQ
	LDMIA	r13!, {R0-R12, lr}
	SUBS	pc, lr, #4

start
	B	InitHW
InitHW
	B	InitMem
InitMem
	B	HWDetect
HWDetect
	B	InitStack
InitStack

	; set to IRQ mode
	MRS	r0, CPSR
	MOV	r0, #0x12	; mode 0b10010 ==> IRQ mode
	MSR	CPSR_c, r0
	MOV	sp, #0x800000	; setup sp_irq stack pointer

	; return to SVC mode	
	MRS	r0, CPSR
	MOV	r0, #0x13	; mode 0b10011 ==> SVC mode
	MSR	CPSR_c, r0
	MOV	sp, #0x100000	; setup sp_svc stack pointer
	
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

	MRS	r0, CPSR	; enable IRQ & FIQ
	BIC	r0, r0, #0xC0
	MSR	CPSR_c, r0

	BL	My_Main		; branch to c function

	END
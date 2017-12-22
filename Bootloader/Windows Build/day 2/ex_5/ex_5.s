;  SWI Handler example
;  IDE: ADS 1.2
;  Programmer: oct@mail2000.com.tw

	AREA	Init, CODE, READONLY	; declare the name of the code segment
	CODE32				; ARM code (32-bit mode)
	
	EXTERN	My_Main
	EXTERN	ISR_SWI
	
	IMPORT	||Image$$RAM_RUN$$ZI$$Base||
	IMPORT	||Image$$RAM_RUN$$ZI$$Length||
	IMPORT	||Image$$RAM_RUN$$ZI$$Limit||

	ENTRY				; mark the first instruction to call

Vect	; exception vector
	B	start
	B	.
	B	SWI_Handle	; branch to SWI_Handle
	B	.
	B	.
	NOP
	B	.
	B	.

SWI_Handle	; SWI Handler
	STMDB	r13!, {r0-r12, lr}
	MRS	r0, SPSR
	STMDB	r13!, {r0}
	LDR	r0, [lr, #-4]
	BIC	r0, r0, #0xFF000000
	BL	ISR_SWI
	LDMIA	r13!, {r0}
	MSR	SPSR_cxsf, r0
	LDMIA	r13!, {r0-r12, lr}
	MOVS	pc, lr

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

	BL	My_Main		; branch to C function

	END
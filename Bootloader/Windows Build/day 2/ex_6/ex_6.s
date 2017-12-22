;  ASM & C example
;  IDE: ADS 1.2
;  Programmer: oct@mail2000.com.tw

	AREA	Init, CODE, READONLY	; declare the name of the code segment
	CODE32				; ARM code (32-bit mode)
	
	EXTERN	My_Main
	EXTERN	call_by_asm
	
	EXPORT	call_to_asm
	
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
	
	MOV	r0, #1
	MOV	r1, #2
	MOV	r2, #3
	MOV	r3, #4
	MOV	r4, #0
	MOV	r5, #0
	MOV	r8, #5
	MOV	r9, #6
	STMDB	r13!, {r8, r9}
	BL	call_by_asm

	BL	My_Main		; branch to c function

call_to_asm
	LDMIA	sp!, {r8, r9}
	SUB	r0, r0, r1
	SUB	r0, r0, r2
	SUB	r0, r0, r3
	SUB	r0, r0, r8
	SUB	r0, r0, r9
	MOV	pc, lr

	END
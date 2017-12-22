;  ARM & Thumb interwork example (ARM->Thumb->ARM->Thumb)
;  IDE: ADS 1.2
;  Programmer: oct@mail2000.com.tw

	AREA	Start, CODE, READONLY	; declare the name of the code segment
	CODE32				; ARM code (32-bit mode)

	ENTRY				; mark the entry point of the segment

start
	MOV	sp, #0x100000		; set stack
	MOV	r0, #0			; stage count = 0
	ADR	r3, thumb_prog + 1	; set bit 0
	ADD	r0, pc, #4		; set return address to lr
	MOV	lr, r0
	BX	r3			; branch and exchange to Thumb mode
	B	.			; block here

	CODE16				; Thumb code (16-bit mode)

thumb_prog
	PUSH	{lr}			; save return address
	MOV	r0, #1			; stage count = 1
	ADR	r3, arm_sub		; address of ARM mode subroutine
	ADD	r1, pc, #4		; set return address to lr
	MOV	lr, r1
	BX	r3			; branch and exchange to ARM mode
	MOV	r0, #1			; stage count = 1
	POP	{r7}			; restore return address to r7
	BX	r7			; branch and exchange to ARM mode

	CODE32				; ARM code (32-bit mode)
	
arm_sub
	STMDB	sp!, {lr}
	MOV	r0, #2			; stage count = 2
	ADR	r3, thumb_sub + 1	; set bit 0
	ADD	r0, pc, #4		; set return address to lr
	MOV	lr, r0
	BX	r3			; branch and exchange to Thumb mode
	LDMIA	sp!, {lr}
	ADD	lr, lr, #1		; set bit 0
	BX	lr			; branch and exchange to Thumb mode
	
	CODE16				; Thumb code (16-bit mode)

thumb_sub
	MOV	r0, #3			; stage count = 3
	BX	lr			; branch and exchange to ARM mode

	B	.			; block here

	END
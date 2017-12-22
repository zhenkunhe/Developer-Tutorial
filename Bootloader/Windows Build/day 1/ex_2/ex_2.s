;  Data processing example
;  IDE: ADS 1.2
;  Programmer: oct@mail2000.com.tw

	AREA	Start, CODE, READONLY	; declare the name of the code segment
	CODE32				; ARM code (32-bit mode)

	ENTRY				; mark the entry point of the segment

start
	MOV	r0, #1
	MOV	r1, #2
	ADD	r2, r1, r0		; r2 = r1 + r0
	ADD	r3, r1, #7		; r3 = r1 + 7
        
	LDR	r0, =0xff00ff00
	LDR	r1, =0xffff0000
	AND	r2, r0, r1		; r2 = r0 & r1
	ORR	r3, r0, r1		; r3 = r0 | r1

	MOV	r0, #0
	MOV	r1, #1
	CMP	r0, r1
	BEQ	equal
	MOV	r3, #0
	
equal
	MOV	r3, #1
	BL	.			; block here

	END
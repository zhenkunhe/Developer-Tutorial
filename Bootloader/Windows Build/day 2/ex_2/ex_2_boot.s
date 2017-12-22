;  Copy ROM code example
;  IDE: ADS 1.2
;  Programmer: oct@mail2000.com.tw

	AREA	Boot, CODE, READONLY	; declare the name of the code segment
	CODE32				; ARM code (32-bit mode)
	
	IMPORT	||Load$$RAM_RUN$$Base||
	IMPORT	||Image$$RAM_RUN$$Base||
	IMPORT	||Image$$RAM_RUN$$Limit||

	ENTRY				; mark the first instruction to call

	LDR	r0, =||Load$$RAM_RUN$$Base||
	LDR	r1, =||Image$$RAM_RUN$$Base||
	LDR	r2, =||Image$$RAM_RUN$$Limit||
cplp
	LDR	r3, [r0], #4
	STR	r3, [r1], #4
	CMP	r1, r2
	BLT	cplp
	
	MOV	PC, #0			; jump to reset exception (entry)

	END
	
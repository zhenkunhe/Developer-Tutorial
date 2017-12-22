//  Copy ROM code example
//  IDE: GNU
//  Programmer: oct@mail2000.com.tw

	.text
	.global _start

_start:
	LDR	r0, =IMAGE_ROM_BASE
	LDR	r1, =IMAGE_RAM_BASE
	LDR	r2, =IMAGE_RAM_LIMIT
cplp:
	LDR	r3, [r0], #4
	STR	r3, [r1], #4
	CMP	r1, r2
	BLT	cplp
	
	MOV	r0, #0
	MCR	p15, 0, r0, c7, c10, 4	// Data Synchronization barrier on ARMv6
	
	MOV	PC, #0			// jump to reset exception (entry)


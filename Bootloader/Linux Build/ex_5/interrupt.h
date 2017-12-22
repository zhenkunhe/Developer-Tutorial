/*************************************************************
INTERRUPT.H
IDE: GNU
*************************************************************/

#ifndef INTERRUPT_H
#define INTERRUPT_H

#ifndef REG32
#define REG32(A) (*(volatile unsigned long *)(A))
#endif

#define	INT_BASE	0x3F00B000

#define	INT_IRQ_BPEND	REG32(INT_BASE + 0x200)
#define	INT_IRQ_PEND1	REG32(INT_BASE + 0x204)
#define	INT_IRQ_PEND2	REG32(INT_BASE + 0x208)
#define	INT_FIQ_CTL	REG32(INT_BASE + 0x20C)
#define	INT_ENIRQ1	REG32(INT_BASE + 0x210)
#define	INT_ENIRQ2	REG32(INT_BASE + 0x214)
#define	INT_ENBIRQ	REG32(INT_BASE + 0x218)
#define	INT_DISIRQ1	REG32(INT_BASE + 0x21C)
#define	INT_DISIRQ2	REG32(INT_BASE + 0x220)
#define	INT_DISBIRQ	REG32(INT_BASE + 0x224)

#endif

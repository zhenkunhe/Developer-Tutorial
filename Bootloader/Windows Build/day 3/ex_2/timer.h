/*************************************************************
TIMER.H
IDE: ADS 1.2
*************************************************************/

#ifndef TIMER_H
#define TIMER_H

#ifndef REG32
#define REG32(A) (*(volatile unsigned long *)(A))
#endif


#define	STIMER_BASE	0x3F003000

#define	STIMER_CTRL	REG32(STIMER_BASE + 0x00)
#define	STIMER_COUNT_L	REG32(STIMER_BASE + 0x04)
#define	STIMER_COUNT_H	REG32(STIMER_BASE + 0x08)
#define	STIMER_CMP0	REG32(STIMER_BASE + 0x0C)
#define	STIMER_CMP1	REG32(STIMER_BASE + 0x10)
#define	STIMER_CMP2	REG32(STIMER_BASE + 0x14)
#define	STIMER_CMP3	REG32(STIMER_BASE + 0x18)


#define	TIMER_BASE	0x3F00B000

#define	TIMER_LOAD	REG32(TIMER_BASE + 0x400)
#define	TIMER_VALUE	REG32(TIMER_BASE + 0x404)
#define	TIMER_CTRL	REG32(TIMER_BASE + 0x408)
#define	TIMER_IRQCLR	REG32(TIMER_BASE + 0x40C)
#define	TIMER_RAWIRQ	REG32(TIMER_BASE + 0x410)
#define	TIMER_MASKIRQ	REG32(TIMER_BASE + 0x414)
#define	TIMER_RELOAD	REG32(TIMER_BASE + 0x418)
#define	TIMER_PREDIV	REG32(TIMER_BASE + 0x418)
#define	TIMER_FREERUN	REG32(TIMER_BASE + 0x420)


#endif
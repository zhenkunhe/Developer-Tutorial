/*************************************************************
MAILBOX.H
IDE: ADS 1.2
*************************************************************/

#ifndef MAILBOX_H
#define MAILBOX_H

#ifndef REG32
#define REG32(A) (*(volatile unsigned long *)(A))
#endif

#define	MB_BASE	0x3F00B880

#define	MB_READ		REG32(MB_BASE + 0x00)
#define	MB_POLL		REG32(MB_BASE + 0x10)
#define	MB_SENDER	REG32(MB_BASE + 0x14)
#define	MB_STATUS	REG32(MB_BASE + 0x18)
#define	MB_CONFIG	REG32(MB_BASE + 0x1C)
#define	MB_WRITE	REG32(MB_BASE + 0x20)

#endif

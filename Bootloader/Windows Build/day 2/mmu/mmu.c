#include "mmu.h"


void mmu_Enable(void)
{
	__asm
	{
		mrc	p15, 0, r0, c1, c0, 0
		orr	r0, r0, #0x1
		mcr	p15, 0, r0, c1, c0, 0
	}
}

void mmu_Disable(void)
{
	__asm
	{
		mrc	p15, 0, r0, c1, c0, 0
		bic	r0, r0, #0x1
		mcr	p15, 0, r0, c1, c0, 0
	}
}

void dcache_Disable(void)
{
	__asm
	{
		mrc	p15, 0, r0, c1, c0, 0
		bic	r0, r0, #0x4
		mcr	p15, 0, r0, c1, c0, 0
	}
}

void mmu_SetBase(void)
{
	__asm
	{
		mov	r0, MMU_BASE
		mcr	p15, 0, r0, c2, c0, 0
	}
}

void mmu_SetDomain(void)
{
	__asm
	{
		mov	r0, #0x3
		mcr	p15, 0, r0, c3, c0, 0
	}
}

// setup 1'st level descriptor as section mapping
void mmu_SetupSection(void)
{
	int i;
	unsigned long *ptr = (unsigned long *)MMU_BASE;
	
	for(i=0; i<4096; i++)
	{
		*ptr++ = (i * 0x100000) | (0xC00);
	}
}

void mmu_SetCoarsePageIdx(unsigned long va, unsigned long base)
{
	int i;
	unsigned long *ptr = (unsigned long *)MMU_BASE;
	
	ptr += (va / 0x100000);
	*ptr = (base & 0xFFFFFC00) | 0x1;

	ptr = (unsigned long *)base;
	for(i=0; i<256; i++)
	{
		*ptr++ = 0;
	}
}

void mmu_SetCoarsePage(unsigned long va, unsigned long pa)
{
	unsigned long first = (unsigned long)MMU_BASE;
	unsigned long second;
	
	first |= (va & 0xFFF00000) >> 18;
	
	second = *(unsigned long *)(first) & 0xFFFFFC00;
	second |= ((va & 0xFF000) >> 10);
	
	*(unsigned long *)second = (pa & 0xFFFFFC00) | 0x2;
}




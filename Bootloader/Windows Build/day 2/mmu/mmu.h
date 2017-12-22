#ifndef	MMU_H
#define	MMU_H

#define MMU_BASE 0x300000

void mmu_Enable(void);
void mmu_Disable(void);
void dcache_Disable(void);
void mmu_SetBase(void);
void mmu_SetupSection(void);
void mmu_SetDomain(void);

void mmu_SetCoarsePageIdx(unsigned long va, unsigned long base);
void mmu_SetCoarsePage(unsigned long va, unsigned long pa);

#endif

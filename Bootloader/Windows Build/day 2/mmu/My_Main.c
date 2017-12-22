#include <stdio.h>
#include <rt_misc.h>
#include "mmu.h"

void My_Main(void)
{
	mmu_Disable();
	dcache_Disable();
	
	__rt_lib_init(0xC0000, 0x100000); // stack for rt_lib (256K)
	
	// MMU table mapping
	mmu_SetBase();
	mmu_SetDomain();

	mmu_SetupSection();
	mmu_SetCoarsePageIdx(0x10000000, 0x304000); // set coarse of va 0x10000000 to 0x304000
	mmu_SetCoarsePage(0x10000000, 0x20000000); // map va 0x10000000 to pa 0x20000000
	mmu_SetCoarsePage(0x10001000, 0x30000000); // map va 0x10001000 to pa 0x30000000
	
	*(unsigned long *)(0x10000000) = 1; // pa 0x10000000 = 1
	*(unsigned long *)(0x10001000) = 2; // pa 0x10001000 = 2
	*(unsigned long *)(0x20000000) = 3; // pa 0x20000000 = 3
	*(unsigned long *)(0x30000000) = 4; // pa 0x20000000 = 4
	
	*(unsigned long *)(0x40000000) = 5; // pa 0x40000000 = 5

	printf("virtual memory test\n");
	printf("MMU disabled\n");
	printf("0x10000000 = %lx\n", *(unsigned long *)(0x10000000));
	printf("0x10001000 = %lx\n", *(unsigned long *)(0x10001000));
	
	mmu_Enable();
	printf("MMU enabled\n");
	printf("0x10000000 = %lx\n", *(unsigned long *)(0x10000000));
	printf("0x10001000 = %lx\n", *(unsigned long *)(0x10001000));
	
	mmu_Disable();
	printf("MMU disabled\n");
	printf("0x10000000 = %lx\n", *(unsigned long *)(0x10000000));
	printf("0x10001000 = %lx\n", *(unsigned long *)(0x10001000));

	// violation test
	printf("violation test\n");
	printf("MMU disabled\n");
	printf("0x10000000 = %lx\n", *(unsigned long *)(0x10000000));
	printf("0x40000000 = %lx\n", *(unsigned long *)(0x40000000));
	
	mmu_Enable();
	printf("MMU enabled\n");
	printf("0x10000000 = %lx\n", *(unsigned long *)(0x10000000));
	printf("0x40000000 = %lx\n", *(unsigned long *)(0x40000000));
	

	while(1) {} // block here
}


__value_in_regs struct __initial_stackheap __user_initial_stackheap(
	unsigned R0, unsigned SP, unsigned R2, unsigned SL)
{
	struct __initial_stackheap config;
    
	config.heap_base = 0xC0000; // heap base
	config.stack_base = 0x100000; // stack base

    return config;
}

obj-m := sn_module.o

#KDIR := /lib/modules/2.6.18/build/
KDIR := /lib/modules/4.4.0-93-generic/build
PWD := $(shell pwd)

ccflags-y := -w

all:
	make -C $(KDIR) M=$(PWD) modules

clean:
	make -C $(KDIR) M=$(PWD) clean
	rm -rf *.ko *.mod.c *.o Module.* *.c~ Makefile~

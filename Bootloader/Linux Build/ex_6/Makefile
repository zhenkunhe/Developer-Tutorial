CROSS_COMPILE	= arm-linux-gnueabihf-
CC		= $(CROSS_COMPILE)gcc
LD		= $(CROSS_COMPILE)ld
AS		= $(CROSS_COMPILE)as
OBJDUMP		= $(CROSS_COMPILE)objdump
OBJCOPY		= $(CROSS_COMPILE)objcopy

LDS   = link.lds
O_MAP = armtutor.map
O_BIN = armtutor.bin
O_ELF = armtutor.elf
O_DIS = armtutor.dis
O_DEP = make.dep

SOBJS = ex_6.o ex_6_boot.o
COBJS = My_Main.o mailbox.o
OBJS  = $(SOBJS) $(COBJS)

AFLAGS	= -Ofast -mfpu=vfp -mfloat-abi=hard -march=armv6zk -mtune=arm1176jzf-s
CFLAGS	= -Ofast -mfpu=vfp -mfloat-abi=hard -march=armv6zk -mtune=arm1176jzf-s
LFLAGS	= -T $(LDS)

%.o: %.S
	$(CC) $(CFLAGS) -c -o $@ $<

%.o: %.c
	$(CC) $(CFLAGS) -c -o $@ $<

.PHONY: all gen_dep gen_all clean

all: gen_dep gen_all

gen_dep: *.c *.h
	$(CC) -M $(SOBJS:.o=.S) $(COBJS:.o=.c) > $(O_DEP)

gen_all: $(O_BIN)

$(O_BIN): $(O_ELF)
	$(OBJCOPY) $< -O binary $@

$(O_ELF): $(OBJS)
	$(LD) --no-undefined $(OBJS) -Map $(O_MAP) -o $(O_ELF) -T $(LDS)
	$(OBJDUMP) -d $(O_ELF) > $(O_DIS)

clean:
	-rm -f $(OBJS)
	-rm -f $(O_MAP) $(O_BIN) $(O_ELF) $(O_DIS) $(O_DEP)

-include make.dep

OUTPUT_ARCH(arm)
ENTRY(_start)
SECTIONS
{
	. = 0x8000;
	.boot :
	{
		_start = .;
		ex_5_boot.o;
	}

	. = ALIGN(0x4);

	IMAGE_ROM_BASE = .;

	.text 0 :
	AT(IMAGE_ROM_BASE)
	{
		IMAGE_RAM_BASE = 0;
		Vect = 0;

		ex_5.o(.text)
		*(.text)
	}

	.data (ADDR(.text) + SIZEOF(.text)) :
	AT(LOADADDR(.text) + SIZEOF(.text))
	{
                *(.data)
        }

	IMAGE_RAM_LIMIT = SIZEOF(.text) + SIZEOF(.data);

	. = ALIGN (0x4);

	.bss : 
	{
		IMAGE_ZI_BASE = .;
		*(.bss)
		IMAGE_ZI_LIMIT = .;
	}
}


TARGET = play_from_file record_to_file

CFLAGS = -Wall `pkg-config --cflags --libs gstreamer-0.10`

PLAY_FROM_FILE_MOD = play_from_file
PLAY_FROM_FILE_SRC = play_from_file.c
RECORD_TO_FILE_MOD = record_to_file
RECORD_TO_FILE_SRC = record_to_file.c

all: ${PLAY_FROM_FILE_MOD} ${RECORD_TO_FILE_MOD}

${PLAY_FROM_FILE_MOD}: clean
	$(CC) ${PLAY_FROM_FILE_SRC} -o $@ $(CFLAGS)

${RECORD_TO_FILE_MOD}: clean
	$(CC) ${RECORD_TO_FILE_SRC} -o $@ $(CFLAGS)

clean:
	rm -f *.o ${TARGET}

CC = gcc
CFLAGS = -fPIC -shared -I. -I/usr/lib/jvm/java-8-openjdk-amd64/include -I/usr/lib/jvm/java-8-openjdk-amd64/include/linux
all: lajp_MsgQ.c
	$(CC) lajp_MsgQ.c -o liblajpmsgq.so $(CFLAGS)

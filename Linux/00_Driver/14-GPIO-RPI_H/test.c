#include<stdio.h>
#include<stdlib.h>
#include<sys/types.h>
#include<sys/stat.h>
#include<unistd.h>
#include<fcntl.h>
#include<time.h>

char on[2] = "1";
char off[2] = "0";
int count = 10;

int main(int argc,char *argv[]){
	int led0 = open("/dev/myLED0",O_RDWR);
	int led1 = open("/dev/myLED1",O_RDWR);

	while(count--){
		printf("%d: LEDs are blinking....\n",count);

		write(led0,on,2); 
		write(led1,off,2);

		usleep(500000);

		write(led0,off,2); 
		write(led1,on,2);

		usleep(500000);
	}

	write(led0,off,2);
	write(led1,off,2);

	close(led0);
	close(led1);
	return 0;
}

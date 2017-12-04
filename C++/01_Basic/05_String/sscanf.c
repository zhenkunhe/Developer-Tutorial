#include<stdio.h>

int main()
{
	char data[4][10];

    char *str = "/data=123/status=456/";
	sscanf(str,"/%[^=]=%[^/]",data[0],data[1]);
	printf("data[0] = [%s]\n",data[0]);
	printf("data[1] = [%s]\n",data[1]);
}

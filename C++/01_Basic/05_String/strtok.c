#include<stdio.h>
#include <string.h>
int main()
{
	const char * const str = "/data=123/str=456";
	const char * const delim = "/";
	char buf[30] = {0};
	char *substr = NULL;
	int count = 0;

	strcpy(buf, str);
	printf("original string: %s\n", buf);

	substr = strtok(buf, delim);
	do {
		printf("#%d sub string: %s\n", count++, substr);
		substr = strtok(NULL, delim);
	} while (substr);
}

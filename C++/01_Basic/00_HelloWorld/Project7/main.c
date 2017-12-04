
#include <stdio.h>
//#include <string.h>
int main(int argc, const char * argv[]) {
    /*
    char string[] = "Hello";
    printf("%s\n",string);
    strcpy(string,"你好嗎？");
    printf("%s\n",string);
    gets(string);
    printf("%s\n",string);
     */
    
    /*
    char str[][1024] = {"aaaaaaaaaaaa","bbb","ccc","狗狗狗"};
    for (int i=0; i<sizeof(str)/(sizeof(str[0])); i++) {
        printf("%s\n",str[i]);
    }
     */
    
    char str[10][1024];
    int num=0;
    for (int i=0; i<sizeof(str)/(sizeof(str[0])); i++) {
        printf("請逐一輸入學生姓名,停止請輸\"入stop\"\n");
        gets(str[i]);
        if(!strcmp(str[i],"stop")) break;
        num++;
    }
    
    for (int i=0; i<num; i++) {
        printf("第%i位為%s\n",i+1,str[i]);
    }
    
    return 0;
}


#include <stdio.h>

/*
struct Phone{
    char name[1024];
    char* no;
    int capacity;
    int price;
}p_1,p_2;
*/


struct Language{
    char ch[1024],en[1024];
};

struct Date{
    int year,month,day;
};

struct Book{
    struct Language language;
    struct Date date;
    char name[1024];
    int price;
}b_1={{"藤子不二雄","A.F"},{1989,7,17},"哆拉Ａ夢",100};



int main(int argc, const char * argv[]) {
    /*
    struct Phone p_3={"sony","z1",16,10000};
    printf("這支手機的名稱=%s\t 編號=%s\t 價格=%i\t 容量=%i\n",p_3.name,p_3.no,p_3.capacity,p_3.price);
    
    strcpy(p_1.name,"HTC");
    p_1.no = "one";
    p_1.capacity = 32;
    p_1.price = 15000;
    printf("這支手機的名稱=%s\t 編號=%s\t 價格=%i\t 容量=%i\n",p_1.name,p_1.no,p_1.capacity,p_1.price);
    
    gets(p_2.name);
    char str[1024];
    gets(str);
    p_2.no = str;
    scanf("%i%i",&p_2.capacity,&p_2.price);
    printf("這支手機的名稱=%s\t 編號=%s\t 價格=%i\t 容量=%i\n",p_2.name,p_2.no,p_2.capacity,p_2.price);
    */
    
    printf(" 作者中文=%s\t 英文=%s\n 出版日期=%i年\t %i月\t %i日\n 書名=%s\n 價格=%i\n\n",b_1.language.ch,b_1.language.en,b_1.date.year,b_1.date.month,b_1.date.day,b_1.name,b_1.price);
    
    struct Book b_2 = {{"富奸","WTF"},{1999,2,13},"獵人",100};
    
    printf(" 作者中文=%s\t 英文=%s\n 出版日期=%i年\t %i月\t %i日\n 書名=%s\n 價格=%i\n\n",b_2.language.ch,b_2.language.en,b_2.date.year,b_2.date.month,b_2.date.day,b_2.name,b_2.price);
    
    
    return 0;
}

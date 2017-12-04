
#include <stdio.h>
#include <stdlib.h>
void changeValue(int a , int b);
void changeRefence(int* a , int* b);
int main(int argc, const char * argv[]) {
    /*
    int a=10;
    int b=20;
    int *ptr = &a;
    b = *ptr;
    a = 1000;
    printf("a本身的值是%i,記憶體位置是%p\n",a,&a);
    printf("b本身的值是%i,記憶體位置是%p\n",b,&b);
    printf("ptr本身的值是%p,記憶體位置是%p\n",ptr,&ptr);
    */
    
    /*
    int a=10,b=20,c=30;
    int *ptr_1,ptr_2;
    ptr_1 = &a;
    ptr_1 -= 2;
    ptr_2 = ptr_1;
    printf("a本身的值是%i,記憶體位置是%p\n",a,&a);
    printf("b本身的值是%i,記憶體位置是%p\n",b,&b);
    printf("c本身的值是%i,記憶體位置是%p\n",c,&c);
    printf("ptr_1本身的值是%p,記憶體位置是%p\n",ptr_1,&ptr_1);
    printf("ptr_2本身的值是%p,記憶體位置是%p\n",ptr_2,&ptr_2);
     */
    
    /*
    int a=10,b=20;
    printf("______改變前a的記憶體位置%p,值為%d\n",&a,a);
    printf("______改變前b的記憶體位置%p,值為%d\n",&b,b);
    //changeValue(a, b);
    changeRefence(&a, &b);
    printf("______改變後a的記憶體位置%p,值為%d\n",&a,a);
    printf("______改變後b的記憶體位置%p,值為%d\n",&b,b);
     */
    
    /*
    int ary[] = {1,2,3,4,5,6,7};
    int* ptr = &ary[0];
    for (int i=0; i<(sizeof(ary)/sizeof(int)); i++) {
        printf("%i\n",*(ptr+i));
    }
    */
    
    
    int max;
    printf("請問要輸入幾個值？");
    scanf("%i",&max);
    int *ptr = (int *)malloc(sizeof(int)*max);
    
    for (int i=0; i<max; i++) {
        scanf("%i",ptr+i);
    }
    for (int i=0; i<max; i++) {
        printf("你輸入的值為%i\n",*(ptr+i));
    }
    free(ptr);
    
    return 0;
}

void changeValue(int a , int b){
    printf("a本身的值是%i,記憶體位置是%p\n",a,&a);
    printf("b本身的值是%i,記憶體位置是%p\n",b,&b);
    int temp =a;
    a=b;
    b=temp;
    printf("a本身的值是%i,記憶體位置是%p\n",a,&a);
    printf("b本身的值是%i,記憶體位置是%p\n",b,&b);
}

void changeRefence(int* a , int* b){
    printf("a本身的值是%p,記憶體位置是%p\n",a,&a);
    printf("b本身的值是%p,記憶體位置是%p\n",b,&b);
    int temp =*a;
    *a=*b;
    *b=temp;
    printf("a本身的值是%p,記憶體位置是%p\n",a,&a);
    printf("b本身的值是%p,記憶體位置是%p\n",b,&b);
}


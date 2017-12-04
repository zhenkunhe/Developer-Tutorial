#include <stdio.h>

int main(int argc, const char * argv[]) {
    /*
    int sum=0;
    for (int i=1 ; i<=10; i++) {
        sum+=i;
    }
    printf("%i\n",sum);
    */
    
    /*
    for (int i=10 ; i>=0; i--) {
        if (i) {
            printf("%i\n",i);
        }
        else{
            printf("時間到！\n");
        }
        
    }
    */
    
    /*
    int i=1;
    int sum=0;
    while (i<=10) {
        sum+=i;
        i++;
    }
    printf("%i\n",sum);
     */
    
    /*
    int i=1;
    int sum=0;
    do {
        sum+=i;
        i++;
    }while(i<=10);
    printf("%i\n",sum);
     */
    
    /*
    int floor;
    
    printf("請輸入你要的樓層：");
    scanf("%i",&floor);
    
    for (int i=1 ; i<=10; i++) {
        if (i==floor) {
            printf("%i樓到了!\n",floor);
            break;
        }
        else{
            printf("%i樓\n",i);
            if(i==10){
                printf("Get out!\n");
            }
            continue;
        }
    }
     */
    
    /*
    int i,j;
    for (i=1; i<=9; i++) {
        for (j=1; j<=9; j++) {
            printf("%i*%i=%i\n",j,i,j*i);
        }
    }
     */
    
    return 0;
}

#include <stdio.h>
#include <ctype.h>

int main(int argc, const char * argv[]) {
    /*
    int x;
    
    scanf("%i",&x);
    
    switch (x) {
        case 0 ... 59:
            printf("Good\n");
            break;
        case 60 ... 79:
            printf("great\n");
            break;
        case 80 ... 100:
            printf("excellent\n");
            break;
        default:
            printf("WTF\n");
            break;
    }
    */
    
    /*
    char choice;
    scanf("%c",&choice);
    choice = toupper(choice);
    
    switch (choice) {
        case 'Y':
            printf("Y\n");
            break;
        case 'N':
            printf("N\n");
            break;
        default:
            printf("WTF\n");
            break;
    }
    */
    
    char choice;
    int money;
    scanf("%c%i", &choice,&money);
    choice = toupper(choice);
    
    switch (choice) {
        case 'A':
            if (money<100)
                printf("Fail\n");
            else
                printf("Done\n");
            break;
        case 'B':
            if (money<1000)
                printf("Fail\n");
            else
                printf("Done\n");
            break;
        default:
            printf("WTF\n");
            break;
    }
    return 0;
}

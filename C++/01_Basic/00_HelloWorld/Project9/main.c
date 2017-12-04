

#include <stdio.h>

/*
struct Cube{
    int l,w,h;
}c_a,*c_b;
*/

/*
struct Job{
    char com[1024];
    char pos[1024];
    int sal;
};
 */

struct Computer{
    char brand[1024];
    int price;
}com_1={"IBM",23999},com_2={"ASUS",19999};

void comPrint(struct Computer com);
void swapCom(struct Computer* com1,struct Computer* com2);
int main(int argc, const char * argv[]) {
    /*
    c_b = &c_a;
    
    scanf("%i%i%i",&((*c_b).l),&((*c_b).w),&((*c_b).h));
    printf("體積＝%i\n", (*c_b).l * (*c_b).w * (*c_b).h);
    */
    
    /*
    int amount;
    printf("有幾個人應徵？\n");
    scanf("%i",&amount);
    struct Job job[amount];
    
    for (int i=0; i<amount; i++) {
        gets(job[i].com);  //gets & scanf 混用時fflush問題
        printf("請輸入第%i位職員資料\n",i+1);
        gets(job[i].com);
        gets(job[i].pos);
        scanf("%i",&job[i].sal);
        //scanf("%s%s%i",job[i].com,job[i].pos,&job[i].sal);
    }
    
    for (int i=0; i<amount; i++) {
        printf("第%i位職員資料：公司＝%s\t 職位＝%s\t 薪資＝%i\n",i+1,job[i].com,job[i].pos,job[i].sal);
    }
    */
    
    swapCom(&com_1,&com_2);
    comPrint(com_1);
    comPrint(com_2);
     
    return 0;
}

void comPrint(struct Computer com){
    printf("此電腦品牌=%s\t價格為%i\n",com.brand,com.price);
}

void swapCom(struct Computer* com1,struct Computer* com2){
    struct Computer temp={"",0};
    strcpy(temp.brand,(*com1).brand);
    temp.price = (*com1).price;

    strcpy((*com1).brand,(*com2).brand);
    (*com1).price = (*com2).price;
    
    strcpy((*com2).brand,temp.brand);
    (*com2).price = temp.price;
}

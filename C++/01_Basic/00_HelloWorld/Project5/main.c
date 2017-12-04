
#include <stdio.h>

int countPower(int x ,int n);
int change(int data[],int i,int j);
int QuickSort(int array[],int length,int left,int right);
int goodluck(int ID);
int sum(int x,int y);
int main(int argc, const char * argv[]) {
    /*
    int a[]={10,20,30,40,50};
    int length = sizeof(a)/sizeof(int);
    int sum = 0;
    
    for (int i = 0; i<3; i++) {
        sum += a[i];
        printf("a[%i]=%i\n",i,a[i]);
    }
    
    printf("sum=%i\n",sum);
    printf("sum=%i\n",length);
    */
    
    /*
    int array[][4]={{1,2,3,4},{5,6,7,8},{9,10,11,12}};
    int length = sizeof(array)/(sizeof(int)*4);
    
    for (int i = 0; i<length; i++) {
        for (int j = 0; j<4; j++) {
            printf("a[%i][%i]=%i\t",i,j,array[i][j]);
        }
        printf("\n");
    }
    */
    
    /*
    int array[] = {4,5,7,2,3,8,10,9,1,6};
    int length = sizeof(array)/sizeof(int);
    
    QuickSort(array,length,0,length);
    for (int i = 0; i<length; i++) {
        printf("a[%i]=%i\n",i,array[i]);
    }
    */
    
    /* 最大最小排序法
    int array[] = {4,5,7,2,3,8,10,9,1,6};
    int length = sizeof(array)/sizeof(int);
     
    for(int i=0;i<length;i++){
        int min=i;
        for (int j=i+1; j<length; j++) {
            if(array[j]<array[min]) min=j;
        }
        change(array, i, min);
    }
    
    for (int i = 0; i<length; i++) {
        printf("a[%i]=%i\n",i,array[i]);
    }
    */
    
    /* 氣泡排序法
    int array[] = {4,5,7,2,3,8,10,9,1,6};
    int length = sizeof(array)/sizeof(int);
    
    for(int i=length-1;i>0;i--){
        for (int j=0; j<i; j++) {
            if(array[j]>array[j+1]) change(array, j, j+1);
        }
    }
    
    for (int i = 0; i<length; i++) {
        printf("a[%i]=%i\n",i,array[i]);
    }
     */

    
    
    /* 次方函式
    int x=2;
    int n=11;
    printf("%i\n",countPower(x,n));
     */
    
    
    /*
    int ID;
    printf("請問你的ＩＤ為：");
    scanf("%i",&ID);
    int result = goodluck(ID);
    if (result==0)
        printf("銘謝惠顧\n");
    else
        printf("恭喜你得到%i元\n",result);
    */
    
    //printf("%i\n",sum(20,10));
    
    return 0;
    
}


int change(int data[],int i,int j){
    int temp = data[i];
    data[i]=data[j];
    data[j]=temp;
    
    return 0;
}


int QuickSort(int array[],int length,int left,int right){
    
    if(left<right){
        int i = left;
        int j = right;
        
        while(1){
            // 向右找大於Pivot的數值的位置
            while (i+1 < length && array[++i] < array[left]);
            // 向左找小於Pivot的數值的位置
            while (j-1 > -1 && array[--j] > array[left]);
            if(i>=j)    break;
            
            change(array,i,j);
        }
        change(array,left,j);
        QuickSort(array,length,left,j-1);
        QuickSort(array,length,j+1,right);
    }
    
    return 0;
}


int countPower(int x,int n){
    if(n==1) return x;
    else return x * countPower(x,n-1);
}

int goodluck(int ID){
    switch (ID%5) {
        case 1:
            return 1000;
        case 2:
            return 2000;
        case 3:
            return 3000;
        case 4:
            return 4000;
        default:
            return 0;
    }
}

int sum(int x,int y){
    int min,max;
    if(x<y){
        min = x;
        max = y;
    }
    else {
        min = y;
        max = x;
    }
    if(min==max) return min;
    else return max+sum(min,max-1);
}


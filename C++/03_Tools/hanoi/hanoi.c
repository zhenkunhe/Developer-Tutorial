#include<stdio.h>

void hanoi (int num, char start, char temp, char end ){
	 if( num > 0 ){
		hanoi ( num-1 , start , end , temp );
		printf("%d  %c -> %c  \n" , num, start , end );
		hanoi ( num-1 , temp , start , end);
	}
}

int main(int argc,int argv[]){
	printf("%d\n",argc);
	hanoi ( 4 , 'A' , 'B' ,'C' );
	return 0;
}

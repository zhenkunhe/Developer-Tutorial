#include <stdio.h>
#include <unistd.h>
#include <pthread.h>
#include <iostream>

using namespace std;


struct A
{
    int status;
};

void func( A **p_a )
{
	A a;
	a.status = 999;
	*p_a = &a;
	cout << *p_a<<" - In function" << endl;
}

int main( int argc , char *argv[] )
{
	A *p_a = NULL;
	func(&p_a);
	cout << p_a<<" - In main" << endl;
	cout << p_a->status << endl;
}

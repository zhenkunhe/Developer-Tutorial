#include <stdlib.h>
#include <iostream>

using namespace std;

class A
{
public:
	A(int a, int b, int c[3]) :ma(a), mb(b), mc(c)
	{
		cout << a << endl;
		cout << b << endl;
		cout << mc[0] << endl;
		cout << mc[1] << endl;
		cout << mc[2] << endl;
	};

	~A() {};

	int ma;
	int mb;
	int* mc;
};

int main()
{
	int x[]={2,3,4};
	A a(0,1,x);
	return 0;
}





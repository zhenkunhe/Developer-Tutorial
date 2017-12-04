#include <iostream>

using namespace std;

int main()
{
	int a = 0x12345678;
	cout << &a << endl;
	cout << &a + 1 << endl;
	cout << (int*) ((unsigned char*) &a + 1) << endl;

	cout << hex << (int) ( *((unsigned char*) &a)) << endl;
	cout << hex << (int) ( *((unsigned char*) &a + 1)) << endl;
	cout << hex << (int) ( *((unsigned char*) &a + 2)) << endl;
	cout << hex << (int) ( *((unsigned char*) &a + 3)) << endl;

	return 0;
}

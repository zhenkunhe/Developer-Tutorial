#include <iostream>
#include <stdio.h>
#define myprintf(...) printf("[lch]:File:%s, Line:%d, Function:%s,"  __VA_ARGS__, __FILE__, __LINE__ ,__FUNCTION__);
using namespace std;

int main()
{
    myprintf("1\n")
    cout << __FUNCTION__ << endl;
    cout << __TIME__ << endl;
    cout << __STDC__ << endl;
    cout << __TIMESTAMP__ << endl;
    return 0;
}

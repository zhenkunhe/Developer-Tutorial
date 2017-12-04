#include <iostream>
#include "global.h"

using namespace std;

int
main()
{
    cout << x << endl;
    test();
    cout << x << endl;
    x++;
    cout << x << endl;
    test();
    return 0;
}

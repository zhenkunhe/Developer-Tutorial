
#include <unistd.h>

#include <iostream>
#include <string>
#include <map>

#include <malloc.h>

using namespace std;

void release_map(void)
{
    malloc_stats();
    map<int,string> testmap;
    sleep(2);
    for(int i=0; i<1000000; i++)
    {
        testmap.insert(make_pair(i,"abc"));
    }
    malloc_stats();
    testmap.clear();
    malloc_stats();
}

int main()
{
    release_map();
    getchar();
    return 0;
}

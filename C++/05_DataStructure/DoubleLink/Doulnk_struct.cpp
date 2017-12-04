#include <vector>
#include <iostream>
#include <algorithm>
#include <stdio.h>
using namespace std;

int main()
{
        vector<int> alex_list;
        alex_list.push_back( 1 );
        alex_list.push_back( 2 );
        alex_list.push_back( 3 );
        alex_list.push_back( 4 );
        vector<int>::iterator begin;

        //random vector
        srand( time( 0 ) );
        random_shuffle( alex_list.begin() + 1, alex_list.end() );
        begin = alex_list.begin();

        cout << *begin << endl;
        for (int i = 0; i < 100; i++)
        {
                if ( *begin == alex_list.front() )
                {
                        cout << "front" << endl;
                        begin = alex_list.end() - 1;
                }
                else
                {
                        --begin;
                }
                cout << *begin << endl;
        }
        cout << "-------------------------------" << endl;

        //sort vector
        sort( alex_list.begin(), alex_list.end() );
        cout << *begin << endl;
        begin = alex_list.begin();
        for (int i = 0; i < 100; i++)
        {
                if ( *begin == alex_list.back() )
                {
                        cout << "end" << endl;
                        begin = alex_list.begin();
                }
                else
                {
                        ++begin;
                }
                cout << *begin << endl;
        }
        cout << "-------------------------------" << endl;

        getchar();
        return 0;
}

#include <iostream>
#include <vector>

using namespace std;

template<typename Iterator>
auto sum( Iterator begin, Iterator end )->decltype(*begin+*begin)
{
        if ( begin == end ) throw logic_error( "...." );

        auto s = *begin;
        ++begin;

        for (; begin != end; ++begin)
        {
                s += *begin;
        }
        return s;
}

int main()
{
        vector<int> v = { 5, 9, 0, 11 };
        vector<int>::iterator vi =  v.begin();
        cout << sum( v.begin(), v.end() ) << endl;

        v.clear();
        vi = v.begin();
        cout << v.size() << endl;
        cout << *vi << endl;
        cout << *(++vi) << endl;

        return 0;
}

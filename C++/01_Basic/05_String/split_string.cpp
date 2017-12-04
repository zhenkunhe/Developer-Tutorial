#include <string>
#include <sstream>
#include <vector>
#include <iostream>

using namespace std;

vector<string> split( const string &s , char delim )
{
	stringstream ss( s );
	string item;
	vector<string> elems;
	while ( getline( ss , item , delim ) )
	{
		elems.push_back( item );
	}
	return elems;
}

int main()
{
	string s;
	while ( 1 )
	{
		cin >> s;
		vector<string> x = split( s , ':' );
		for (auto item : x)
		{
			cout << item << endl;
		}
	}
}

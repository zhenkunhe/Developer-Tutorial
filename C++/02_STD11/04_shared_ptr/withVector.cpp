// shared_ptr relational operators
#include <iostream>
#include <memory>
#include <vector>
#include <algorithm>

using namespace std;

vector<shared_ptr<int>> ints;

void PushItemByMove( shared_ptr<int>& arg )
{
	if ( arg == nullptr ) return;
	vector<shared_ptr<int>>::iterator find = find_if( ints.begin() , ints.end() , [&](shared_ptr<int> value)
	{
		return (value == arg);
	} );
	if ( find == ints.end() ) ints.push_back( move( arg ) );

}

void PushItemByCopy( shared_ptr<int>& arg )
{
	if ( arg == nullptr ) return;
	vector<shared_ptr<int>>::iterator find = find_if( ints.begin() , ints.end() , [&](shared_ptr<int> value)
	{
		return (value == arg);
	} );
	if ( find == ints.end() ) ints.push_back( arg );
}

void removeItem( shared_ptr<int>& arg )
{
	for (vector<shared_ptr<int>>::iterator it = ints.begin(); it != ints.end(); ++it)
	{
		if ( *it == arg )
		{
			cout << "Fine Remove Item!" << endl;
			ints.erase( it );
			break;
		}
	}
}

void TestPushAndRemoveItem( void )
{
	shared_ptr<int> e;
	e = make_shared<int>( 10 );
	cout << "Vector Size : " << ints.size() << endl;
	cout << "e != nullptr: " << (e != nullptr) << endl;
	PushItemByMove( e );
	PushItemByMove( e );
	PushItemByMove( e );
	cout << "Vector Size : " << ints.size() << endl;
	cout << "e != nullptr: " << (e != nullptr) << endl;
	removeItem( e );
	cout << "Vector Size : " << ints.size() << endl;
	cout << "e != nullptr: " << (e != nullptr) << endl << endl;

	shared_ptr<int> f;
	f = make_shared<int>( 20 );
	cout << "Vector Size : " << ints.size() << endl;
	cout << "f != nullptr: " << (f != nullptr) << endl;
	PushItemByCopy( f );
	PushItemByCopy( f );
	PushItemByCopy( f );
	cout << "Vector Size : " << ints.size() << endl;
	cout << "f != nullptr: " << (f != nullptr) << endl;
	removeItem( f );
	cout << "Vector Size : " << ints.size() << endl;
	cout << "f != nullptr: " << (f != nullptr) << endl << endl;
}

int main()
{
	shared_ptr<int> a , b , c , d;

	a = make_shared<int>( 10 );
	b = make_shared<int>( 10 );
	c = b;

	cout << "comparisons:\n" << boolalpha;

	cout << "a == b: " << (a == b) << endl;
	cout << "b == c: " << (b == c) << endl;
	cout << "c == d: " << (c == d) << endl;

	cout << "a != nullptr: " << (a != nullptr) << endl;
	cout << "b != nullptr: " << (b != nullptr) << endl;
	cout << "c != nullptr: " << (c != nullptr) << endl;
	cout << "d != nullptr: " << (d != nullptr) << endl << endl;

	TestPushAndRemoveItem();
	cout << "Vector[0] value : " << *ints[0] << endl;

	return 0;
}

#include<stdlib.h>
#include<iostream>

using namespace std;

template<typename T>
using function = void(*)(T);

template<typename T>
class Property
{
	public:
		Property( function<T> callback ) :
				data(), callback( callback )
		{
		}

		Property& operator=( const T& newvalue )
		{
			if ( data != newvalue)
			{
				data = newvalue;
				callback( data );
			}
			return *this;
		}

		operator T() const
		{
			return data;
		}

	private:
		T data;
		function<T> callback;
};

void test ( int x)
{
	cout << "Alex test:" << x << endl;
}

class PlayState
{
	public:
		PlayState() :X( &test ), Y( &test )
		{
		}

		Property<int> X , Y;
};

int main( int argc , char**argv , char**envArg )
{
	PlayState playState;
	playState.X = 123;
	playState.X = 123;
	playState.X = 456;
	cout << playState.X << endl;
	return 0;
}

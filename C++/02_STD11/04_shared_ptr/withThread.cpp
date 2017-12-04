// shared_ptr relational operators
#include <iostream>
#include <memory>
#include <thread>
#include <unistd.h>

using namespace std;

thread th;

void printThread( const shared_ptr<const int> arg )
{
	while ( true )
	{
		cout << "arg value : " << *arg << endl;
		sleep( 1 );
	}
}

void start()
{
	shared_ptr<int> tmp;
	tmp = make_shared<int>( 10 );
	th = thread( printThread , tmp );
}

int main()
{
	start();
	th.join();

	return 0;
}

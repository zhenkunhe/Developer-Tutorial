#include <iostream>
#include <string>
using namespace std;

class Interface
{
	public:
		virtual ~Interface()
		{
		}
		virtual void write( string& ) = 0;
		virtual void read( string& ) = 0;
};

class Core : public Interface
{
	public:
		~Core()
		{
			cout << "dtor-Core\n";
		}
		/*virtual*/
		void write( string& b )
		{
			b += "MESSAGE|";
		}
		/*virtual*/
		void read( string& b )
		{
			int pos = b.find_first_of( '|' );
			cout << "Wrapper: " << b.substr( 0 , pos ) << endl;
			b = b.substr( pos + 1 );
		}
};

class Decorator : public Interface
{
		Interface* inner;
	public:
		Decorator( Interface* c )
		{
			inner = c;
		}
		~Decorator()
		{
			delete inner;
		}
		/*virtual*/
		void write( string& b )
		{
			inner->write( b );
		}
		/*virtual*/
		void read( string& b )
		{
			inner->read( b );
		}
};

class Wrapper : public Decorator
{
		string forward , backward;
	public:
		Wrapper( Interface* c , string str ) :
						Decorator( c )
		{
			forward = str;
			string::reverse_iterator it;
			it = str.rbegin();
			for (; it != str.rend(); ++it)
				backward += *it;
		}
		~Wrapper()
		{
			cout << "dtor-" << forward << "  ";
		}
		void write( string& b )
		{
			b += forward + "]";
			Decorator::write( b );
			b += backward + "]";
		}
		void read( string& b )
		{
			int pos = b.find_first_of( ']' );
			cout << "Wrapper: " << b.substr( 0 , pos ) << endl;
			b = b.substr( pos + 1 );

			Decorator::read( b );

			pos = b.find_first_of( ']' );
			cout << "Wrapper: " << b.substr( 0 , pos ) << endl;
			b = b.substr( pos + 1 );
		}
};

int main( void )
{
	Interface* object = new Wrapper( new Wrapper( new Wrapper( new Core() , "123" ) , "abc" ) , "987" );
	string buf;
	object->write( buf );
	cout << "main: " << buf << endl;
	object->read( buf );
	delete object;
}

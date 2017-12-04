#include<iostream>
#include<thread>
#include<exception>
#include<stdexcept>

static std::exception_ptr teptr = nullptr;

void f()
{
	try
	{
		std::this_thread::sleep_for( std::chrono::seconds( 1 ) );
		throw std::runtime_error( "To be passed between threads" );
	}
	catch ( ... )
	{
		teptr = std::current_exception();
	}
}

int main( int argc , char **argv )
{
	std::thread mythread( f );
	mythread.join();

	if ( teptr )
	{
		try
		{
			std::rethrow_exception( teptr );
		}
		catch ( const std::exception &ex )
		{
			std::cerr << "Thread exited with exception: " << ex.what() << "\n";
		}
	}

	return 0;
}

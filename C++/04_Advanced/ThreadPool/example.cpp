#include <iostream>
#include <vector>
#include <chrono>

#include "ThreadPool.h"

int print_hello(int i)
{
	for(int x=0;x<100;x++)
	{
		std::cout << "hello " << i << std::endl;
		std::this_thread::sleep_for(std::chrono::seconds(1));
		std::cout << "world " << i << std::endl;
	}
	return i*i;
}

int main()
{

	ThreadPool pool( 4 );
	std::vector<std::future<int> > results;

	for (int i = 0; i < 8; ++i)
	{
		results.emplace_back( pool.enqueue( print_hello,i ) );
	}

	for (auto && result : results)
		std::cout << result.get() << ' ';
	std::cout << std::endl;

	return 0;
}

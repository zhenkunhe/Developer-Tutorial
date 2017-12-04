#include <locale>
#include <sstream>
#include <iostream>

namespace
{
	struct DecimalSeparator : std::numpunct<char>
	{
			char do_decimal_point() const
			{
				return '.';
			}
	};
}

struct test
{
		void dump()
		{
			std::stringstream ss;
			const static std::locale loc( std::locale() , new DecimalSeparator );
			ss.imbue( loc );
			ss << "test " << 1.0;
			std::cout << ss.str() << std::endl;
		}
		~test()
		{
			dump();
		}
};

static test t;

int main()
{
	t.dump();
	return 0;
}

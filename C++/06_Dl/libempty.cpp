// libempty.cpp follows. Compile with g++ -std=c++0x libempty.cpp -o libempty.so -fPIC -shared
#include <iostream>
#include <vector>

using namespace std;

class Base
{
	public:
    	virtual void init(){};
};

class Foo: public Base
{	
    void init()
    {
		static int nx = 0;
        static vector<float> ns = { 0.f, 0.75f, 0.67f, 0.87f };
    }
	void test()
	{
		 static vector<float> ns = { 0.f, 0.75f, 0.67f, 0.87f };
	}
};

void newBase() 
{
	Foo x;
}

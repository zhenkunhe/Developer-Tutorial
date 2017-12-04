#include <stdlib.h>
#include <iostream>

using namespace std;

class A
{
	public:
		A(){};
		virtual ~A(){};
    void func1()
    {
      cout << "A:func1" << endl;
    }
    virtual void func2()
    {
      cout << "A:func2" << endl;
    }
		virtual void func3() = 0;
};

class B:public A
{
	public:
		B(){};
		virtual ~B(){};
    void func1(){
      cout << "B:func1" << endl;
    }
		void func2(){
			cout << "B:func2" << endl;
		}
    void func3(){
      cout << "B:func3" << endl;
    }
};

int main()
{
	B b;
  //A a; compile error
	A* a = new B();

	a->func1();
	b.func1();

  a->func2();
  b.func2();

  a->func3();
  b.func3();

	return 0;
}

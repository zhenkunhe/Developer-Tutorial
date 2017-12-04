#include <cstdlib>
#include <future>
#include <iostream>
#include <vector>
using namespace std;

class TestFuture
{
public:
	int a, b;
	vector<future <int> > ran;

	TestFuture(int i = 1, int j = 2)
	{
		a = i;
		b = j;
	}

	int thread_A(int x, int y)
	{
		cout << "thread_A x:" << x << endl;
		cout << "thread_A y:" << y << endl;
    return x + y;
	}

  int thread_B(int x, int y)
  {
    cout << "thread_B x:" << x << endl;
    cout << "thread_B y:" << y << endl;
    return x + y;
  }

	void run()
	{
	  int c = 5;
    //兩種寫法：

    //若要用async內建格式,參數傳遞要加上ref()
    //此時傳入的參數會複製一個reference再傳入
    //傳入的參數如果是區域變數(會被release掉),有很大風險
    //接收可加可不加＆,看你要call by value 還是call by reference
    //當接收端加上&就是call by reference,則此處為雙重call by reference
		ran.push_back(
      async(launch::async, &TestFuture::thread_A, this, ref(c), ref(b))
    );
    //若要用匿名函式,則傳入參數要為const或等效於const
    //此時傳入的參數會複製一個value再傳入
    //接收端也必須只能call by value
		ran.push_back(
			async(launch::async, [=] {return this->thread_B(c, 4);} )
		);
	}
};

int main()
{
	TestFuture test_future(3, 4);
	test_future.run();
	cout << "thread_A result:" << test_future.ran[0].get() << endl;
	cout << "thread_B result:" << test_future.ran[1].get() << endl;
	return 0;
}

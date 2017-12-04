#include <iostream>
#include <utility>
#include <thread>
#include <chrono>
#include <functional>
#include <atomic>

void f1(int n)
{
    for (int i = 0; i < 5; ++i) {
        std::cout << "Thread " << n << " executing\n";
        std::this_thread::sleep_for(std::chrono::milliseconds(10));
    }
}

void f2(int& n)
{
    for (int i = 0; i < 5; ++i) {
        std::cout << "Thread 2 executing\n";
        ++n;
        std::this_thread::sleep_for(std::chrono::milliseconds(10));
    }
}

void f3(int* n)
{
    for (int i = 0; i < 5; ++i) {
        std::cout << "Thread 3 executing\n";
        *n=*n+1;
        std::this_thread::sleep_for(std::chrono::milliseconds(10));
    }
}

int main()
{
    int n = 0;
    int* p_n = new int(0);
    std::thread t1; // t1 is not a thread
    t1 = std::thread(f3, p_n);
    //std::thread t2(f1, n + 1); // pass by value
    //std::thread t3(f2, std::ref(n)); // pass by reference
    //std::thread t4(std::move(t3)); // t4 is now running f2(). t3 is no longer a thread
    t1.join();
    //t4.join();
    std::cout << "Final value of n is " << *p_n << '\n';
}

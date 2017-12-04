#include <stdio.h>
#include <pthread.h>
#include <mutex>

pthread_mutex_t mtx = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t fakeCond = PTHREAD_COND_INITIALIZER;
int avail = 0;

void producer(int timeInMs)
{
    int s;

    s = pthread_mutex_lock(&mtx);
    
    if (s != 0)	printf("pthread_mutex_lock fail.");
    avail++;
    
    s = pthread_mutex_unlock(&mtx);
    if (s != 0)	printf("pthread_mutex_lock fail.");
}

void* fun(void* arg)
{
    printf("\nIn thread\n");
    mywait(5000);
}

int main()
{
    pthread_t thread,thread2;
    pthread_create(&thread, NULL, fun, NULL);
}

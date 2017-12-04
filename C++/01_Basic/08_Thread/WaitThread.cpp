#include <stdio.h>
#include <pthread.h>
#include <mutex>
#include <sys/time.h>

pthread_mutex_t fakeMutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t fakeCond = PTHREAD_COND_INITIALIZER;

void mywait(int timeInMs)
{;
    struct timeval now;
    struct timespec timeToWait;
    int rt;

    gettimeofday(&now, NULL);
    timeToWait.tv_sec = time(NULL) + timeInMs / 1000;
    timeToWait.tv_nsec = now.tv_usec * 1000 + 1000 * 1000 * (timeInMs % 1000);
    timeToWait.tv_sec += timeToWait.tv_nsec / (1000 * 1000 * 1000);
    timeToWait.tv_nsec %= (1000 * 1000 * 1000);

    pthread_mutex_lock(&fakeMutex);
    rt = pthread_cond_timedwait(&fakeCond, &fakeMutex, &timeToWait);
    pthread_mutex_unlock(&fakeMutex);
    printf("\nDone\n");
}

void* fun(void* arg)
{
    printf("\nIn thread\n");
    mywait(5000);
}

int main()
{
    pthread_t thread,thread2;
    void *ret;

    pthread_create(&thread, NULL, fun, NULL);
    pthread_join(thread,&ret);
}

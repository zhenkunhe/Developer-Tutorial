#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

typedef struct ticket_lock_s
{
    pthread_cond_t cond;
    pthread_mutex_t mutex;
    unsigned long queue_head;
    unsigned long queue_tail;
} ticket_lock_t;

void ticket_lock(ticket_lock_t *ticket)
{
    unsigned long queue_me;

    pthread_mutex_lock(&ticket->mutex);
    queue_me = ticket->queue_tail++;
	printf("Thread ID = %ld\twill wait.\n",(long)pthread_self());
    while (queue_me != ticket->queue_head)
    {
        pthread_cond_wait(&ticket->cond, &ticket->mutex);
    }
    pthread_mutex_unlock(&ticket->mutex);
}

void ticket_unlock(ticket_lock_t *ticket)
{
    pthread_mutex_lock(&ticket->mutex);
    ticket->queue_head++;
    pthread_cond_broadcast(&ticket->cond);
    pthread_mutex_unlock(&ticket->mutex);
}

void ticket_mutex_init(ticket_lock_t *t_mutex)
{
  t_mutex->mutex = PTHREAD_MUTEX_INITIALIZER;
  t_mutex->cond = PTHREAD_COND_INITIALIZER;
  t_mutex->queue_head = 0;
  t_mutex->queue_tail = 0;
}

static ticket_lock_t fifo_mutex;

static void * threadFunc(void *arg)
{
    int cnt = atoi((char *) arg);

    for (int j = 0; j < cnt; j++)
    {
        sleep(1);
        ticket_lock(&fifo_mutex);
        printf("Thread ID = %ld\tDo some thing.\n",(long)pthread_self());
        ticket_unlock(&fifo_mutex);
    }

    return NULL;
}

int main(int argc, char *argv[])
{
    ticket_mutex_init(&fifo_mutex);
    pthread_t tid;
    int s;

    /* Create all threads */
    for (int j = 1; j < argc; j++)
    {
        s = pthread_create(&tid, NULL, threadFunc, argv[j]);
        if (s != 0)	printf("pthread_create fail\n");
    }

    for (;;)
    {
    	sleep(100);
    }

    exit(EXIT_SUCCESS);
}



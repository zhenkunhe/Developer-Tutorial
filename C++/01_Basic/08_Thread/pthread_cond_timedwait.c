#include<stdio.h>
#include<sys/time.h>
#include<unistd.h>
#include<pthread.h>
#include <errno.h>

pthread_t thread;
pthread_cond_t cond;
pthread_mutex_t mutex;
bool flag = true;

void * thr_fn( void * arg )
{
	struct timeval now;
	struct timespec outtime;
	pthread_mutex_lock( &mutex );
	//Not put this code in there
	//outtime.tv_sec = time(NULL) + 5;
	//outtime.tv_nsec = 0;
	while ( flag )
	{
		printf( ".\n" );
		gettimeofday( &now , NULL );
		outtime.tv_sec = time(NULL) + 5;
		outtime.tv_nsec = 0;

		printf( "%li\n", outtime.tv_sec);
		pthread_cond_timedwait( &cond , &mutex , &outtime );
	}
	pthread_mutex_unlock( &mutex );
	printf( "thread exit\n" );
}

int main()
{
	pthread_mutex_init( &mutex , NULL );
	pthread_cond_init( &cond , NULL );
	if ( 0 != pthread_create( &thread , NULL , thr_fn , NULL ) )
	{
		printf( "error when create pthread,%d\n" , errno );
		return 1;
	}
	char c;
	while ( (c = getchar()) != 'q' )
		;
	printf( "Now terminate the thread!\n" );
	flag = false;
	pthread_mutex_lock( &mutex );
	pthread_cond_signal( &cond );
	pthread_mutex_unlock( &mutex );
	printf( "Wait for thread to exit\n" );
	pthread_join( thread , NULL );
	printf( "Bye\n" );
	return 0;
}

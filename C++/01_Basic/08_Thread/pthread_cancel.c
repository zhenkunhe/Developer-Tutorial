#include <stdio.h>
#include <unistd.h>
#include <pthread.h>

long long int count = 0;

void *func( void *param )
{
	pthread_setcanceltype( PTHREAD_CANCEL_ASYNCHRONOUS , NULL );
	printf( "thread begin...\n" );

	while ( 1 )
	{
		count++;
	}
	printf( "thread end...\n" );

}

int main( int argc , char *argv[] )
{
	int i;
	pthread_t thrid; // start thread

	if ( pthread_create( &thrid , NULL , func , NULL ) )
	{
		printf( "pthread_create error\n" );
		return -1;
	}
// sleep
	for (i = 0; i < 5; i++)
	{
		sleep( 1 );
		printf( "count=%lld\n" , count );
	}
// pthread cancel test
	if ( !pthread_cancel( thrid ) ) printf( "pthread_cancel OK\n" );

	for (i = 0; i < 5; i++)
	{
		sleep( 1 );
		printf( "count=%lld\n" , count );
	}
}

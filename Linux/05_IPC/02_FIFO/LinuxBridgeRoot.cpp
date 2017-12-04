/*
 * LinuxBridgeRoot.cpp
 *
 *  Created on: Jul 25, 2015
 *      Author: ivan_lee
 */

/*
#include <python2.7/Python.h>
#include "Duplex_FIFO.h"

#define ALLJOYN_FIFO_IN "/tmp/AllJoyn/Sensor/FIFO_IN"
#define ALLJOYN_FIFO_OUT "/tmp/AllJoyn/Sensor/FIFO_OUT"



int main(int argc, char *argv[])
{
	iDuplex_FIFO AllJoyn_PIPO(ALLJOYN_FIFO_IN, ALLJOYN_FIFO_OUT);

	AllJoyn_PIPO.run();

  Py_SetProgramName(argv[0]);
  Py_Initialize();
  PyRun_SimpleString("from time import time,ctime\n"
                     "print 'Today is',ctime(time())\n");
  Py_Finalize();

  return 0;
}

#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>

int fd[2];//File descriptor for creating a pipe

//This function continously reads fd[0] for any input data byte
//If available, prints

void *reader(void*)
{
   while(1){
      char    ch;
      int     result;

      result = read (fd[0],&ch,1);
      if (result != 1) {
        perror("read");
        exit(3);
      }

      printf ("Reader: %c\n", ch);   }
}

//This function continously writes Alphabet into fd[1]
//Waits if no more space is available

void *writer(void*)
{
   int     result;
   char    ch='A';

   while(1){
       result = write (fd[1], &ch,1);
       if (result != 1){
           perror ("write");
           exit (2);
       }

       printf ("Writer: %c\n", ch);
       if(ch == 'Z')
         ch = 'A'-1;

       ch++;
   }
}

int main()
{
   pthread_t       tid1,tid2;
   int             result;

   result = pipe (fd);
   if (result < 0){
       perror("pipe ");
       exit(1);
   }

   pthread_create(&tid1,NULL,reader,NULL);
   pthread_create(&tid2,NULL,writer,NULL);

   pthread_join(tid1,NULL);
   pthread_join(tid2,NULL);


   return 0 ;
}


#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>

int main(int argc, const char **argv)
{
   int shmid;
   // give your shared memory an id, anything will do
   key_t key = 123456;
   char *shared_memory;

   // Setup shared memory, 11 is the size
   if ((shmid = shmget(key, 11, IPC_CREAT | 0666)) < 0)
   {
      printf("Error getting shared memory id");
      exit(1);
   }
   // Attached shared memory
   if ((shared_memory = shmat(shmid, NULL, 0)) == (char *) -1)
   {
      printf("Error attaching shared memory id");
      exit(1);
   }
   // copy "hello world" to shared memory
   memcpy(shared_memory, "Hello World", sizeof("Hello World"));
   // sleep so there is enough time to run the reader!
   sleep(10);
   // Detach and remove shared memory
   shmdt(shmid);
   shmctl(shmid, IPC_RMID, NULL);
}
*/


#include "unistd.h"
#include "stdio.h"
#include "sys/types.h"
#include "sys/stat.h"
#include "errno.h"
#include "fcntl.h"
#include "Duplex_FIFO.h"
#include "pthread.h"

#define ALLJOYN_FIFO_IN "/tmp/ARDUINO_FIFO_IN"
#define ALLJOYN_FIFO_OUT "/tmp/ARDUINO_FIFO_OUT"

iDuplex_FIFO *pDuplexFiFoObj = NULL ;

void ReceiveEvt(const char* Msg)
{
	printf("Rec evt : %s ()\n", Msg);
	//pDuplexFiFoObj->write_fifo(Msg) ;
}

void *MonitorArduinoEvt(void*)
{
	pDuplexFiFoObj->Start();
	return NULL ;
}


int main(int argc, const char **argv)
{
   pthread_t       tid_client;

   pDuplexFiFoObj = new iDuplex_FIFO(ALLJOYN_FIFO_IN, ALLJOYN_FIFO_OUT, ReceiveEvt) ;
   pthread_create(&tid_client,NULL,MonitorArduinoEvt,NULL);
   pthread_join(tid_client,NULL);

   if (pDuplexFiFoObj)
	   delete pDuplexFiFoObj ;
   return 0 ;
}

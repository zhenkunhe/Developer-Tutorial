#include <sys/types.h>  
#include <sys/ipc.h>  
#include <sys/msg.h>
#include <sys/sem.h>
#include <sys/shm.h>
#include <stdio.h>  
#include <stdlib.h>  
#include <unistd.h>  
#include <string.h>
#include "lajp_MsgQ.h"
 
#define MSG_MAX 8192 
 
struct message  
{  
    long msg_type;
    char msg_text [ MSG_MAX ];
};
 
JNIEXPORT jint JNICALL Java_lajp_MsgQ_msgget( JNIEnv *env, jclass obj, jint key )
{
    jint msqid;
    if ( ( msqid = msgget ( key, IPC_CREAT | 0666 ) ) == -1 )
    {
        perror ( "[JNI ERROR]msgget Error" ) ;
    }

    return msqid;
}
 
JNIEXPORT jint JNICALL Java_lajp_MsgQ_msgsnd( JNIEnv *env, jclass obj, jint msqid, jint mstype, jbyteArray msg, jint mslen )
{
    if ( MSG_MAX < mslen )
    {
        perror ( "[JNI ERROR]msgsnd Error: jbyteArray msg too big." ) ;
    }
 
    struct message msgq;
    msgq. msg_type = mstype;
    ( *env ) ->GetByteArrayRegion ( env, msg, 0 , mslen, msgq. msg_text ) ;
 
    int ret;
    if ( ( ret = msgsnd ( msqid, &msgq, mslen, 0 ) ) < 0 )  
    {  
        perror ( "[JNI ERROR]msgsnd Error" ) ;  
    }
    return ret;
}
 
JNIEXPORT jint JNICALL Java_lajp_MsgQ_msgrcv
  ( JNIEnv *env, jclass obj, jint msqid, jbyteArray msg, jint mslen, jint mstype )
{
    struct message msgq;
    msgq. msg_type = mstype;
 
    int readmslen;
    if ( ( readmslen = msgrcv ( msqid, &msgq, MSG_MAX, mstype, 0 ) ) < 0 )  
    {  
        perror ( "[JNI ERROR]msgrcv Error" ) ;  
    }
 
    if ( mslen < readmslen )
    {
        perror ( "[JNI ERROR]msgrcv Error: jbyteArray msg too small." ) ;
    }

    ( *env ) ->SetByteArrayRegion ( env, msg, 0 , readmslen, msgq. msg_text ) ;

    return readmslen;
}


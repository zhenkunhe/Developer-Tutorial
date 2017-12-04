package lajp;

public class MsgQ
{    
    public native static int msgget ( int msg_key ) ;

    public native static int msgsnd ( int msqid, int type, byte [ ] msg, int len ) ;

    public native static int msgrcv ( int msqid, byte [ ] msg, int len, int type ) ;
}

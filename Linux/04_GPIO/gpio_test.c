#include <stdio.h>
#include <fcntl.h>
#include <poll.h>
#include <unistd.h>

int main()
{
    int fd=open("/sys/class/gpio/gpio439/value",O_RDONLY);
    if(fd<0)
    {
        perror("open '/sys/class/gpio/gpio439/value' failed!\n");  
        return -1;
    }
    struct pollfd fds[1];
    fds[0].fd=fd;
    fds[0].events=POLLPRI;
    printf("it will monitor gpio439 (wireless button) \n"\
            "make sure the value of /sys/class/gpio/gpio439/edge is falling/rising/both \n"\
            "if /sys/class/gpio/gpio439/edge is none, it will not detect the trigger event \n"\
            "you can use below command to modify the edge value \n"\
            "    $ sudo bash -c \"echo both > edge\" \n");
    while(1)
    {
        /*
         * need to do below command to enable gpio trigger event
         * $ sudo bash -c "echo both > /sys/class/gpio/gpio439/edge"
        */
        printf("\nstart to poll() : \n");
        if(poll(fds,1,-1)==-1)
        {
            perror("poll failed!\n");
            return -1;
        }
        printf("found key event\n");
        if(fds[0].revents&POLLPRI)
        {
            if(lseek(fd,0,SEEK_SET)==-1)
            {
                perror("lseek failed!\n");
                return -1;
            }
            char buffer[16];
            int len;
            if((len=read(fd,buffer,sizeof(buffer)))==-1)
            {
                perror("read failed!\n");
                return -1;
            }
            buffer[len]=0;
            printf("current gpio value : %s",buffer);
        }
    }
    return 0;
}

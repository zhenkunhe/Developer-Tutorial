#ifndef _MESSAGE_RECEVIER_H
#define _MESSAGE_RECEVIER_H

class MessageRecevier
{
	public:
		typedef void(MessageRecevier::*ReplyHandler)(int* paramerter);
};

#endif /* _MESSAGE_RECEVIER_H */

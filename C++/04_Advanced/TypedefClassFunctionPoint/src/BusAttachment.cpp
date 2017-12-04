#include "BusAttachment.h"

#include "AlexGateway.h"

BusAttachment::BusAttachment( void )
{

}

BusAttachment::~BusAttachment( void )
{

}

void BusAttachment::RegisterReplyHandler( MessageRecevier* pMessage_recevier ,MessageRecevier::ReplyHandler reply_handler )
{
	int paramerter = 1;
	(pMessage_recevier->*reply_handler)(&paramerter);
}

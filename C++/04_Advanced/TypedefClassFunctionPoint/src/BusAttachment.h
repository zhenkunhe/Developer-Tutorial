#ifndef _VOICE_SERVICE_H
#define _VOICE_SERVICE_H

#include "MessageRecevier.h"

class BusAttachment
{
	public:
		BusAttachment( void );
		virtual ~BusAttachment( void );

		void RegisterReplyHandler( MessageRecevier* pMessage_recevier ,MessageRecevier::ReplyHandler reply_handler);
};

#endif /* _VOICE_SERVICE_H */

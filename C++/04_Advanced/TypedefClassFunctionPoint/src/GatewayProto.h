#ifndef _GATEWAY_PROTO_H
#define _GATEWAY_PROTO_H
#include <stdio.h>

#include "MessageRecevier.h"
#include "BusAttachment.h"

class GatewayProto : public MessageRecevier
{
	public:
		GatewayProto( int num );
		virtual ~GatewayProto( void );
		void init( BusAttachment* pBus );

	protected:
		virtual void Start( void ) = 0;
		virtual void Finish( void ) = 0;

	private:
		void ReplyHandlerA( int* pNum );
		void ReplyHandlerB( int* pNum );
		BusAttachment* m_pBus;
		int m_number;
};

#endif /* _GATEWAY_PROTO_H */

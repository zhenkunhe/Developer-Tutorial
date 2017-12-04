#include "GatewayProto.h"

GatewayProto::GatewayProto(int num)
{
	m_pBus = 0;
	m_number = num;
}

GatewayProto::~GatewayProto( void )
{

}

void GatewayProto::init( BusAttachment* pBus )
{
	m_pBus = pBus;
	m_pBus->RegisterReplyHandler( this , static_cast<MessageRecevier::ReplyHandler>( &GatewayProto::ReplyHandlerA ) );
}

void GatewayProto::ReplyHandlerA( int* pNum )
{
	Start();
	printf("pNum = %d\n",*pNum);
	printf("m_number = %d\n",m_number);
}

void GatewayProto::ReplyHandlerB( int* pNum )
{
	Finish();
}

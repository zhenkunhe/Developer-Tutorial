/** 
 *  @file    EventHandler.cpp
 *  @author  alex
 *  @date    2017年2月21日 
 *  @version 1.0 
 *  
 *  @brief <brief>
 *
 *  @section DESCRIPTION
 *  
 *  Your description
 *
 */
#include "gateway/EventHandler.hpp"
#include <iostream>
#include "zmq/zmsg.hpp"

using namespace std;

thread EventHandler::eventProxyThread;
vector<thread> EventHandler::eventSubscribeThreads;

//static
void EventHandler::Start( context_t* context )
{
	eventProxyThread = thread( InitEventProxy , context );
}

//static
void EventHandler::Stop()
{
	eventProxyThread.join();
}

//static
void* EventHandler::InitEventProxy( context_t* context )
{
	socket_t xsub( *context , ZMQ_XSUB );
	xsub.bind( EVENT_INPROC_PUBLISHER_PATH );

	socket_t xpub( *context , ZMQ_XPUB );
	xpub.bind( EVENT_INPROC_SUBSCRIBER_PATH );

	proxy( xsub , xpub , NULL );
}

//static
void* EventHandler::InitEventSubscribe( context_t* context )
{
	socket_t subscriber( *context , ZMQ_SUB );
	subscriber.connect( EVENT_INPROC_SUBSCRIBER_PATH );
	subscriber.setsockopt( ZMQ_SUBSCRIBE , "" , 0 );

	while ( true )
	{
		zmsg msg;
		msg.recv( subscriber );
		cout << "parts:" << msg.parts() << endl;
		cout << "subscriber Event Name:" << (char*) msg.pop_front().c_str() << endl;
		cout << "subscriber Message:" << (char*) msg.pop_front().c_str() << endl;
	}
}

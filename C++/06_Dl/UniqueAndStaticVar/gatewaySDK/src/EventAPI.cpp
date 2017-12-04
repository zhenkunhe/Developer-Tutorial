/** 
 *  @file    EventAPI.cpp
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

#include "gateway/EventAPI.h"
#include "gateway/EventHandler.hpp"
#include "gateway/Gateway.hpp"
#include "zmq/zmq.hpp"
#include "zmq/zmsg.hpp"

using namespace zmq;

void SentEvent( const char* eventName , const char* message )
{
	socket_t publisher( *Gateway::GetContext() , ZMQ_PUB );
	publisher.connect( EVENT_INPROC_PUBLISHER_PATH );

	zmsg msg( eventName );
	msg.push_back( (char*)message );
	msg.send( publisher );
}

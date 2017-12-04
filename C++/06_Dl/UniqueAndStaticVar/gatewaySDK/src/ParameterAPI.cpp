/** 
 *  @file    ParameterHandle.cpp

 *  @author  alex
 *  @date    2017年2月20日 
 *  @version 1.0 
 *  
 *  @brief <brief>
 *
 *  @section DESCRIPTION
 *  
 *  Your description
 *
 */

#include <gateway/ParameterAPI.h>
#include <gateway/ParameterHandler.hpp>
#include "gateway/Gateway.hpp"
#include "json/json.hpp"
#include "zmq/zmq.hpp"
#include "zmq/zmsg.hpp"

using json = nlohmann::json;
using namespace zmq;

char* HandleParameter( const char* action , const char* parameterName , const char* parameterValue )
{
	socket_t client( *Gateway::GetContext() , ZMQ_REQ );
	client.connect( PARAMETER_INPROC_CLIENTS_PATH );

	json jsonMsg;
	jsonMsg[PARAMETER_ACTION] = (action == NULL ? "" : action);
	jsonMsg[PARAMETER_NAME] = (parameterName == NULL ? "" : parameterName);
	jsonMsg[PARAMETER_VALUE] = (parameterValue == NULL ? "" : parameterValue);

	zmsg msg( jsonMsg.dump().c_str() );
	msg.send( client );
	msg.recv( client );

	return msg.body();
}

void SetParameter( const char* parameterName , const char* parameterValue )
{
	HandleParameter( PARAMETER_ACTION_SET , parameterName , parameterValue );
}

char* GetParameter( const char* parameterName )
{
	return HandleParameter( PARAMETER_ACTION_GET , parameterName , NULL );
}


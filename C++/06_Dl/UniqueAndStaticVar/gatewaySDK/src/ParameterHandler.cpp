/** 
 *  @file    Parameter.cpp
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
#include <gateway/ParameterHandler.hpp>
#include <iostream>
#include "json/json.hpp"
#include "zmq/zmsg.hpp"

using json = nlohmann::json;

thread ParameterHandler::parameterProxyThread;
ThreadPool ParameterHandler::parameterResponseThreads( PARAMETER_RESPONSE_THREAD_SIZE );
map<string, string> ParameterHandler::parameterMap;

//static
void ParameterHandler::Start( context_t* context )
{
	parameterProxyThread = thread( InitParameterProxy , context );
}

//static
void ParameterHandler::Stop()
{
	parameterProxyThread.join();
}

//static
void* ParameterHandler::InitParameterProxy( context_t* context )
{
	socket_t router( *context , ZMQ_ROUTER );
	router.bind( PARAMETER_INPROC_CLIENTS_PATH );

	socket_t dealer( *context , ZMQ_DEALER );
	dealer.bind( PARAMETER_INPROC_WORKERS_PATH );

	for (int threadNum = 0; threadNum < PARAMETER_RESPONSE_THREAD_SIZE; threadNum++)
	{
		parameterResponseThreads.enqueue( InitParameterResponse , context );
	}

	proxy( router , dealer , NULL );
}

//static
void* ParameterHandler::InitParameterResponse( context_t* context )
{
	socket_t worker( *context , ZMQ_REP );
	worker.connect( PARAMETER_INPROC_WORKERS_PATH );

	while ( true )
	{
		zmsg msg;
		msg.recv( worker );

		json jsonMsg = json::parse( msg.body() );
		if ( jsonMsg[PARAMETER_ACTION].get<string>() == PARAMETER_ACTION_SET )
		{
			parameterMap[jsonMsg[PARAMETER_NAME].get<string>()] = jsonMsg[PARAMETER_VALUE].get<string>();
			msg.body_set( "success" );
		}
		else if ( jsonMsg[PARAMETER_ACTION].get<string>() == PARAMETER_ACTION_GET )
		{
			string parameterName = jsonMsg[PARAMETER_NAME].get<string>();
			string parameterValue = parameterMap.find( parameterName ) != parameterMap.end() ? parameterMap[parameterName] : "";
			msg.body_set( parameterValue.c_str() );
		}

		msg.send( worker );
	}
}

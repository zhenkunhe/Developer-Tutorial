/** 
 *  @file    Parameter.hpp
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
#ifndef _GATEWAY_PARAMETERHANDLER_HPP_
#define _GATEWAY_PARAMETERHANDLER_HPP_
#define PARAMETER_RESPONSE_THREAD_SIZE 1
#define PARAMETER_INPROC_CLIENTS_PATH "inproc://parameter/clients"
#define PARAMETER_INPROC_WORKERS_PATH "inproc://parameter/workers"
#define PARAMETER_ACTION "action"
#define PARAMETER_ACTION_SET "set"
#define PARAMETER_ACTION_GET "get"
#define PARAMETER_NAME "name"
#define PARAMETER_VALUE "value"

#include <map>
#include "zmq/zmq.hpp"
#include "ThreadPool.hpp"

using namespace std;
using namespace zmq;

class ParameterHandler
{
	public:
		static void Start( context_t* context );
		static void Stop();
		static void* InitParameterProxy( context_t* context );
		static void* InitParameterResponse( context_t* context );

		static thread parameterProxyThread;
		static ThreadPool parameterResponseThreads;
		static map<string, string> parameterMap;
};

#endif /* _GATEWAY_PARAMETERHANDLER_HPP_ */

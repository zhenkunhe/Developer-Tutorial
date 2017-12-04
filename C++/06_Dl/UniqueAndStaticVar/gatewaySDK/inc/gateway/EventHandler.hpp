/** 
 *  @file    EventHandler.hpp
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
#ifndef _GATEWAY_EVENTHANDLER_HPP_
#define _GATEWAY_EVENTHANDLER_HPP_
#define EVENT_INPROC_PUBLISHER_PATH "inproc://event/publisher"
#define EVENT_INPROC_SUBSCRIBER_PATH "inproc://event/subscriber"

#include "zmq/zmq.hpp"
#include "ThreadPool.hpp"

using namespace zmq;

class EventHandler
{
	public:
		static void Start( context_t* context );
		static void Stop();
		static void* InitEventProxy( context_t* context );
		static void* InitEventSubscribe( context_t* context );

		static thread eventProxyThread;
		static vector<thread> eventSubscribeThreads;
};

#endif /* _GATEWAY_EVENTHANDLER_HPP_ */

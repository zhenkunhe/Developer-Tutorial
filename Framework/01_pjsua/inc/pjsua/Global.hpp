#ifndef _GLOBAL_HPP
#define _GLOBAL_HPP

#define SIP_DOMAIN  		"35.165.196.180"
#define SIP_PORT  			5060
#define SIP_USER			"pegahome"
#define SIP_PASSWD  		"pega#1234"
#define RING_TIMES_SEC  	10
#define THREAD_POOL_SIZE  	4
#define DEBUG_LEVEL  		3

#include <ThreadPool.hpp>

class Global
{
	public:
		static ThreadPool pool;
		static vector<future<int>> results;
};

#endif /* _GLOBAL_HPP */

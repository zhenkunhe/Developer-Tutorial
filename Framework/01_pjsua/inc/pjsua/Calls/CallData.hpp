#ifndef _CALL_DATA_HPP
#define _CALL_DATA_HPP

#include <pjsua2.hpp>
using namespace pj;

struct IncomingCallData
{
		bool answer;
		AudioMedia *m_AudioMedia;
		IncomingCallData( bool ans = false ) :
						answer( ans ),
						m_AudioMedia( NULL )
		{
		}
};

struct OutgoingCallData
{
		bool answer;
		OutgoingCallData( bool ans = false ) :
						answer( ans )
		{
		}
};

struct RedirectionCallData
{
		bool answer;
		bool hangup;
		AudioMedia *m_AudioMedia;
		RedirectionCallData( bool ans = false,bool hang = false) :
						answer( ans ),
						hangup( hang ),
						m_AudioMedia( NULL )
		{
		}
};

#endif /* _CALL_DATA_HPP */

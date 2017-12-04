#ifndef _INCOMING_CALL_PEGA_HPP
#define _INCOMING_CALL_PEGA_HPP

#include <pjsua/phone/AccountPega.hpp>

#define RESPONSE_WAV_PATH "response.wav"
#define RECORD_WAV_PATH "record.wav"

using namespace std;
using namespace pj;

class IncomingCall : public Call
{
	public:
		IncomingCall( Account &acc , int call_id = PJSUA_INVALID_ID );

		virtual ~IncomingCall();

		virtual void onCallState( OnCallStateParam &prm );

		virtual void onCallMediaState( OnCallMediaStateParam &prm );

	private:
		AccountPega *m_Account;
};

#endif /* _INCOMING_CALL_PEGA_HPP */

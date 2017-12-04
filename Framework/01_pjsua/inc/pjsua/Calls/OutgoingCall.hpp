#ifndef _OUTGOING_CALL_PEGA_HPP
#define _OUTGOING_CALL_PEGA_HPP

#include <pjsua/phone/AccountPega.hpp>

using namespace std;
using namespace pj;

class OutgoingCall : public Call
{
	public:
		OutgoingCall( Account &acc , int call_id = PJSUA_INVALID_ID );

		virtual ~OutgoingCall();

		virtual void onCallState( OnCallStateParam &prm );

		virtual void onCallMediaState( OnCallMediaStateParam &prm );

	private:
		AccountPega *m_Account;
		AudioMedia *m_AudioMedia;
};

#endif  /* _OUTGOING_CALL_PEGA_HPP */

#ifndef _REDIRECTION_CALL_PEGA_HPP
#define _REDIRECTION_CALL_PEGA_HPP

#include <pjsua/phone/AccountPega.hpp>

using namespace std;
using namespace pj;

class RedirectionCall : public Call
{
	public:
		RedirectionCall( Account &account , int call_id = PJSUA_INVALID_ID );

		virtual ~RedirectionCall();

		virtual void onCallState( OnCallStateParam &prm );

		virtual void onCallMediaState( OnCallMediaStateParam &prm );

	private:
		AccountPega *m_Account;
};

#endif  /*_REDIRECTION_CALL_PEGA_HPP*/

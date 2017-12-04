#ifndef _ACCOUNT_PEGA_HPP
#define _ACCOUNT_PEGA_HPP

#include <iostream>
#include <pjsua2.hpp>

using namespace std;
using namespace pj;

class AccountPega : public Account
{

	public:
		AccountPega();

		virtual ~AccountPega();

		void addCall( Call *call );

		void removeCall( Call *call );

		virtual void onRegState( OnRegStateParam &prm );

		virtual void onIncomingCall( OnIncomingCallParam &iprm );

		vector<Call*> m_calls;
};

#endif /* _ACCOUNT_PEGA_HPP */

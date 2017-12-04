#ifndef _PHONE_HPP
#define _PHONE_HPP

#include <pjsua2.hpp>
#include <memory>
#include <stdexcept>
#include <stdio.h>
#include <string>
#include <pjsua/phone/AccountPega.hpp>

using namespace std;
using namespace pj;

class Phone
{
	public:

		static Phone* getInstance();

		static void DestroyInstance();

		void start( string& domain , string& username , string& passwd );

		auto_ptr<AccountPega> m_account;

		auto_ptr<Endpoint> m_endPoing;

	private:

		Phone();

		virtual ~Phone();

		static Phone* m_instance;
};

#endif /* _PHONE_HPP */

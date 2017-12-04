#include <pjsua/phone/Phone.hpp>
#include <pjsua/Global.hpp>

Phone* Phone::m_instance = NULL;

Phone::Phone()
{
	m_account.reset( new AccountPega );
	m_endPoing.reset( new Endpoint );
}

Phone::~Phone()
{
}

Phone* Phone::getInstance()
{
	if ( !m_instance )
	{
		m_instance = new Phone();
	}
	return m_instance;
}

void Phone::DestroyInstance()
{
	if ( m_instance )
	{
		m_instance->m_endPoing->hangupAllCalls();
		try
		{
			m_instance->m_endPoing->libDestroy();
		}
		catch ( Error &err )
		{
			cout << "Exception: " << err.info() << endl;
		}
		delete m_instance;
		m_instance = NULL;
	}
}

void Phone::start( string& domain , string& username , string& passwd )
{
	try
	{
		m_endPoing->libCreate();

		EpConfig ep_cfg;
		ep_cfg.logConfig.level = DEBUG_LEVEL;

		m_endPoing->libInit( ep_cfg );

		TransportConfig tcfg;
		tcfg.port = SIP_PORT;
		m_endPoing->transportCreate( PJSIP_TRANSPORT_UDP , tcfg );
		m_endPoing->transportCreate( PJSIP_TRANSPORT_TCP , tcfg );

		m_endPoing->libStart();

		AccountConfig acc_cfg;
		acc_cfg.idUri = "sip:" + username + "@" + domain;
		acc_cfg.regConfig.registrarUri = "sip:" + domain;

		AuthCredInfo aci;
		aci.scheme = "digest";
		aci.realm = domain;
		aci.username = username;
		aci.data = passwd;
		aci.dataType = 0;
		acc_cfg.sipConfig.authCreds.push_back( aci );

		m_account->create( acc_cfg );
	}
	catch ( Error &err )
	{
		cout << "Exception: " << err.info() << endl;
	}
}

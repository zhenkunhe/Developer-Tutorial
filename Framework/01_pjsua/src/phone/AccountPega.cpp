#include <pjsua/phone/AccountPega.hpp>
#include <pjsua/Calls/CallData.hpp>
#include <pjsua/phone/Phone.hpp>
#include <pjsua/Global.hpp>
#include <pjsua/Media/AudioMediaPlayerPega.hpp>

#include <pjsua/Calls/IncomingCall.hpp>
#include <pjsua/Calls/RedirectionCall.hpp>

int redirectionCallHandler( Call* call )
{
	CallOpParam prm;
	prm.opt.audioCount = 1;
	prm.opt.videoCount = 0;
	if ( !Phone::getInstance()->m_endPoing->libIsThreadRegistered() )
	{
		Phone::getInstance()->m_endPoing->libRegisterThread( "call" + call->getId() );
	}
	Phone::getInstance()->m_account->addCall( call );
	cout << "test1" << endl;
	call->makeCall( "sip:Louis@" SIP_DOMAIN , prm );

	//如果不到秒數 且 沒有接聽->等
	while ( (call->getInfo().totalDuration.sec < RING_TIMES_SEC) && !((RedirectionCallData*) call->getUserData())->answer )
	{
		cout << "test2" << endl;
		this_thread::sleep_for( chrono::seconds( 1 ) );
	}
	//如果沒有接->掛掉+return 0
	if ( !((RedirectionCallData*) call->getUserData())->answer )
	{
		call->hangup( prm );
		return 0;
	}
	return 1;
}

int incomingCallHandler( Call* call )
{
	CallOpParam prm;
	Call *call_LD = NULL;
	future<int> future;
	if ( !Phone::getInstance()->m_endPoing->libIsThreadRegistered() )
	{
		Phone::getInstance()->m_endPoing->libRegisterThread( "call" + call->getId() );
	}

	//如果不到秒數 且 沒有接聽->響
	while ( (call->getInfo().totalDuration.sec < RING_TIMES_SEC) && !((IncomingCallData*) call->getUserData())->answer )
	{
		prm.statusCode = (pjsip_status_code) PJSIP_SC_RINGING;
		call->answer( prm );
		this_thread::sleep_for( chrono::seconds( 1 ) );
	}

	//如果沒有接->轉發
	if ( !((IncomingCallData*) call->getUserData())->answer )
	{
		call_LD = new RedirectionCall( *Phone::getInstance()->m_account );
		call_LD->setUserData( new RedirectionCallData() );
		future = Global::pool.enqueue( redirectionCallHandler , call_LD );

		//如果轉發沒有人接 -> 留言
		if ( !future.get() )
		{
			cout << "no answer" << endl;
			prm.statusCode = (pjsip_status_code) PJSIP_SC_OK;
			call->answer( prm );

			while ( !((IncomingCallData*) call->getUserData())->m_AudioMedia )
			{
				cout << "Wait IncomingCallData..." << endl;
				this_thread::sleep_for( chrono::seconds( 1 ) );
			}
			AudioMediaRecorder *m_Recorder = new AudioMediaRecorder();
			m_Recorder->createRecorder( RECORD_WAV_PATH );
			AudioMediaPlayerPega *m_Player = new AudioMediaPlayerPega( *(((IncomingCallData*) call->getUserData())->m_AudioMedia) , *m_Recorder );
			m_Player->createPlayer( RESPONSE_WAV_PATH , PJMEDIA_FILE_NO_LOOP );

			m_Player->startTransmit( *(((IncomingCallData*) call->getUserData())->m_AudioMedia) );
		}
		else
		{
			cout << "answer" << endl;
			prm.statusCode = (pjsip_status_code) PJSIP_SC_OK;
			call->answer( prm );
			while ( !((IncomingCallData*) call->getUserData())->m_AudioMedia )
			{
				cout << "Wait IncomingCallData" << endl;
				this_thread::sleep_for( chrono::seconds( 1 ) );
			}
			while ( !((RedirectionCallData*) call_LD->getUserData())->m_AudioMedia )
			{
				cout << "Wait RedirectionCallData" << endl;
				this_thread::sleep_for( chrono::seconds( 1 ) );
			}
			cout << "Final.." << endl;
			(((RedirectionCallData*) call_LD->getUserData())->m_AudioMedia)->startTransmit( *((IncomingCallData*) call->getUserData())->m_AudioMedia );
			(((IncomingCallData*) call->getUserData())->m_AudioMedia)->startTransmit( *((RedirectionCallData*) call_LD->getUserData())->m_AudioMedia );
		}
	}
	//如果有人接聽
	else
	{
		AudDevManager& mgr = Endpoint::instance().audDevManager();
		while ( !((IncomingCallData*) call->getUserData())->m_AudioMedia )
		{
			cout << "Wait IncomingCallData..." << endl;
			this_thread::sleep_for( chrono::seconds( 1 ) );
		}
		((IncomingCallData*) call->getUserData())->m_AudioMedia->startTransmit( mgr.getPlaybackDevMedia() );
		mgr.getCaptureDevMedia().startTransmit( *((IncomingCallData*) call->getUserData())->m_AudioMedia );
	}

	return 0;
}

AccountPega::AccountPega()
{
}

AccountPega::~AccountPega()
{
	cout << "*** Account is being deleted: No of calls=" << m_calls.size() << endl;
}

void AccountPega::addCall( Call *call )
{
	m_calls.push_back( call );
}

void AccountPega::removeCall( Call *call )
{
	for (vector<Call *>::iterator it = m_calls.begin(); it != m_calls.end(); ++it)
	{
		if ( *it == call )
		{
			m_calls.erase( it );
			break;
		}
	}
}

void AccountPega::onRegState( OnRegStateParam &prm )
{
	AccountInfo ai = getInfo();
	cout << (ai.regIsActive ? "*** Register: code=" : "*** Unregister: code=") << prm.code << endl;
}

void AccountPega::onIncomingCall( OnIncomingCallParam &iprm )
{
	Call *call = new IncomingCall( *this , iprm.callId );
	call->setUserData( new IncomingCallData() );
	addCall( call );
	Global::results.emplace_back( Global::pool.enqueue( incomingCallHandler , call ) );
}

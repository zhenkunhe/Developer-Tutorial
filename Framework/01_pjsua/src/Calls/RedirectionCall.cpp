#include <pjsua/Calls/CallData.hpp>
#include <pjsua/Calls/RedirectionCall.hpp>
#include <pjsua/Media/AudioMediaPlayerPega.hpp>

RedirectionCall::RedirectionCall( Account &account , int call_id ) :
				Call( account , call_id ),
				m_Account( (AccountPega *) &account )
{
}

RedirectionCall::~RedirectionCall()
{
}

void RedirectionCall::onCallState( OnCallStateParam &prm )
{
	PJ_UNUSED_ARG( prm );

	CallInfo callInfo = getInfo();
	cout << "*** RedirectionCall: " << callInfo.remoteUri << " [" << callInfo.stateText << "]" << endl;

	if ( callInfo.state == PJSIP_INV_STATE_CONFIRMED )
	{
		((RedirectionCallData*)getUserData())->answer = true;
	}
	if ( callInfo.state == PJSIP_INV_STATE_DISCONNECTED )
	{
		((RedirectionCallData*)getUserData())->hangup = true;
		m_Account->removeCall( this );
		delete this;
	}
}

void RedirectionCall::onCallMediaState( OnCallMediaStateParam &prm )
{
	CallInfo callInfo = getInfo();

	//尋找所有可用的Media
	for (auto callMediaInfo : callInfo.media)
		//若有Audio Media
		if ( callMediaInfo.type == PJMEDIA_TYPE_AUDIO && getMedia( callMediaInfo.index ) )
			((RedirectionCallData *)getUserData())->m_AudioMedia = (AudioMedia *) getMedia( callMediaInfo.index );
}

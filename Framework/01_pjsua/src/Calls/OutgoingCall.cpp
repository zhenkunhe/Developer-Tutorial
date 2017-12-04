#include <pjsua/Calls/CallData.hpp>
#include <pjsua/Calls/OutgoingCall.hpp>
#include <pjsua/Media/AudioMediaPlayerPega.hpp>

OutgoingCall::OutgoingCall( Account &account , int call_id ) :
				Call( account , call_id ),
				m_Account( (AccountPega *) &account ),
				m_AudioMedia( NULL )
{
}

OutgoingCall::~OutgoingCall()
{
}

void OutgoingCall::onCallState( OnCallStateParam &prm )
{
	PJ_UNUSED_ARG( prm );

	CallInfo callInfo = getInfo();
	cout << "*** OutgoingCall: " << callInfo.remoteUri << " [" << callInfo.stateText << "]" << endl;

	if ( callInfo.state == PJSIP_INV_STATE_CONFIRMED )
	{

	}
	if ( callInfo.state == PJSIP_INV_STATE_DISCONNECTED )
	{
		m_Account->removeCall( this );
		delete this;
	}
}

void OutgoingCall::onCallMediaState( OnCallMediaStateParam &prm )
{
	CallInfo callInfo = getInfo();

	//尋找所有可用的Media
	for (auto callMediaInfo : callInfo.media)
	{
		//若有Audio Media
		if ( callMediaInfo.type == PJMEDIA_TYPE_AUDIO && getMedia( callMediaInfo.index ) )
		{
		}
	}
}

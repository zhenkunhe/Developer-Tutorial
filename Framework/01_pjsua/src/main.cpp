#include <pjsua/Calls/CallData.hpp>
#include <iostream>
#include <pjsua/Global.hpp>
#include <pjsua/phone/Phone.hpp>

int main( int argc , char** argv )
{
	int ret = PJ_SUCCESS;

	string domain = SIP_DOMAIN;
	string username = SIP_USER;
	string passwd = SIP_PASSWD;
	Phone::getInstance()->start( domain , username , passwd );

//	cout << "Press ENTER to quit..." << endl;
//	cin.get();
//
//	Call* call1 = Call::lookup( 0 );
//	((CallData*) call1->getUserData())->answer = true;

	cout << "Press ENTER to quit..." << endl;
	cin.get();

	Phone::DestroyInstance();

	return ret;
}

//int main(int argc , char** argv)
//{
//	int ret = PJ_SUCCESS;
//	Endpoint ep;
//
//	try
//	{
//		init( ep );
//		start( ep );
//		addAccount( SIP_USER , SIP_PASSWD );
//
//		cout << "Press ENTER to quit..." << endl;
//		cin.get();
//
////		Call *callLD = new CallPega(*Global::account);
////		Global::account->m_calls.push_back(callLD);
////		CallOpParam prm2(true);
////		prm2.opt.audioCount = 1;
////		prm2.opt.videoCount = 0;
////		callLD->makeCall("sip:Louis@"SIP_DOMAIN, prm2);
////
////		cout << "Press ENTER to quit..." << endl;
////		cin.get();
//
////		CallOpParam prm(true);
////		prm.opt.audioCount = 1;
////		prm.opt.videoCount = 0;
////		Call::lookup(0)->xfer("sip:Louis@"SIP_DOMAIN, prm);
//
////		Call *incall = Call::lookup(0);
////		CallInfo incallCI = incall->getInfo();
////		AudioMedia *incallAM = NULL;
////		for (unsigned i = 0; i < incallCI.media.size(); i++)
////		{
////			if ( incallCI.media[i].type == PJMEDIA_TYPE_AUDIO && incall->getMedia( i ) )
////			{
////				incallAM = (AudioMedia *) incall->getMedia( i );
////			}
////		}
////
////		CallInfo callLDCI = callLD->getInfo();
////		AudioMedia *callLDAM = NULL;
////		for (unsigned i = 0; i < callLDCI.media.size(); i++)
////		{
////			if ( callLDCI.media[i].type == PJMEDIA_TYPE_AUDIO && callLD->getMedia( i ) )
////			{
////				callLDAM = (AudioMedia *) callLD->getMedia( i );
////			}
////		}
////
////		incallAM->startTransmit( *callLDAM );
////		callLDAM->startTransmit( *incallAM );
//
//		Call *incall = Call::lookup( 0 );
//		CallInfo incallCI = incall->getInfo();
//		AudioMedia *incallAM = NULL;
//		AudDevManager& mgr = Endpoint::instance().audDevManager();
//		for (unsigned i = 0; i < incallCI.media.size(); i++)
//		{
//			if ( incallCI.media[i].type == PJMEDIA_TYPE_AUDIO && incall->getMedia( i ) )
//			{
//				incallAM = (AudioMedia *) incall->getMedia( i );
//			}
//		}
//
////		if ( !Global::recorder )
////		{
////			Global::recorder = new AudioMediaRecorder();
////			Global::recorder->createRecorder( "recorder_test_output.wav" );
////		}
////		incallAM->startTransmit( *Global::recorder );
//		incallAM->startTransmit( mgr.getPlaybackDevMedia() );
//		mgr.getCaptureDevMedia().startTransmit( *incallAM );
//
//		cout << "Press ENTER to quit..." << endl;
//		cin.get();
//		delete Global::recorder;
//		system( "python bing.py passwd.wav");
//
//		cout << "Press ENTER to quit..." << endl;
//		cin.get();
//
//		ep.hangupAllCalls();
//		ep.libDestroy();
//	}
//	catch ( Error & err )
//	{
//		cout << "Exception: " << err.info() << endl;
//		ret = 1;
//	}
//
//	return ret;
//}


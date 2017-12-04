#include <pjsua/Media/AudioMediaPlayerPega.hpp>

AudioMediaPlayerPega::AudioMediaPlayerPega( AudioMedia &audioMedia , AudioMediaRecorder &recorder ) :
				m_AudioMedia( &audioMedia ),
				m_Recorder( &recorder ),
				isEof( false )
{
}

AudioMediaPlayerPega::~AudioMediaPlayerPega()
{

}

bool AudioMediaPlayerPega::onEof()
{
	if ( m_AudioMedia && m_Recorder && !isEof )
	{
		cout << "Start Recording..." << endl;
		m_AudioMedia->startTransmit( *m_Recorder );
		isEof = true;
	}
	return true;
}

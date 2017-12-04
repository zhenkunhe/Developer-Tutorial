#ifndef _AUDIO_MEDIA_PLAYER_PEGA_HPP
#define _AUDIO_MEDIA_PLAYER_PEGA_HPP

#include <iostream>
#include <pjsua2.hpp>

using namespace std;
using namespace pj;

class AudioMediaPlayerPega : public AudioMediaPlayer
{
	public:
		AudioMediaPlayerPega( AudioMedia &audioMedia , AudioMediaRecorder &recorder );

		virtual ~AudioMediaPlayerPega();

		virtual bool onEof();

	private:
		AudioMedia *m_AudioMedia;
		AudioMediaRecorder *m_Recorder;
		bool isEof;
};

#endif /* _AUDIO_MEDIA_PLAYER_PEGA_HPP */

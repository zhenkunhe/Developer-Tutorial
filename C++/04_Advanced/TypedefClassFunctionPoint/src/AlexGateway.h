#ifndef _ALEX_PLAY_GSTREAMER_STATUS_LISTENER_H
#define _ALEX_PLAY_GSTREAMER_STATUS_LISTENER_H

#include "GatewayProto.h"

class AlexGateway : public GatewayProto
{
	public:
		AlexGateway( int num );
		virtual ~AlexGateway( void );

	protected:
		virtual void Start( void );
		virtual void Finish( void );
};

#endif /* _ALEX_PLAY_GSTREAMER_STATUS_LISTENER_H */

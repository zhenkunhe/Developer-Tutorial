#include <iostream>
#include <stdio.h>

#include "AlexGateway.h"
#include "BusAttachment.h"

int main( int argc , char* argv[] )
{
	BusAttachment* pBus = new BusAttachment();
	AlexGateway* alex_gateway = new AlexGateway( 999 );
	alex_gateway->init(pBus);
}


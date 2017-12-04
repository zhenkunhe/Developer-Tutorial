#include "AlexGateway.h"

#include <iostream>

AlexGateway::AlexGateway( int num ) :GatewayProto(num)
{

}

AlexGateway::~AlexGateway( void )
{

}

void AlexGateway::Start( void )
{
	std::cout << "start" << std::endl;
}


void AlexGateway::Finish( void )
{
	std::cout << "finish" << std::endl;
}

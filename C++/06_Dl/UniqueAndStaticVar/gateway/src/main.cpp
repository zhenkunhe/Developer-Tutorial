//============================================================================
// Name        : gateway.cpp
// Author      : Alex
// Version     :
// Copyright   : 
// Description :
//============================================================================
#include <iostream>
#include <string>
#include "gateway/Gateway.hpp"

int main( int argc , char *argv[] )
{
	string jsonFilePath , zlogFilePath;
	if ( argv[1] ) jsonFilePath = argv[1];
	if ( argv[2] ) zlogFilePath = argv[2];

	Gateway::SetJsonFilePath( jsonFilePath );
	Gateway::GetInstance();

	while ( true )
	{
		string temp;
		cin >> temp;
		if ( temp == "q" ) break;
		if ( temp == "u" )
		{
			cout << "-----------------Unload-----------------" << endl;
			Gateway::GetInstance()->UnloadModule();
		}
		if ( temp == "l" )
		{
			cout << "-----------------load-----------------" << endl;
			Gateway::GetInstance()->LoadModule();
			Gateway::GetInstance()->StartModule();
		}
	}
	Gateway::DestroyInstance();

	return 0;
}

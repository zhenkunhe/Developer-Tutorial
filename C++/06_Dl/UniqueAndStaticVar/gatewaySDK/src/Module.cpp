/** 
 *  @file    Module.cpp
 *  @author  alex
 *  @date    2017年2月10日 
 *  @version 1.0 
 *  
 *  @brief <brief>
 *
 *  @section DESCRIPTION
 *  
 *  Your description
 *
 */
#include "gateway/Module.hpp"
#include <iostream>

using namespace std;

Module::Module( string const& name , string const& path , string const& permission , string const& version , string const& args ) :
				name_( name ),
				path_( path ),
				permission_( permission ),
				version_( version ),
				args_( args ),
				createAPI( NULL ),
				destroyAPI( NULL ),
				getCallBackMapAPI( NULL ),
				errorAPI( NULL ),
				isLoad_( false )
{
	// TODO Auto-generated constructor stub
}

Module::~Module()
{
	// TODO Auto-generated destructor stub
}

void Module::Load( string const& path )
{
	libHandle.LoadFile( path );
	createAPI = libHandle.LoadFunction<CreateAPI>( MODULE_CREATE_API );
	destroyAPI = libHandle.LoadFunction<DestroyAPI>( MODULE_DESTORY_API );
	getCallBackMapAPI = libHandle.LoadFunction<GetCallBackMapAPI>( MODULE_GETCALLBACKMAP_API );
	errorAPI = libHandle.LoadFunction<ErrorAPI>( MODULE_ERROR_API );
	isLoad_ = true;
}

void Module::Unload()
{
	libHandle.UnloadLibrary();
	createAPI = NULL;
	destroyAPI = NULL;
	getCallBackMapAPI = NULL;
	errorAPI = NULL;
	isLoad_ = false;
}

void Module::Start()
{
	createAPI();
	cout << endl;
	destroyAPI();
	cout << endl;
	getCallBackMapAPI();
	cout << endl;
	errorAPI();
	cout << endl;
}

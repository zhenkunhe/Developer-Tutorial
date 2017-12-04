/** 
 *  @file    Gateway.cpp
 *  @author  alex
 *  @date    2017年2月6日 
 *  @version 1.0 
 *  
 *  @brief <brief>
 *
 *  @section DESCRIPTION
 *  
 *  Your description
 *
 */

#include <iostream>
#include <fstream>
#include "gateway/Gateway.hpp"
#include "gateway/ParameterHandler.hpp"
#include "gateway/EventHandler.hpp"
#include "gateway/ModuleDefine.h"

Gateway* Gateway::instance = NULL;
context_t Gateway::context( 1 );
string Gateway::jsonFilePath;

//static
Gateway* Gateway::GetInstance()
{
	if ( !instance )
	{
		ParameterHandler::Start( &Gateway::context );
		EventHandler::Start( &Gateway::context );
		instance = new Gateway();
	}
	return instance;
}

//static
void Gateway::DestroyInstance()
{
	if ( instance )
	{
		ParameterHandler::Stop();
		EventHandler::Stop();
		delete instance;
		instance = NULL;
	}
	context.close();
}

//static
void Gateway::SetJsonFilePath( string& jsonFilePath )
{
	Gateway::jsonFilePath = jsonFilePath;
}

//static
context_t* Gateway::GetContext()
{
	return &Gateway::context;
}

void Gateway::StartModule()
{
	for (auto& module : modules_)
	{
		module.Start();
		cout << "*****************************" << endl;
	}
}

void Gateway::UnloadModule()
{
	for (auto& module : modules_)
	{
		module.Unload();
	}
}

void Gateway::LoadModule()
{
	for (auto& module : modules_)
	{
		module.Load( module.path_ );
	}
}

//----------------------------------------------------------------------------------------------------------------------------------------

Gateway::Gateway()
{
	LoadJsonFile();
	InitModules();
}

Gateway::~Gateway()
{
}

void Gateway::LoadJsonFile()
{
	ifstream is( jsonFilePath );
	is >> launchJson_;
	numOfModule_ = launchJson_["modules"].size();
}

void Gateway::InitModules()
{
	for (int i = 0; i < numOfModule_; ++i)
	{
		string name = launchJson_["modules"][i][MODULE_NAME].is_null() ? "" : launchJson_["modules"][i][MODULE_NAME].get<string>();
		string path = launchJson_["modules"][i][MODULE_PATH].is_null() ? "" : launchJson_["modules"][i][MODULE_PATH].get<string>();
		string permission = launchJson_["modules"][i][MODULE_PERMISSION].is_null() ? "" : launchJson_["modules"][i][MODULE_PERMISSION].get<string>();
		string version = launchJson_["modules"][i][MODULE_VERSION].is_null() ? "" : launchJson_["modules"][i][MODULE_VERSION].get<string>();
		string args = launchJson_["modules"][i][MODULE_ARGS].is_null() ? "" : launchJson_["modules"][i][MODULE_ARGS].dump();

		modules_.emplace_back( name , path , permission , version , args );
	}
}


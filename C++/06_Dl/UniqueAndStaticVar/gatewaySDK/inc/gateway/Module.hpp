/** 
 *  @file    Module.hpp
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
#ifndef _GATEWAY_MODULE_HPP_
#define _GATEWAY_MODULE_HPP_

#include <string>
#include "gateway/ModuleDefine.h"
#include "gateway/DynamicLoader.hpp"

using namespace std;

class Module
{
	public:
		Module( string const& name = NULL , string const& path = NULL , string const& permission = NULL , string const& version = NULL , string const& args =
				NULL );
		virtual ~Module();
		void Load( string const& path);
		void Unload();
		void Start();

		bool isLoad_;
		string name_;
		string path_;
		string permission_;
		string version_;
		string args_;

		DynamicLoader libHandle;
		CreateAPI createAPI;
		DestroyAPI destroyAPI;
		GetCallBackMapAPI getCallBackMapAPI;
		ErrorAPI errorAPI;
};

#endif /* _GATEWAY_MODULE_HPP_ */

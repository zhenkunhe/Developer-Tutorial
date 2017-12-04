/** 
 *  @file    Gateway.hpp
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
#ifndef _GATEWAY_GATEWAY_HPP_
#define _GATEWAY_GATEWAY_HPP_
#define __cplusplus 201103L

#include <string>
#include <vector>
#include "zmq/zmq.hpp"
#include "gateway/Module.hpp"
#include "json/json.hpp"

using namespace std;
using namespace zmq;
using json = nlohmann::json;

class Gateway
{
	public:
		static Gateway* GetInstance();
		static void DestroyInstance();
		static void SetJsonFilePath( string& jsonFilePath );
		static context_t* GetContext();
		void StartModule();
		void UnloadModule();
		void LoadModule();

	private:
		static Gateway* instance;
		static context_t context;
		static string jsonFilePath;

		Gateway();
		virtual ~Gateway();
		void LoadJsonFile();
		void InitModules();

		json launchJson_;
		int numOfModule_;
		vector<Module> modules_;
};

#endif /* _GATEWAY_GATEWAY_HPP_ */

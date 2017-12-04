//============================================================================
// Name        : samplePublishModule.cpp
// Author      : Alex
// Version     :
// Copyright   : 
// Description : Hello World in C++, Ansi-style
//============================================================================

#include <gateway/ModuleDefine.h>
#include <gateway/ParameterAPI.h>
#include <gateway/EventAPI.h>
#include <UniqueVariable.hpp>
#include <iostream>
#include <sstream>

using namespace std;

static int count = 0;

UniqueVariable unique;

MODULE_EXPORT void create()
{
	cout << "Publish create" << endl;
	//unique.PrintStaticInGlobal();
	//unique.PrintStaticInMember();
	//unique.PrintStaticInLocal();
	unique.PrintUniqueVarible();
	cout << "Publish global variable:" << ++count << endl;
}

MODULE_EXPORT void destroy()
{
	cout << "Publish destroy" << endl;
	//unique.PrintStaticInGlobal();
	//unique.PrintStaticInMember();
	//unique.PrintStaticInLocal();
	unique.PrintUniqueVarible();
}

MODULE_EXPORT void getCallBackMap()
{
	cout << "Publish getCallBackMap" << endl;
}

MODULE_EXPORT void error()
{
	cout << "Publish error" << endl;
}


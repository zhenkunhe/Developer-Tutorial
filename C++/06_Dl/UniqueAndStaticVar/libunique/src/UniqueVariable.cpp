//============================================================================
// Name        : UniqueVariable.cpp
// Author      : Alex
// Version     :
// Copyright   : 
// Description : Hello World in C++, Ansi-style
//============================================================================

#include "../inc/UniqueVariable.hpp"

static int g_count = 0;

int UniqueVariable::m_count = 0;

void UniqueVariable::PrintStaticInGlobal()
{
	cout << "UNIQUE g_count:" << ++g_count << endl;
}

void UniqueVariable::PrintStaticInMember()
{
	cout << "UNIQUE m_count:" << ++m_count << endl;
}

void UniqueVariable::PrintStaticInLocal()
{
	static int staticInLocal;
	cout << "UNIQUE staticInLocal:" << ++staticInLocal << endl;
}

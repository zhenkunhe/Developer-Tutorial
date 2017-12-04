/** 
 *  @file    UniqueVariable.hpp
 *  @author  alex
 *  @date    2017年2月24日 
 *  @version 1.0 
 *  
 *  @brief <brief>
 *
 *  @section DESCRIPTION
 *  
 *  Your description
 *
 */
#ifndef _UNIQUEVARIABLE_HPP_
#define _UNIQUEVARIABLE_HPP_

#include <iostream>
using namespace std;

class UniqueVariable
{
	public:
		UniqueVariable(){}
		~UniqueVariable(){}
		void PrintStaticInGlobal();
		void PrintStaticInMember();
		void PrintStaticInLocal();

		void PrintUniqueVarible();

		static int m_count;
};

inline void UniqueVariable::PrintUniqueVarible()
{
	static int uniqueVariable;
	cout << "UNIQUE uniqueVarible:" << ++uniqueVariable << endl;
}


#endif /* _UNIQUEVARIABLE_HPP_ */

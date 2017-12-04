/** 
 *  @file    DynamicLoader.hpp
 *  @author  alex
 *  @date    2017年2月7日 
 *  @version 1.0 
 *  
 *  @brief <brief>
 *
 *  @section DESCRIPTION
 *  
 *  Your description
 *
 */
#ifndef _GATEWAY_DYNAMICLOADER_HPP_
#define _GATEWAY_DYNAMICLOADER_HPP_
#include <string>
#include <functional>
#include <dlfcn.h>

using namespace std;

class DynamicLoader
{
	public:
		DynamicLoader();

		DynamicLoader( string const& filename );

		virtual ~DynamicLoader();

		void LoadFile( string const& filename );

		template<typename SIG>
		SIG LoadFunction( string const& functionName );

		void UnloadLibrary();

	private:
		void* libHandle_;
};

inline DynamicLoader::DynamicLoader() :
				libHandle_( NULL )
{
}

inline DynamicLoader::DynamicLoader( string const& filename )
{
	LoadFile( filename );
}

inline DynamicLoader::~DynamicLoader()
{
	// TODO Auto-generated destructor stub
}

inline void DynamicLoader::LoadFile( string const& filename )
{
	libHandle_ = dlopen( filename.c_str() , RTLD_LAZY );

	/* Throw Some Error */
	if ( !libHandle_ )
	{
	}
}

template<typename SIG>
inline SIG DynamicLoader::LoadFunction( string const& functionName )
{
	return reinterpret_cast<SIG>( dlsym( libHandle_ , functionName.c_str() ) );
}

inline void DynamicLoader::UnloadLibrary()
{
	dlclose( libHandle_ );
}

#endif /* _GATEWAY_DYNAMICLOADER_HPP_ */

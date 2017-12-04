/** 
 *  @file    api.hpp
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
#ifndef _GATEWAY_MODULEDEFINE_HPP_
#define _GATEWAY_MODULEDEFINE_HPP_
#define MODULE_EXPORT
#define MODULE_NAME "module name"
#define MODULE_PATH "module path"
#define MODULE_PERMISSION "permission"
#define MODULE_VERSION "version"
#define MODULE_ARGS "args"
#define MODULE_CREATE_API "create"
#define MODULE_DESTORY_API "destroy"
#define MODULE_GETCALLBACKMAP_API "getCallBackMap"
#define MODULE_ERROR_API "error"

#ifdef __cplusplus
extern "C"
{
#endif

	typedef void (*CreateAPI)( void );
	typedef void (*DestroyAPI)( void );
	typedef void (*GetCallBackMapAPI)( void );
	typedef void (*ErrorAPI)( void );

	MODULE_EXPORT void create( void );
	MODULE_EXPORT void destroy( void );
	MODULE_EXPORT void getCallBackMap( void );
	MODULE_EXPORT void error( void );

#ifdef __cplusplus
}
#endif

#endif /* _GATEWAY_MODULEDEFINE_HPP_ */

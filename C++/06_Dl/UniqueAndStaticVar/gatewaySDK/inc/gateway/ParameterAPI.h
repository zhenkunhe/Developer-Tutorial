/** 
 *  @file    ParameterHandle.h
 *  @author  alex
 *  @date    2017年2月20日 
 *  @version 1.0 
 *  
 *  @brief <brief>
 *
 *  @section DESCRIPTION
 *  
 *  Your description
 *
 */
#ifndef _GATEWAY_PARAMETERAPI_H_
#define _GATEWAY_PARAMETERAPI_H_

#ifdef __cplusplus
extern "C"
{
#endif

	void SetParameter( const char* parameterName , const char* parameterValue );
	char* GetParameter( const char* parameterName );

#ifdef __cplusplus
}
#endif

#endif /* _GATEWAY_PARAMETERAPI_H_ */

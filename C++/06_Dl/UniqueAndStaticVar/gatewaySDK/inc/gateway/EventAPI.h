/** 
 *  @file    EventAPI.h
 *  @author  alex
 *  @date    2017年2月21日 
 *  @version 1.0 
 *  
 *  @brief <brief>
 *
 *  @section DESCRIPTION
 *  
 *  Your description
 *
 */
#ifndef _GATEWAY_EVENTAPI_H_
#define _GATEWAY_EVENTAPI_H_

#ifdef __cplusplus
extern "C"
{
#endif

	void SentEvent( const char* eventName , const char* message );

#ifdef __cplusplus
}
#endif

#endif /* _GATEWAY_EVENTAPI_H_ */

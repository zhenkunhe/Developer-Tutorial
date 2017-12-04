/*
 * Duplex_FIFO.h
 *
 *  Created on: Jul 26, 2015
 *      Author: ivan_lee
 */

#ifndef DEBUG_SRC_DUPLEX_FIFO_H_
#define DEBUG_SRC_DUPLEX_FIFO_H_

#include <string>



class iDuplex_FIFO
{
	typedef void (*CALL_BACKFUNCTION)(const char*);


public:
	iDuplex_FIFO(const std::string& _FIFO_SIGNATURE_NAME_IN, const std::string& _FIFO_SIGNATURE_NAME_OUT, CALL_BACKFUNCTION pCallBackFn = NULL);
	~iDuplex_FIFO();
	void Start();
	void WriteFiFo(const std::string& str);


private:
	void RemoveFiFo();
	void CreateFiFo();
	void InitFiFo();
	std::string ReadFiFo(std::string& str);
	void PerFormAction();

	bool Status;
	bool bFiFoCreateIn_Status;
	bool bFiFoCreateOut_Status;
	std::string StrFiFo_In;
	std::string StrFiFo_Out;
	CALL_BACKFUNCTION pCallBackFunction ;
};


#endif /* DEBUG_SRC_DUPLEX_FIFO_H_ */

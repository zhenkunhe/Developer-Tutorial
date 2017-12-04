/*
 * Duplex_FIFO.cpp
 *
 *  Created on: Jul 26, 2015
 *      Author: ivan_lee
 */

#include <iostream>
#include <fstream>
#include <cerrno>
#include <sys/stat.h>

#include "Duplex_FIFO.h"

using namespace std;

iDuplex_FIFO::iDuplex_FIFO(const string& _FIFO_SIGNATURE_NAME_IN, const string& _FIFO_SIGNATURE_NAME_OUT, CALL_BACKFUNCTION pCallBackFn)
{
	Status = false ;
	bFiFoCreateIn_Status = false ;
	bFiFoCreateOut_Status = false ;
	pCallBackFunction = NULL ;
	StrFiFo_In = _FIFO_SIGNATURE_NAME_IN;
	StrFiFo_Out = _FIFO_SIGNATURE_NAME_OUT;
	if (pCallBackFn)
		pCallBackFunction = pCallBackFn ;
}

iDuplex_FIFO::~iDuplex_FIFO()
{
	RemoveFiFo();
}

// remove fifos from file system
void iDuplex_FIFO::RemoveFiFo()
{
	int err;
	Status = true;

	if(bFiFoCreateIn_Status)
	{
		// remove fifo_in file if it exists
		err = ::remove(StrFiFo_In.c_str());
		if(err == -1 && errno != ENOENT)
		{
			Status = false;
		}
	}
	if(bFiFoCreateOut_Status)
	{
		// remove fifo_out file if it exists
		err = ::remove(StrFiFo_Out.c_str());
		if(err == -1 && errno != ENOENT)
		{
			Status = false;
		}
	}
}

// create fifo_in and fifo_out
void iDuplex_FIFO::CreateFiFo()
{
	int err;
	bFiFoCreateIn_Status = bFiFoCreateOut_Status = true;

	// create fifo_in
	err = mkfifo(StrFiFo_In.c_str(), S_IRUSR | S_IWUSR | S_IRGRP | S_IWGRP);
	if(err == -1)
	{
		bFiFoCreateIn_Status = false;
	}

	// create fifo_out
	err = mkfifo(StrFiFo_Out.c_str(), S_IRUSR | S_IWUSR | S_IRGRP | S_IWGRP);
	if(err == -1)
	{
		bFiFoCreateOut_Status = false;
	}

	Status = bFiFoCreateIn_Status && bFiFoCreateOut_Status;
}

// initialize fifo files
void iDuplex_FIFO::InitFiFo()
{
	bFiFoCreateIn_Status = bFiFoCreateOut_Status = true;
	Status = true;

	RemoveFiFo();
	if(!Status)
		return;

	CreateFiFo();
}

// reads string from fifo_in
string iDuplex_FIFO::ReadFiFo(string& str)
{
	ifstream ifs(StrFiFo_In.c_str()); // ifstream for reading

	// test ifs
	if(!ifs.good())
	{
		cerr << "Cannot read from input stream.\n";
		Status = false;
		return str;
	}

	// read data
	ifs >> str;

	return str;
}

// writes string to fifo_out
void iDuplex_FIFO::WriteFiFo(const string& str)
{
	Status = true;

	ofstream ofs(StrFiFo_Out.c_str()); // ofstream for writing

	// test ofs
	if(!ofs.good())
	{
		cerr << "Cannot write to output stream.\n";
		Status = false;
		return;
	}

	// write data
	ofs << str;
}

// reads from fifo_in, writes to fifo_out
void iDuplex_FIFO::PerFormAction()
{
	string str;

	// read string from fifo_in
	ReadFiFo(str);
	if(!Status)
		return;

	// terminate server
	if(str.compare("Terminate") == 0)
	{
		cerr << "Terminating ...\n";
		Status = false;
	}
	else
	{
	// write string to fifo_out
		//write_fifo(str);
		pCallBackFunction((const char*)str.c_str()) ;
	}
	if(!Status)
		return;
}

// read data from fifo_in in a loop
void iDuplex_FIFO::Start()
{
	InitFiFo();
	if(!Status)
		return;

	while(true)
	{
		PerFormAction();
		if(!Status)
			break;
	}
}

#include <iostream>
#include <fstream>
#include <sstream>
using namespace std;

int main()
{
	string filePath = "test.txt";
	ifstream inFileStream( filePath.c_str() );
	string s;
	if ( inFileStream.good() )
	{
		cout << "Exists file" << endl;
		stringstream buffer;
		buffer << inFileStream.rdbuf();
		string s = buffer.str();
		cout << s;

		ofstream outFileStream( filePath.c_str() );
		outFileStream << "After A B C.\n";
		outFileStream.close();
	}
	else
	{
		cout << "No file" << endl;
		ofstream outFileStream( filePath.c_str() );
		outFileStream << "First A B C.\n";
		outFileStream.close();
	}
	inFileStream.close();
	return 0;
}

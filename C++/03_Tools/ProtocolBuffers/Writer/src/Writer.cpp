#include <iostream>
#include <fstream>
#include "lm.helloworld.pb.h"
using namespace std;

int main() {
	lm::helloworld msg1;
	msg1.set_id(1011);
	msg1.set_str("helloo");

	// Write the new address book back to disk.

	fstream output("/home/zhenkun/Alex.log", ios::out | ios::trunc | ios::binary);

	if (!msg1.SerializeToOstream(&output)) {
		cerr << "Failed to write msg." << endl;
		return -1;
	}
	return 0;
}

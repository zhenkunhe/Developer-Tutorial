#include <iostream>
#include <sstream>
using namespace std;

string convertDouble(double value)
{
  std::ostringstream o;
  if (!(o << value))
    return "";
  return o.str();
}

int main()
{
	double tmp = 2.2;
	cout << convertDouble(tmp) << endl;

	char* cstr;
	asprintf( &cstr, "%.1f * %.1f = %.1f", tmp, tmp, tmp*tmp );

	cout << cstr << endl;
	return 0;
}

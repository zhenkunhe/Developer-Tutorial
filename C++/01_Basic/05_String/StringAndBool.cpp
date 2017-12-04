#include <iostream>

using namespace std;

int main()
{
        bool mute = false;
        cout << "to_string(false):" << std::to_string(mute) << endl;
        mute = true;
        cout << "to_string(true):" << std::to_string(mute) << endl;

        const char* message = "0";
        mute = atoi(message);
        cout << "assign atoi(\"0\") to bool:" << mute << endl;
        const char* message2 = "1";
        mute = atoi(message2);
        cout << "assign atoi(\"1\") to bool:" << mute << endl;

        return 0;
}

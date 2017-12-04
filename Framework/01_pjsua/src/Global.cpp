#include <pjsua/Global.hpp>

ThreadPool Global::pool( THREAD_POOL_SIZE );
vector<future<int>> Global::results;

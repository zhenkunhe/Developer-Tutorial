cmake_minimum_required(VERSION 2.8)
project(Bar)

add_executable(bar src/bar.c)
target_link_libraries(bar
  foo
)

cmake_minimum_required(VERSION 2.8)
project(Foo)

add_library(foo SHARED src/foo.c)

target_include_directories(foo PUBLIC
  ${PROJECT_SOURCE_DIR}/inc
)

# target_include_directories(foo PRIVATE
#   ${PROJECT_SOURCE_DIR}/inc
# )

# set_target_properties(foo
#   PROPERTIES
#   INTERFACE_INCLUDE_DIRECTORIES   ${PROJECT_SOURCE_DIR}/inc
# )

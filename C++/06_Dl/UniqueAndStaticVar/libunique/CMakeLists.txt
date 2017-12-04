cmake_minimum_required(VERSION 2.8.11)
PROJECT(unique)

set(module_sources "${CMAKE_CURRENT_SOURCE_DIR}/src")
set(module_include "${CMAKE_CURRENT_SOURCE_DIR}/inc")
file(GLOB all_cpp ${module_sources}/*.cpp)
file(GLOB all_hpp ${module_include}/*.hpp)

#include
include_directories(
    ${module_include}
)
    
#library
add_library(${PROJECT_NAME} MODULE ${all_cpp})
TARGET_LINK_LIBRARIES(${PROJECT_NAME} 
)

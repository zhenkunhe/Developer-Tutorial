cmake_minimum_required(VERSION 2.8.11)
PROJECT(gatewaySDK)

set (CMAKE_CXX_STANDARD 11)
set(module_sources "${CMAKE_CURRENT_SOURCE_DIR}/src")
set(module_include "${CMAKE_CURRENT_SOURCE_DIR}/inc")
file(GLOB all_cpp ${module_sources}/*.cpp)
file(GLOB all_hpp ${module_include}/*.hpp)

#define
add_definitions(-DZMQ_BUILD_DRAFT_API=1)

#include
include_directories(
    ${module_include}
)
    
#library
add_library(${PROJECT_NAME} SHARED ${all_cpp})
TARGET_LINK_LIBRARIES(${PROJECT_NAME} 
    pthread
    zmq
    uuid
    dl
)

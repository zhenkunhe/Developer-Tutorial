cmake_minimum_required(VERSION 2.8.11)
PROJECT(gateway)

set (CMAKE_CXX_STANDARD 11)
set(module_sources "${CMAKE_CURRENT_SOURCE_DIR}/src")
set(module_include "${CMAKE_CURRENT_SOURCE_DIR}/inc")
file(GLOB all_cpp ${module_sources}/*.cpp)
file(GLOB all_hpp ${module_include}/*.hpp)

#include
include_directories(
    "../gatewaySDK/inc"
    ${module_include}
)
    
#execute
link_directories("../gatewaySDK/build/Debug")
add_executable(${PROJECT_NAME} ${all_cpp})
TARGET_LINK_LIBRARIES(${PROJECT_NAME} 
    gatewaySDK
)

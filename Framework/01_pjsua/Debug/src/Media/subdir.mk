################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src/Media/AudioMediaPlayerPega.cpp 

OBJS += \
./src/Media/AudioMediaPlayerPega.o 

CPP_DEPS += \
./src/Media/AudioMediaPlayerPega.d 


# Each subdirectory must supply rules for building sources it contributes
src/Media/%.o: ../src/Media/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++  -std=c++11 -pthread -D__cplusplus=201103L -I/usr/local/includ -I"/home/alex/workspace/c++/pjsua/inc" -O0 -g3 -Wall -c -fmessage-length=0 -DPJ_AUTOCONF=1 -O2 -DPJ_IS_BIG_ENDIAN=0 -DPJ_IS_LITTLE_ENDIAN=1 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '



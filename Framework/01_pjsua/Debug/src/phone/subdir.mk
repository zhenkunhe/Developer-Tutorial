################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src/phone/AccountPega.cpp \
../src/phone/Phone.cpp 

OBJS += \
./src/phone/AccountPega.o \
./src/phone/Phone.o 

CPP_DEPS += \
./src/phone/AccountPega.d \
./src/phone/Phone.d 


# Each subdirectory must supply rules for building sources it contributes
src/phone/%.o: ../src/phone/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++  -std=c++11 -pthread -D__cplusplus=201103L -I/usr/local/includ -I"/home/alex/workspace/c++/pjsua/inc" -O0 -g3 -Wall -c -fmessage-length=0 -DPJ_AUTOCONF=1 -O2 -DPJ_IS_BIG_ENDIAN=0 -DPJ_IS_LITTLE_ENDIAN=1 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '



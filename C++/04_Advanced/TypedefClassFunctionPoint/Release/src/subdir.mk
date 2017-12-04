################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src/AlexGateway.cpp \
../src/BusAttachment.cpp \
../src/GatewayProto.cpp \
../src/main.cpp 

OBJS += \
./src/AlexGateway.o \
./src/BusAttachment.o \
./src/GatewayProto.o \
./src/main.o 

CPP_DEPS += \
./src/AlexGateway.d \
./src/BusAttachment.d \
./src/GatewayProto.d \
./src/main.d 


# Each subdirectory must supply rules for building sources it contributes
src/%.o: ../src/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -O3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '



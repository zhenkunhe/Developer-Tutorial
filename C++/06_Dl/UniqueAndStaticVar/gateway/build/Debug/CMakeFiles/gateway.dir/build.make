# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.5

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /usr/bin/cmake

# The command to remove a file.
RM = /usr/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/alex/workspace/c++/gateway

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/alex/workspace/c++/gateway/build/Debug

# Include any dependencies generated for this target.
include CMakeFiles/gateway.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/gateway.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/gateway.dir/flags.make

CMakeFiles/gateway.dir/src/main.cpp.o: CMakeFiles/gateway.dir/flags.make
CMakeFiles/gateway.dir/src/main.cpp.o: ../../src/main.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/alex/workspace/c++/gateway/build/Debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/gateway.dir/src/main.cpp.o"
	/usr/bin/c++   $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/gateway.dir/src/main.cpp.o -c /home/alex/workspace/c++/gateway/src/main.cpp

CMakeFiles/gateway.dir/src/main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/gateway.dir/src/main.cpp.i"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/alex/workspace/c++/gateway/src/main.cpp > CMakeFiles/gateway.dir/src/main.cpp.i

CMakeFiles/gateway.dir/src/main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/gateway.dir/src/main.cpp.s"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/alex/workspace/c++/gateway/src/main.cpp -o CMakeFiles/gateway.dir/src/main.cpp.s

CMakeFiles/gateway.dir/src/main.cpp.o.requires:

.PHONY : CMakeFiles/gateway.dir/src/main.cpp.o.requires

CMakeFiles/gateway.dir/src/main.cpp.o.provides: CMakeFiles/gateway.dir/src/main.cpp.o.requires
	$(MAKE) -f CMakeFiles/gateway.dir/build.make CMakeFiles/gateway.dir/src/main.cpp.o.provides.build
.PHONY : CMakeFiles/gateway.dir/src/main.cpp.o.provides

CMakeFiles/gateway.dir/src/main.cpp.o.provides.build: CMakeFiles/gateway.dir/src/main.cpp.o


# Object files for target gateway
gateway_OBJECTS = \
"CMakeFiles/gateway.dir/src/main.cpp.o"

# External object files for target gateway
gateway_EXTERNAL_OBJECTS =

gateway: CMakeFiles/gateway.dir/src/main.cpp.o
gateway: CMakeFiles/gateway.dir/build.make
gateway: CMakeFiles/gateway.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/alex/workspace/c++/gateway/build/Debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable gateway"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/gateway.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/gateway.dir/build: gateway

.PHONY : CMakeFiles/gateway.dir/build

CMakeFiles/gateway.dir/requires: CMakeFiles/gateway.dir/src/main.cpp.o.requires

.PHONY : CMakeFiles/gateway.dir/requires

CMakeFiles/gateway.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/gateway.dir/cmake_clean.cmake
.PHONY : CMakeFiles/gateway.dir/clean

CMakeFiles/gateway.dir/depend:
	cd /home/alex/workspace/c++/gateway/build/Debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/alex/workspace/c++/gateway /home/alex/workspace/c++/gateway /home/alex/workspace/c++/gateway/build/Debug /home/alex/workspace/c++/gateway/build/Debug /home/alex/workspace/c++/gateway/build/Debug/CMakeFiles/gateway.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/gateway.dir/depend

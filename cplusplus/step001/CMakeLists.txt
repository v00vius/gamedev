cmake_minimum_required(VERSION 3.25)
set(CMAKE_INSTALL_PREFIX ~/install)

project(game VERSION 1.0
    LANGUAGES CXX
)
# set(CXX_ADD_FLAGS "-std=c++20 -fmodules-ts")
set(CXX_ADD_FLAGS "-fmodules-ts")
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} ${CXX_ADD_FLAGS}")

set(CMAKE_VERBOSE_MAKEFILE Yes)
set(CMAKE_RULE_MESSAGES No)

add_library(compiler_flags INTERFACE)
target_compile_features(compiler_flags INTERFACE cxx_std_20)

add_executable(${PROJECT_NAME}
     vector2.cpp
     impl/vector2.cpp
     component.cpp
     entity.cpp
     app.cpp
)

#add_subdirectory(utils)

target_link_libraries(${PROJECT_NAME} PUBLIC compiler_flags)
#target_link_libraries(${PROJECT_NAME} PUBLIC utils compiler_flags)

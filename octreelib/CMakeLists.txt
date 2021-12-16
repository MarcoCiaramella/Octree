
cmake_minimum_required(VERSION 3.4.1)

add_library(
        Octree
        SHARED
        src/main/cpp/octree.c
)

target_link_libraries(
        Octree
        log
)

include_directories(src/main/cpp/)
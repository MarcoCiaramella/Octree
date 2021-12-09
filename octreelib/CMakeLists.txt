
cmake_minimum_required(VERSION 3.4.1)

add_library(
        Octree
        SHARED
        src/main/cpp/octree.c
)

include_directories(src/main/cpp/)
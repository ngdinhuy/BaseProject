cmake_minimum_required(VERSION 3.22.1)

# Thiết lập tên thư viện và file nguồn
add_library(
             decrypt-lib
             SHARED
             decrypt-lib.cpp )
#crypto
add_library(crypto STATIC IMPORTED)
set_target_properties(
                       crypto
                       PROPERTIES IMPORTED_LOCATION
                       ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI}/libcrypto.a )

#ssl
add_library(ssl STATIC IMPORTED)
set_target_properties(
                       ssl
                       PROPERTIES IMPORTED_LOCATION
                       ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI}/libssl.a )

target_include_directories(decrypt-lib PRIVATE
        ${CMAKE_SOURCE_DIR}/include)

# Tìm kiếm các thư viện log của Android (tùy chọn)
find_library(
              log-lib
              log )

# Liên kết thư viện của bạn với các thư viện hệ thống
target_link_libraries(
                       decrypt-lib
                       ssl
                       crypto
                       ${log-lib} )
cmake_minimum_required(VERSION 3.23)
project(UserLoginSystem)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)
set(CMAKE_AUTOUIC ON)

set(CMAKE_PREFIX_PATH /opt/homebrew/Cellar/qt/6.4.0/lib/cmake)
find_package(Qt6 REQUIRED COMPONENTS Core Gui Widgets)
qt_standard_project_setup()

add_executable(UserLoginSystem
        main.cpp
        MainWindow/mainwindow.cpp
        MainWindow/mainwindow.h
        MainWindow/mainwindow.ui
        Map/Map.h
        Deque/Deque.h
        String/String.h
        String/String.cpp
        LoginSystem/UserLoginSystem.cpp
        LoginSystem/UserLoginSystem.h Exception/Exception.h LoginWindow/loginwindow.cpp LoginWindow/loginwindow.h LoginWindow/loginwindow.ui RegWindow/regwindow.cpp RegWindow/regwindow.h RegWindow/regwindow.ui ChPwdWindow/chpwdwindow.cpp ChPwdWindow/chpwdwindow.h ChPwdWindow/chpwdwindow.ui LogoutWindow/logoutwindow.cpp LogoutWindow/logoutwindow.h LogoutWindow/logoutwindow.ui)

target_link_libraries(UserLoginSystem
        Qt6::Core
        Qt6::Gui
        Qt6::Widgets
        )
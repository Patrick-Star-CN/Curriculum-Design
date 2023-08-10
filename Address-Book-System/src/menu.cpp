//
// Created by 15928 on 2022/5/3.
//

#include "../include/menu.h"

void Menu::displayMainMenu() {
    std::cout << "----------------------------------------" << std::endl;
    std::cout << "请输入对应功能前的编号来操作通讯录管理系统" << std::endl
              << "1: 添加新用户信息" << std::endl
              << "2: 查询用户信息" << std::endl
              << "3: 输出用户信息" << std::endl
              << "4: 修改用户信息" << std::endl
              << "5: 删除用户信息" << std::endl
              << "6: 对用户信息进行排序" << std::endl
              << "0: 退出系统并保存数据" << std::endl;
    std::cout << ">";
}

void Menu::displayFetchMenu() {
    std::cout << "----------------------------------------" << std::endl;
    std::cout << "请输入对应功能前的编号来查询信息" << std::endl
              << "1: 根据姓名进行精确查询" << std::endl
              << "2: 根据电话进行精确查询" << std::endl
              << "3: 根据地址进行模糊查询" << std::endl
              << "4: 根据类别查询" << std::endl
              << "0: 返回上级菜单" << std::endl;
    std::cout << ">";
}

void Menu::displayChangeMenu() {
    std::cout << "----------------------------------------" << std::endl;
    std::cout << "请输入对应功能前的编号来修改信息" << std::endl
              << "1: 根据姓名进行修改信息" << std::endl
              << "2: 根据电话进行修改信息" << std::endl
              << "0: 返回上级菜单" << std::endl;
    std::cout << ">";
}

void Menu::displayDeleteMenu() {
    std::cout << "----------------------------------------" << std::endl;
    std::cout << "请输入对应功能前的编号来删除用户" << std::endl
              << "1: 根据姓名进行删除" << std::endl
              << "2: 根据电话进行删除" << std::endl
              << "0: 返回上级菜单" << std::endl;
    std::cout << ">";
}

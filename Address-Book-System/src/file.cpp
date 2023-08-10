//
// Created by 15928 on 2022/5/3.
//

#include "../include/file.h"

File::File(const std::string path, const std::string fileType) {
    File::path = path;
    File::fileType = fileType;
}

void File::init(UserList &userList) {
    std::ifstream in;
    in.open(path + fileType, std::ios::in);
    if(!in.is_open()) {
        std::ofstream out;
        out.open(path + fileType, std::ios::out | std::ios::app);
        out.close();
        in.open(path + fileType, std::ios::in);
    }
    while(!in.eof()) {
        userList.addNode(in);
    }
    in.close();
}

void File::add(User &user) {
    std::ofstream out;
    out.open(path + fileType, std::ios::app);
    if(!out.is_open()) {
        std::cout << "文件打开失败" << std::endl;
        return;
    }
    out << std::endl << user;
    out.close();
}

void File::change(UserList &userList) {
    std::ofstream out;
    out.open(path + ".temp", std::ios::app);
    if(!out.is_open()) {
        std::cout << "文件打开失败" << std::endl;
        return;
    }
    auto ptr = userList.getHead();
    if(ptr) {
        out << ptr->getData();
        ptr = ptr->getNext();
    }
    while(ptr) {
        out << std::endl << ptr->getData();
        ptr = ptr->getNext();
    }
    out.close();
    remove((path + fileType).c_str());
    rename((path + ".temp").c_str(), (path + fileType).c_str());
}

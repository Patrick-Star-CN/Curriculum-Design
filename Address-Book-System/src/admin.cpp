//
// Created by 15928 on 2022/5/14.
//

#include "../include/admin.h"

Admin::Admin(std::string path, std::string type): path(path), type(type){
}

bool Admin::login() {
    std::ifstream in;
    std::string name;
    std::string pwd;
    std::string name_;
    std::string pwd_;

    in.open(path + type, std::ios::in);
    if(!in.is_open()) {
        std::ofstream out;
        out.open(path + type, std::ios::out | std::ios::app);
        out.close();
        in.open(path + type, std::ios::in);
    }
    in >> name >> pwd;

    std::cout << "���������Ա���û���\n>";
    std::cin >> name_;
    std::cout << "���������Ա������\n>";
    std::cin >> pwd_;

    if(name != name_) {
        std::cout << "�û�������\n";
        return false;
    }
    if(pwd != pwd_) {
        std::cout << "�������\n";
        return false;
    }
    std::cout << "��½�ɹ�\n";
    return true;
}


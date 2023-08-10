//
// Created by 15928 on 2022/5/14.
//

#ifndef PROJECT_ABS_ADMIN_H
#define PROJECT_ABS_ADMIN_H
#include <string>
#include "../include/file.h"
class Admin {
public:
    Admin(std::string, std::string);

    bool login();

private:
    std::string name;
    std::string pwd;
    std::string path;
    std::string type;
};
#endif //PROJECT_ABS_ADMIN_H

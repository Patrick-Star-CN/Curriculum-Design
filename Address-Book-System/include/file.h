//
// Created by 15928 on 2022/5/3.
//

#ifndef PROJECT_ABS_FILE_H
#define PROJECT_ABS_FILE_H

#include <fstream>
#include "userList.h"

class File {
public:
    File(const std::string, const std::string);

    void init(UserList&);

    void add(User&);

    void change(UserList&);
private:
    std::string path;
    std::string fileType;
};
#endif //PROJECT_ABS_FILE_H

#include "UserLoginSystem.h"

void UserLoginSystem::login(const String &username, const String &pwd) {
    std::ofstream log;
    log.open("../data/system.log", std::ios::app);
    time_t now = time(nullptr);
    Map<String, String>::Iterator it = userList.find(username);
    if (!it) {
        log << ctime(&now) << " 用户不存在" << username << std::endl;
        printf("%s 用户不存在\t%s\n", ctime(&now), (char *)username);
        throw UserNotFindException((char *) "用户不存在");
    }
    if (it->getSecond() != pwd) {
        log << ctime(&now) << " 密码错误" << username << std::endl;
        printf("%s密码错误 %s\n", ctime(&now), (char *)username);
        throw WrongPwdException((char *) "密码错误");
    }
    log << ctime(&now) << " 登录成功" << username << std::endl;
    printf("%s 登录成功\t%s\n", ctime(&now), (char *)username);
}

void UserLoginSystem::updatePassword(const String &username, const String &oldPwd, String &pwd, String &pwdAgain) {
    std::ofstream log;
    log.open("../data/system.log", std::ios::app);
    time_t now = time(nullptr);
    Map<String, String>::Iterator it = userList.find(username);
    if (!it) {
        log << ctime(&now) << " 用户不存在" << username << std::endl;
        printf("%s 用户不存在\t%s\n", ctime(&now), (char *)username);
        throw UserNotFindException((char *) "用户不存在");
    } else if (it->getSecond() != oldPwd) {
        log << ctime(&now) << " 原密码错误" << username << std::endl;
        printf("%s 原密码错误\t%s\n", ctime(&now), (char *)username);
        throw WrongOldPwdException((char *) "原密码错误");
    }
    if (pwd != pwdAgain) {
        log << ctime(&now) << " 密码不一致" << username << std::endl;
        printf("%s 密码不一致\t%s\n", ctime(&now), (char *)username);
        throw WrongPwdException((char *) "两次密码不一致");
    }
    if (!std::regex_match((char *)pwd, re)) {
        log << ctime(&now) << " 密码不符合要求" << username << std::endl;
        printf("%s 密码不符合要求\t%s\n", ctime(&now), (char *)username);
        throw PwdException((char *) "密码不符合要求");
    }
    userList.at(username) = pwd;
    log << ctime(&now) << " 密码修改成功" << username << std::endl;
    printf("%s 密码修改成功\t%s\n", ctime(&now), (char *)username);
}

void UserLoginSystem::deleteUser(const String& username, const String& pwd) {
    std::ofstream log;
    log.open("../data/system.log", std::ios::app);
    time_t now = time(nullptr);
    Map<String, String>::Iterator it = userList.find(username);
    if (!it) {
        log << ctime(&now) << " 用户不存在" << username << std::endl;
        printf("%s 用户不存在\t%s\n", ctime(&now), (char *)username);
        throw UserNotFindException((char *) "用户不存在");
    } else if (it->getSecond() != pwd) {
        log << ctime(&now) << " 身份验证失败" << username << std::endl;
        printf("%s 身份验证失败\t%s\n", ctime(&now), (char *)username);
        throw UserNotFindException((char *) "身份验证失败");
    }
    userList.erase(userList.find(username));
    log << ctime(&now) << " 用户注销成功" << username << std::endl;
    printf("%s 用户注销成功\t%s\n", ctime(&now), (char *)username);
}

void UserLoginSystem::registor(const String &username, String &pwd, String &pwdAgain) {
    std::ofstream log;
    log.open("../data/system.log", std::ios::app);
    time_t now = time(nullptr);
    Map<String, String>::Iterator it = userList.find(username);
    if (it) {
        log << ctime(&now) << " 用户已存在" << username << std::endl;
        printf("%s 用户已存在\t%s\n", ctime(&now), (char *)username);
        throw UserExistedException((char *) "用户已存在");
    } else if (!std::regex_match((char *)username, re)) {
        log << ctime(&now) << " 用户名不符合要求" << username << std::endl;
        printf("%s 用户名不符合要求\t%s\n", ctime(&now), (char *)username);
        throw UsernameException((char *) "用户名不符合要求");
    }
    if (pwd != pwdAgain) {
        log << ctime(&now) << " 密码不一致" << username << std::endl;
        printf("%s 密码不一致\t%s\n", ctime(&now), (char *)username);
        throw WrongPwdException((char *) "两次密码不一致");
    } else if (!std::regex_match((char *)pwd, re)) {
        log << ctime(&now) << " 密码不符合要求" << username << std::endl;
        printf("%s 密码不符合要求\t%s\n", ctime(&now), (char *)username);
        throw PwdException((char *) "密码不符合要求");
    }
    userList.insert(Pair<String, String>(username, pwd));
    log << ctime(&now) << " 注册成功" << username << std::endl;
    printf("%s 注册成功\t%s\n", ctime(&now), (char *)username);
}

void UserLoginSystem::init() {
    time_t now = time(nullptr);
    std::ifstream in;
    in.open("../data/userdata.txt", std::ios::in);
    if(!in.is_open()) {
        std::ofstream out;
        out.open("../data/userdata.txt", std::ios::out | std::ios::app);
        out.close();
        in.open("../data/userdata.txt", std::ios::in);
    }
    while(!in.eof()) {
        String username, pwd;
        in >> username >> pwd;
        if (username == " ") {
            break;
        }
        userList.insert(Pair<String, String>(username, pwd));
    }
    in.close();
}

void UserLoginSystem::save() {
    std::ofstream log;
    log.open("../data/system.log", std::ios::app);
    time_t now = time(nullptr);
    std::ofstream out;
    out.open("../data/userdata.temp", std::ios::app);
    if(!out.is_open()) {
        log << ctime(&now) << " 文件打开失败" << std::endl;
        printf("%s 文件打开失败\n", ctime(&now));
        return;
    }
    auto it = userList.begin();
    while (it != userList.end()) {
        if (it != userList.begin()) {
            out << std::endl;
        }
        out << it->getFirst() << " " << it->getSecond();
        it ++;
    }
    out.close();
    remove("../data/userdata.txt");
    rename("../data/userdata.temp", "../data/userdata.txt");
}

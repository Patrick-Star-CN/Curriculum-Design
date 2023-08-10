//
// 用户数据类的实现文件
// Created by 15928 on 2022/4/17.
//

#include "../include/user.h"

#include <utility>

const std::string &User::getName() const {
    return name;
}

void User::setName(const std::string &name) {
    User::name = name;
}

const std::string &User::getSex() const {
    return sex;
}

void User::setSex(const std::string &sex) {
    User::sex = sex;
}

const std::string &User::getPhoneNum() const {
    return phoneNum;
}

void User::setPhoneNum(const std::string &phoneNum) {
    User::phoneNum = phoneNum;
}

const Address &User::getAddress() const {
    return address;
}

void User::setAddress(const Address &address) {
    User::address = address;
}

const std::string &User::getPostalCode() const {
    return postalCode;
}

void User::setPostalCode(const std::string &postalCode) {
    User::postalCode = postalCode;
}

const std::string &User::getEMail() const {
    return eMail;
}

void User::setEMail(const std::string &eMail) {
    User::eMail = eMail;
}

const std::string &User::getQqNum() const {
    return QQNum;
}

void User::setQqNum(const std::string &qqNum) {
    QQNum = qqNum;
}

const std::string &User::getType() const {
    return type;
}

void User::setType(const std::string &type) {
    User::type = type;
}

User::User() = default;

User::~User() = default;

std::istream &operator>>(std::istream &in, User &right) {
    if(typeid(in) == typeid(std::cin)) {
        std::cout << "请输入该用户的姓名：";
    }
    in >> right.name;

    std::string sex;
    if(typeid(in) == typeid(std::cin)) {
        std::cout << "请输入" << right.name << "的性别：";
    }
    while (in >> sex) {
        if (sex == "男" || sex == "女" || sex == "male" || sex == "female") {
            right.setSex(sex);
            break;
        } else {
            std::cout << "输入的性别格式非法，应该为'男'或'女'或'male'或'female'，请重新输入：";
        }
    }

    std::string phoneNum;
    if(typeid(in) == typeid(std::cin)) {
        std::cout << "请输入" << right.name << "的电话号码：";
    }
    while (in >> phoneNum) {
        if (regex_match(phoneNum, std::regex("[1-9][0-9]{10}"))) {
            right.setPhoneNum(phoneNum);
            break;
        } else {
            std::cout << "输入的电话号码格式非法，应该为11位非0开头的纯数字，请重新输入：";
        }
    }

    if(typeid(in) == typeid(std::cin)) {
        std::cout << "请输入" << right.name << "的住址";
    }
    in >> right.address;

    std::string postalCode;
    if(typeid(in) == typeid(std::cin)) {
        std::cout << "请输入" << right.name << "的邮政编码（输入0代表为留空）：";
    }
    while (in >> postalCode) {
        if (regex_match(postalCode, std::regex("^[0-9]{6}"))) {
            right.setPostalCode(postalCode);
            break;
        } else if(postalCode == "0") {
            right.setPostalCode("");
            break;
        } else {
            std::cout << "输入的邮政编码格式非法，应该为6为纯数字，请重新输入：";
        }
    }

    std::string eMail;
    if(typeid(in) == typeid(std::cin)) {
        std::cout << "请输入" << right.name << "的电子邮箱（输入0代表为留空）：";
    }
    while (in >> eMail) {
        if (regex_match(eMail, std::regex(R"(^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$)"))) {
            right.setEMail(eMail);
            break;
        } else if (eMail == "0") {
            right.setEMail("");
            break;
        } else {
            std::cout << "输入的邮箱地址格式非法，正确的样例为：123456@zjut.edu.cn，请重新输入：";
        }
    }

    std::string QQNum;
    if(typeid(in) == typeid(std::cin)) {
        std::cout << "请输入" << right.name << "的QQ号（输入0代表为留空）：";
    }
    while (in >> QQNum) {
        if (regex_match(QQNum, std::regex("[1-9][0-9]{4,}"))) {
            right.setQqNum(QQNum);
            break;
        } else if (QQNum == "0") {
            right.setQqNum("");
            break;
        } else {
            std::cout << "输入的QQ号格式非法，应该为至少五位且首位非0的纯数字，请重新输入：";
        }
    }

    std::string type;
    if(typeid(in) == typeid(std::cin)) {
        std::cout << "请输入" << right.name << "的类型：";
    }
    in >> right.type;
    return in;
}

std::ostream &operator<<(std::ostream &out, const User &right) {
    if(typeid(out) == typeid(std::cout)) {
        out << "姓名:" << right.getName() << '\n'
            << "性别:" << right.getSex() << '\n'
            << "电话:" << right.getPhoneNum() << "\n"
            << "地址:" << right.getAddress() << '\n'
            << "邮编:" << right.getPostalCode() << '\n'
            << "邮箱:" << right.getEMail() << '\n'
            << "QQ号:" << right.getQqNum() << '\n'
            << "类型:" << right.getType() << std::endl;
    } else {
        out << right.getName() << " " << right.getSex() << " " << right.getPhoneNum() << " " << right.getAddress();
        if(right.getPostalCode().empty()) {
            out << "0" << " ";
        } else {
            out << right.getPostalCode() << " ";
        }
        if(right.getEMail().empty()) {
            out << "0" << " ";
        } else {
            out << right.getEMail() << " ";
        }
        if(right.getQqNum().empty()) {
            out << "0" << " ";
        } else {
            out << right.getQqNum() << " ";
        }
        out << right.getType();
    }
    return out;
}

User::User(std::string phoneNum) : phoneNum(std::move(phoneNum)) {}
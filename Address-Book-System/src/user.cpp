//
// �û��������ʵ���ļ�
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
        std::cout << "��������û���������";
    }
    in >> right.name;

    std::string sex;
    if(typeid(in) == typeid(std::cin)) {
        std::cout << "������" << right.name << "���Ա�";
    }
    while (in >> sex) {
        if (sex == "��" || sex == "Ů" || sex == "male" || sex == "female") {
            right.setSex(sex);
            break;
        } else {
            std::cout << "������Ա��ʽ�Ƿ���Ӧ��Ϊ'��'��'Ů'��'male'��'female'�����������룺";
        }
    }

    std::string phoneNum;
    if(typeid(in) == typeid(std::cin)) {
        std::cout << "������" << right.name << "�ĵ绰���룺";
    }
    while (in >> phoneNum) {
        if (regex_match(phoneNum, std::regex("[1-9][0-9]{10}"))) {
            right.setPhoneNum(phoneNum);
            break;
        } else {
            std::cout << "����ĵ绰�����ʽ�Ƿ���Ӧ��Ϊ11λ��0��ͷ�Ĵ����֣����������룺";
        }
    }

    if(typeid(in) == typeid(std::cin)) {
        std::cout << "������" << right.name << "��סַ";
    }
    in >> right.address;

    std::string postalCode;
    if(typeid(in) == typeid(std::cin)) {
        std::cout << "������" << right.name << "���������루����0����Ϊ���գ���";
    }
    while (in >> postalCode) {
        if (regex_match(postalCode, std::regex("^[0-9]{6}"))) {
            right.setPostalCode(postalCode);
            break;
        } else if(postalCode == "0") {
            right.setPostalCode("");
            break;
        } else {
            std::cout << "��������������ʽ�Ƿ���Ӧ��Ϊ6Ϊ�����֣����������룺";
        }
    }

    std::string eMail;
    if(typeid(in) == typeid(std::cin)) {
        std::cout << "������" << right.name << "�ĵ������䣨����0����Ϊ���գ���";
    }
    while (in >> eMail) {
        if (regex_match(eMail, std::regex(R"(^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$)"))) {
            right.setEMail(eMail);
            break;
        } else if (eMail == "0") {
            right.setEMail("");
            break;
        } else {
            std::cout << "����������ַ��ʽ�Ƿ�����ȷ������Ϊ��123456@zjut.edu.cn�����������룺";
        }
    }

    std::string QQNum;
    if(typeid(in) == typeid(std::cin)) {
        std::cout << "������" << right.name << "��QQ�ţ�����0����Ϊ���գ���";
    }
    while (in >> QQNum) {
        if (regex_match(QQNum, std::regex("[1-9][0-9]{4,}"))) {
            right.setQqNum(QQNum);
            break;
        } else if (QQNum == "0") {
            right.setQqNum("");
            break;
        } else {
            std::cout << "�����QQ�Ÿ�ʽ�Ƿ���Ӧ��Ϊ������λ����λ��0�Ĵ����֣����������룺";
        }
    }

    std::string type;
    if(typeid(in) == typeid(std::cin)) {
        std::cout << "������" << right.name << "�����ͣ�";
    }
    in >> right.type;
    return in;
}

std::ostream &operator<<(std::ostream &out, const User &right) {
    if(typeid(out) == typeid(std::cout)) {
        out << "����:" << right.getName() << '\n'
            << "�Ա�:" << right.getSex() << '\n'
            << "�绰:" << right.getPhoneNum() << "\n"
            << "��ַ:" << right.getAddress() << '\n'
            << "�ʱ�:" << right.getPostalCode() << '\n'
            << "����:" << right.getEMail() << '\n'
            << "QQ��:" << right.getQqNum() << '\n'
            << "����:" << right.getType() << std::endl;
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
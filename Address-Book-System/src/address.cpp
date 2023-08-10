//
// Created by 15928 on 2022/5/2.
//

#include "../include/address.h"

const std::string &Address::getProvince() const {
    return province;
}

void Address::setProvince(const std::string &province) {
    Address::province = province;
}

const std::string &Address::getCity() const {
    return city;
}

void Address::setCity(const std::string &city) {
    Address::city = city;
}

const std::string &Address::getDistrict() const {
    return district;
}

void Address::setDistrict(const std::string &district) {
    Address::district = district;
}

const std::string &Address::getTown() const {
    return town;
}

void Address::setTown(const std::string &town) {
    Address::town = town;
}

std::istream &operator>>(std::istream &in, Address &right) {
    std::string temp;
    if(typeid(in) == typeid(std::cin)) {
        std::cout << "请输入地址的省级行政区划（0代表留空）：";
    }
    in >> temp;
    if(temp == "0") {
        right.setProvince("");
    } else {
        right.setProvince(temp);
    }

    if(typeid(in) == typeid(std::cin)) {
        std::cout << "请输入地址的市级行政区划（0代表留空）：";
    }
    in >> temp;
    if(temp == "0") {
        right.setCity("");
    } else {
        right.setCity(temp);
    }

    if(typeid(in) == typeid(std::cin)) {
        std::cout << "请输入地址的区级行政区划（0代表留空）：";
    }
    in >> temp;
    if(temp == "0") {
        right.setDistrict("");
    } else {
        right.setDistrict(temp);
    }

    if(typeid(in) == typeid(std::cin)) {
        std::cout << "请输入地址的镇级行政区划（0代表留空）：";
    }
    in >> temp;
    if(temp == "0") {
        right.setTown("");
    } else {
        right.setTown(temp);
    }
    return in;
}

std::ostream &operator<<(std::ostream &out, const Address &right) {
    if(!right.getProvince().empty()) {
        out << right.getProvince() << " ";
    } else if(typeid(out) != typeid(std::cout)) {
        out << "0 ";
    }
    if(!right.getCity().empty()) {
        out << right.getCity() << " ";
    } else if(typeid(out) != typeid(std::cout)) {
        out << "0 ";
    }
    if(!right.getDistrict().empty()) {
        out << right.getDistrict() << " ";
    } else if(typeid(out) != typeid(std::cout)) {
        out << "0 ";
    }
    if(!right.getTown().empty()) {
        out << right.getTown() << " ";
    } else if(typeid(out) != typeid(std::cout)) {
        out << "0 ";
    }
    return out;
}

bool Address::operator==(const Address &rhs) const {
    return province == rhs.province &&
           city == rhs.city &&
           district == rhs.district &&
           town == rhs.town;
}

bool Address::operator!=(const Address &rhs) const {
    return !(rhs == *this);
}

//
// Created by 15928 on 2022/5/2.
//

#ifndef PROJECT_ABS_ADDRESS_H
#define PROJECT_ABS_ADDRESS_H
#include <string>
#include <iostream>
class Address {
public:
    const std::string &getProvince() const;

    void setProvince(const std::string &province);

    const std::string &getCity() const;

    void setCity(const std::string &city);

    const std::string &getDistrict() const;

    void setDistrict(const std::string &district);

    const std::string &getTown() const;

    void setTown(const std::string &town);

    friend std::istream &operator>>(std::istream&, Address&);

    friend std::ostream &operator<<(std::ostream&, const Address&);

    bool operator==(const Address &rhs) const;

    bool operator!=(const Address &rhs) const;

private:
    std::string province;
    std::string city;
    std::string district;
    std::string town;
};
#endif //PROJECT_ABS_ADDRESS_H

#ifndef DATA_STRUCTURES_USERLOGINSYSTEM_H
#define DATA_STRUCTURES_USERLOGINSYSTEM_H
#include "../Map/Map.h"
#include "../Deque/Deque.h"
#include "../String/String.h"
#include "../Exception/Exception.h"
#include <fstream>
#include <ctime>
#include <regex>

class UserLoginSystem {
public:
    void init();

    void registor(const String &, String &, String &);

    void login(const String &, const String &);

    void updatePassword(const String &, const String &, String &, String &);

    void deleteUser(const String &, const String &);

    void save();

private:
    Map<String, String> userList;

    const std::regex re = std::regex("[A-Za-z0-9]{6,12}");
};


#endif //DATA_STRUCTURES_USERLOGINSYSTEM_H

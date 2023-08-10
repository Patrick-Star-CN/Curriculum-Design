//
// Created by 卿无言 on 2022/11/20.
//

#ifndef USERLOGINSYSTEM_EXCEPTION_H
#define USERLOGINSYSTEM_EXCEPTION_H
#include <stdexcept>

class UserNotFindException: public std::exception {
public:
    UserNotFindException(char *m) {
        msg = m;
    }

    virtual char *what() {
        return msg;
    }

private:
    char *msg;
};

class UserExistedException: public std::exception {
public:
    UserExistedException(char *m) {
        msg = m;
    }

    virtual char *what() {
        return msg;
    }

private:
    char *msg;
};

class WrongPwdException: public std::exception {
public:
    WrongPwdException(char *m) {
        msg = m;
    }

    virtual char *what() {
        return msg;
    }

private:
    char *msg;
};

class WrongOldPwdException: public std::exception {
public:
    WrongOldPwdException(char *m) {
        msg = m;
    }

    virtual char *what() {
        return msg;
    }

private:
    char *msg;
};

class PwdException: public std::exception {
public:
    PwdException(char *m) {
        msg = m;
    }

    virtual char *what() {
        return msg;
    }

private:
    char *msg;
};

class UsernameException: public std::exception {
public:
    UsernameException(char *m) {
        msg = m;
    }

    virtual char *what() {
        return msg;
    }

private:
    char *msg;
};

#endif //USERLOGINSYSTEM_EXCEPTION_H

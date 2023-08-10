#ifndef DATA_STRUCTURES_STRING_H
#define DATA_STRUCTURES_STRING_H

#include <cassert>
#include <iostream>

class String {
private:
    unsigned int bufLen;
    char *buffer;

    int *getPrefix() const;

    int KMPMatch(const String &, unsigned int) const;

    int compare(const String &);

    void clear();

    class Iterator {
    public:
        explicit Iterator(char *ptr) : now(ptr) {}

        Iterator() : now(nullptr) {}

        bool operator==(const Iterator &) const;

        bool operator!=(const Iterator &) const;

        const Iterator operator++(int);

        const Iterator operator--(int);

        Iterator &operator++();

        Iterator &operator--();

        char &operator*() const;

    private:
        char *now;
    };

    class ConstIterator {
    public:
        explicit ConstIterator(const char *ptr) : now(ptr) {}

        ConstIterator() : now(nullptr) {}

        bool operator==(const ConstIterator &) const;

        bool operator!=(const ConstIterator &) const;

        const ConstIterator operator++(int);

        const ConstIterator operator--(int);

        ConstIterator &operator++();

        ConstIterator &operator--();

        const char operator*() const;

    private:
        const char *now;
    };

public:
    String();

    explicit String(unsigned int);

    explicit String(char);

    explicit String(const char *);

    String(const String &);

    ~String();

    explicit operator const char *() const;

    explicit operator char *() const;

    String &operator=(const String &);

    String &operator=(const char *);

    String operator+(const String &);

    friend String operator+(const char, String &);

    char &operator[](unsigned);

    bool operator<=(const String &);

    bool operator<(const String &);

    bool operator>=(const String &);

    bool operator>(const String &);

    bool operator==(const String &);

    bool operator!=(const String &);

    bool operator==(const char *);

    bool operator!=(const char *);

    void operator+=(const String &);

    char *getBuffer() const;

    unsigned length() const;

    String::Iterator begin() const;

    String::Iterator end() const;

    String::ConstIterator cbegin() const;

    String::ConstIterator cend() const;

    String subString(unsigned int, unsigned int) const;

    String replaceAll(const String &, const String &);

    unsigned find(const String &, unsigned = 0) const;

    String reverse(const String::Iterator, const String::Iterator);

    std::istream &getLine(std::istream &);

    std::istream &read(std::istream &, char = ' ');
};

unsigned cStrLen(const char *);

std::istream &operator>>(std::istream &, String &);

std::ostream &operator<<(std::ostream &, const String &);

String toString(int);

#endif //DATA_STRUCTURES_STRING_H

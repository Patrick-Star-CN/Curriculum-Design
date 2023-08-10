#include "String.h"

unsigned String::length() const {
    return cStrLen(buffer);
}

String::String() : bufLen(16) {
    buffer = new(std::nothrow) char[bufLen];
    assert(buffer != nullptr);
    for (unsigned i = 0; i < bufLen; i++) {
        buffer[i] = '\0';
    }
}

String::String(unsigned int size) : bufLen(size) {
    buffer = new(std::nothrow) char[size];
    assert(buffer != nullptr);
    for (unsigned i = 0; i < bufLen; i++) {
        buffer[i] = '\0';
    }
}

String::String(char c) : bufLen(2) {
    buffer = new(std::nothrow) char[bufLen];
    assert(buffer != nullptr);
    buffer[0] = c;
    buffer[1] = '\0';
}

String::String(const char *initStr) : bufLen(1 + cStrLen(initStr)) {
    buffer = new(std::nothrow) char[bufLen];
    assert(buffer != nullptr);
    for (unsigned i = 0; i < bufLen - 1; i++) {
        buffer[i] = initStr[i];
    }
    buffer[bufLen - 1] = '\0';
}

String::String(const String &initStr) : bufLen(1 + cStrLen(initStr.buffer)) {
    buffer = new(std::nothrow) char[bufLen];
    assert(buffer != nullptr);
    for (unsigned i = 0; i < bufLen - 1; i++) {
        buffer[i] = initStr.buffer[i];
    }
    buffer[bufLen - 1] = '\0';
}

String::~String() {
    delete[]buffer;
    bufLen = 0;
    buffer = nullptr;
}

char *String::getBuffer() const {
    return buffer;
}

String &String::operator=(const String &ori) {
    if (this == &ori) {
        return (*this);
    }
    const unsigned rightLength = ori.length();
    if (rightLength >= bufLen) {
        delete[]buffer;
        bufLen = 1 + rightLength;
        buffer = new char[bufLen];
        assert(buffer != nullptr);
    }
    unsigned i = 0;
    for (; ori.buffer[i] != '\0'; i++) {
        buffer[i] = ori.buffer[i];
    }
    buffer[i] = '\0';
    return (*this);
}

String &String::operator=(const char *ori) {
    const unsigned rightLength = cStrLen(ori);
    if (rightLength >= bufLen) {
        delete[]buffer;
        bufLen = 1 + rightLength;
        buffer = new char[bufLen];
        assert(buffer != nullptr);
    }
    unsigned i = 0;
    for (; ori[i] != '\0'; i++) {
        buffer[i] = ori[i];
    }
    buffer[i] = '\0';
    return (*this);
}

char nothing;

char &String::operator[](unsigned index) {
    if (index >= cStrLen(buffer)) {
        nothing = '\0';
        return nothing;
    }
    return buffer[index];
}

int String::compare(const String &rhs) {
    char *p = buffer;
    char *q = rhs.buffer;

    for (; (*p != '\0') && (*p == *q); p++, q++);
    return *p - *q;
}

String::operator const char *() const {
    return buffer;
}

std::istream &String::getLine(std::istream &in) {
    in.getline(buffer, bufLen, '\n');
    return in;
}

unsigned cStrLen(const char *s) {
    unsigned len = 0;
    for (; s[len] != '\0'; len++);
    return len;
}

std::istream &operator>>(std::istream &in, String &str) {
    char inBuffer[1000];
    if (in >> inBuffer) {
        str = inBuffer;
    } else {
        str = " ";
    }
    return in;
}

std::ostream &operator<<(std::ostream &out, const String &str) {
    out << str.getBuffer();
    return out;
}

String String::operator+(const String &rhs) {
    String newString(*this);
    newString += rhs;
    return newString;
}

int String::KMPMatch(const String &pattern, unsigned int num = 0) const {
    int *prefix = pattern.getPrefix();
    int tar = 0, pos = 0, num_ = 0;
    while (tar < length()) {
        if (buffer[tar] == pattern.buffer[pos]) {
            tar++;
            pos++;
        } else if (pos != 0) {
            pos = prefix[pos - 1];
        } else {
            tar++;
        }

        if (pos == pattern.length()) {
            if (num == num_) {
                return tar - pos;
            }
            num_++;
        }
    }
    return -1;
}

int *String::getPrefix() const {
    int *prefix = new(std::nothrow) int[bufLen];
    assert(prefix != nullptr);
    prefix[0] = 0;
    int i = 1, now = 0;
    while (i < bufLen) {
        if (buffer[i] == buffer[now]) {
            now++;
            prefix[i] = now;
            i++;
        } else if (now != 0) {
            now = prefix[now - 1];
        } else {
            i++;
            prefix[i] = now;
        }
    }
    return prefix;
}

bool String::operator<=(const String &rhs) {
    return compare(rhs) <= 0;
}

bool String::operator<(const String &rhs) {
    return compare(rhs) < 0;
}

bool String::operator>=(const String &rhs) {
    return compare(rhs) >= 0;
}

bool String::operator>(const String &rhs) {
    return compare(rhs) > 0;
}

bool String::operator==(const String &rhs) {
    return compare(rhs) == 0;
}

bool String::operator!=(const String &rhs) {
    return compare(rhs) != 0;
}

bool String::operator==(const char *rhs) {
    char *p = buffer;

    for (; (*p != '\0') && (*p == *rhs); p++, rhs++);
    return *p == *rhs;
}

bool String::operator!=(const char *rhs) {
    return !(*this == rhs);
}

void String::operator+=(const String &rhs) {
    unsigned int len = bufLen + rhs.bufLen - 1;
    char *newBuffer = new(std::nothrow) char[len];
    assert(newBuffer != nullptr);
    int i = 0;
    for (; i < bufLen - 1; i++) {
        newBuffer[i] = buffer[i];
    }
    for (unsigned int j = 0; i < len - 1; i++, j++) {
        newBuffer[i] = rhs.buffer[j];
    }
    newBuffer[i] = '\0';
    bufLen = len;
    delete[]buffer;
    buffer = newBuffer;
}

String String::subString(unsigned int pos, unsigned int len) const {
    String newStr(len + 1);
    for (unsigned int i = pos, j = 0; i < pos + len; i++, j++) {
        newStr.buffer[j] = buffer[i];
    }
    return newStr;
}

String String::replaceAll(const String &subStr, const String &newSubstr) {
    String newStr;
    int i = KMPMatch(subStr, 0), j = 0, k = 0;
    for (; i != -1; k++, i = KMPMatch(subStr, k)) {
        newStr += subString(j, i - j);
        newStr += newSubstr;
        j = i + cStrLen(subStr.getBuffer());
    }
    newStr += subString(j, bufLen - j);
    (*this) = newStr;
    return newStr;
}

unsigned String::find(const String &subStr, unsigned num) const {
    return KMPMatch(subStr, num);
}

std::istream &String::read(std::istream &in, char delim) {
    clear();
    while (in.peek() != delim && in.peek() != '\n') {
        if (in.peek() == ' ') {
            getchar();
            continue;
        }
        (*this) += String(char(getchar()));
    }
    return in;
}

String String::reverse(const String::Iterator first, const String::Iterator last) {
    String str;
    for (Iterator it = first; it != last; it++) {
        str = *it + str;
    }
    return str;
}

String::Iterator String::begin() const {
    return String::Iterator(buffer);
}

String::Iterator String::end() const {
    return String::Iterator(buffer + cStrLen(buffer));
}

String toString(int ori) {
    String str;
    if (ori < 0) {
        str += String('-');
        ori = -ori;
    } else if (!ori) {
        str = String('0');
        return str;
    }
    while (ori) {
        str = char(ori % 10 + '0') + str;
        ori /= 10;
    }
    return str;
}

String operator+(const char c, String &rhs) {
    String str(rhs.length() + 1);
    str.buffer[0] = c;
    int i = 1;
    for (; i < rhs.bufLen; i++) {
        str.buffer[i] = rhs.buffer[i - 1];
    }
    str.buffer[i] = '\0';
    return str;
}

void String::clear() {
    bufLen = 1;
    delete[] buffer;
    buffer = new char[1];
    buffer[0] = '\0';
}

String::operator char *() const {
    return buffer;
}

String::ConstIterator String::cbegin() const {
    return String::ConstIterator(buffer);
}

String::ConstIterator String::cend() const {
    return String::ConstIterator(buffer + cStrLen(buffer));
}

bool String::Iterator::operator==(const String::Iterator &rhs) const {
    return now == rhs.now;
}

bool String::Iterator::operator!=(const String::Iterator &rhs) const {
    return !(rhs == *this);
}

char &String::Iterator::operator*() const {
    return *now;
}

const String::Iterator String::Iterator::operator++(int i) {
    String::Iterator tmp(now);
    ++(*this);
    return tmp;
}

const String::Iterator String::Iterator::operator--(int) {
    String::Iterator tmp(now);
    --(*this);
    return tmp;
}

String::Iterator &String::Iterator::operator++() {
    ++now;
    return (*this);
}

String::Iterator &String::Iterator::operator--() {
    --now;
    return (*this);
}

bool String::ConstIterator::operator==(const String::ConstIterator &rhs) const {
    return now == rhs.now;
}

bool String::ConstIterator::operator!=(const String::ConstIterator &rhs) const {
    return !(rhs == *this);
}

const String::ConstIterator String::ConstIterator::operator++(int) {
    String::ConstIterator tmp(now);
    ++(*this);
    return tmp;
}

const String::ConstIterator String::ConstIterator::operator--(int) {
    String::ConstIterator tmp(now);
    --(*this);
    return tmp;
}

const char String::ConstIterator::operator*() const {
    return *now;
}

String::ConstIterator &String::ConstIterator::operator++() {
    ++now;
    return (*this);
}

String::ConstIterator &String::ConstIterator::operator--() {
    --now;
    return (*this);
}

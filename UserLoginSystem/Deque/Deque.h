#ifndef DATA_STRUCTURES_DEQUE_H
#define DATA_STRUCTURES_DEQUE_H

#include <iostream>
#include <cassert>

template<typename T>
class Deque {
private:

    T *data;
    unsigned int capacity;
    unsigned int size;
    unsigned int first;
    unsigned int last;

    void zoom();

public:
    class ConstIterator {
    private:
        const T *first;
        const T *ptr;
        const unsigned int capacity;

    public:
        ConstIterator() : ptr(nullptr), first(nullptr), capacity(0) {}

        explicit ConstIterator(T *p, T *v, unsigned int capacity) : ptr(p), first(v), capacity(capacity) {}

        ConstIterator(const ConstIterator &right) : ptr(right.ptr) {}

        const ConstIterator operator++(int) {
            ConstIterator tmp(*this);
            ++(*this);
            return tmp;
        }

        const ConstIterator operator--(int) {
            ConstIterator tmp(*this);
            --(*this);
            return tmp;
        }

        ConstIterator &operator++() {
            if (ptr - first == capacity - 1) {
                ptr = first;
                return (*this);
            }
            ++ptr;
            return (*this);
        }

        ConstIterator &operator--() {
            if (ptr == first) {
                ptr = first + capacity;
                return (*this);
            }
            --ptr;
            return (*this);
        }

        ConstIterator operator+(int n) {
            Iterator tmp(*this);
            tmp += n;
            return tmp;
        }

        ConstIterator operator-(int n) {
            Iterator tmp(*this);
            tmp -= n;
            return tmp;
        }

        ConstIterator &operator+=(int n) {
            if (n == 0) {
                return (*this);
            }
            n += ptr - first;
            if (n > 0) {
                ptr = first + n % capacity;
            } else {
                int z = capacity - 1 - n;
                ptr = first + (capacity - 1 - z % capacity);
            }
            return (*this);
        }

        ConstIterator &operator-=(int n) {
            return (*this) += -n;
        }

        T &operator*() {
            return *(ptr);
        }

        const T *operator->() {
            return ptr;
        }

        bool operator==(const ConstIterator &rhs) const {
            return ptr == rhs.ptr;
        }

        bool operator!=(const ConstIterator &rhs) const {
            return rhs != *this;
        }
    };

    class Iterator {
    private:
        T *first;
        T *ptr;
        unsigned int capacity;

    public:
        Iterator() : ptr(nullptr), first(nullptr), capacity(0) {}

        explicit Iterator(T *p, T *v, unsigned int capacity) : ptr(p), first(v), capacity(capacity) {}

        Iterator(const Iterator &right) : ptr(right.ptr) {}

        const Iterator operator++(int) {
            Iterator tmp(*this);
            ++(*this);
            return tmp;
        }

        const Iterator operator--(int) {
            Iterator tmp(*this);
            --(*this);
            return tmp;
        }

        Iterator &operator++() {
            if (ptr - first == capacity - 1) {
                ptr = first;
                return (*this);
            }
            ++ptr;
            return (*this);
        }

        Iterator &operator--() {
            if (ptr == first) {
                ptr = first + capacity;
                return (*this);
            }
            --ptr;
            return (*this);
        }

        Iterator operator+(int n) {
            Iterator tmp(*this);
            tmp += n;
            return tmp;
        }

        Iterator operator-(int n) {
            Iterator tmp(*this);
            tmp -= n;
            return tmp;
        }

        Iterator &operator+=(int n) {
            if (n == 0) {
                return (*this);
            }
            n += ptr - first;
            if (n > 0) {
                ptr = first + n % capacity;
            } else {
                int z = capacity - 1 - n;
                ptr = first + (capacity - 1 - z % capacity);
            }
            return (*this);
        }

        Iterator &operator-=(int n) {
            return (*this) += -n;
        }

        T &operator*() {
            return *(ptr);
        }

        T *operator->() {
            return ptr;
        }

        bool operator==(const Iterator &rhs) const {
            return ptr == rhs.ptr;
        }

        bool operator!=(const Iterator &rhs) const {
            return rhs != *this;
        }
    };

    Deque() : data(new T[16]), first(0), last(0), capacity(16), size(0) {}

    explicit Deque(unsigned int capacity) : first(0), last(0), capacity(capacity), size(0) {
        data = new(std::nothrow) T[capacity];
        assert(data != nullptr);
    }

    ~Deque() {
        delete[] data;
        data = nullptr;
    }

    Iterator begin();

    ConstIterator cBegin();

    Iterator end();

    ConstIterator cEnd();

    void pushBack(const T &);

    void pushBack(T &&);

    void pushFront(const T &);

    void pushFront(T &&);

    void popBack();

    void popFront();

    T &back();

    T &front();

    bool empty();
};

#endif //DATA_STRUCTURES_DEQUE_H

template<typename T>
typename Deque<T>::Iterator Deque<T>::begin() {
    return Iterator(data + first, data, capacity);
}

template<typename T>
typename Deque<T>::Iterator Deque<T>::end() {
    return Iterator(data + (last + 1) % capacity, data, capacity);
}

template<typename T>
typename Deque<T>::ConstIterator Deque<T>::cBegin() {
    return ConstIterator(data + first, data, capacity);
}

template<typename T>
typename Deque<T>::ConstIterator Deque<T>::cEnd() {
    return ConstIterator(data + (last + 1) % capacity, data, capacity);
}

template<typename T>
void Deque<T>::pushBack(const T &v) {
    if (empty()) {
        data[0] = v;
        size = 1;
        first = 0;
        last = 0;
        return;
    }
    if (size == capacity - 1) {
        Deque<T>::zoom();
    }
    ++last;
    if (last == first + capacity) {
        last = 0;
    }
    data[last] = v;
    ++size;
}

template<typename T>
void Deque<T>::pushBack(T &&v) {
    if (empty()) {
        data[0] = v;
        size = 1;
        first = 0;
        last = 0;
        return;
    }
    if (size == capacity - 1) {
        Deque<T>::zoom();
    }
    ++last;
    if (last == capacity) {
        last = 0;
    }
    data[last] = v;
    ++size;
}

template<typename T>
void Deque<T>::pushFront(const T &v) {
    if (empty()) {
        data[0] = v;
        size = 1;
        first = 0;
        last = 0;
        return;
    }
    if (size == capacity - 1) {
        Deque<T>::zoom();
    }
    if (first == 0) {
        first = capacity;
    }
    --first;
    data[first] = v;
    ++size;
}

template<typename T>
void Deque<T>::pushFront(T &&v) {
    if (empty()) {
        data[0] = v;
        size = 1;
        first = 0;
        last = 0;
        return;
    }
    if (size == capacity - 1) {
        Deque<T>::zoom();
    }
    if (first == 0) {
        first = capacity;
    }
    --first;
    data[first] = v;
    ++size;
}

template<typename T>
void Deque<T>::popBack() {
    --last;
    if (last == -1) {
        last = capacity - 1;
    }
    --size;
}

template<typename T>
void Deque<T>::popFront() {
    ++first;
    if (first == capacity) {
        first = 0;
    }
    --size;
}

template<typename T>
T &Deque<T>::back() {
    return data[first];
}

template<typename T>
T &Deque<T>::front() {
    return data[last];
}

template<typename T>
bool Deque<T>::empty() {
    return size == 0;
}

template<typename T>
void Deque<T>::zoom() {
    T *newData = new T[capacity * 2];
    int i = 0;
    for (Iterator it = begin(); it != end(); it++, i++) {
        newData[i] = *it;
    }
    delete[] data;
    capacity *= 2;
    data = newData;
    first = 0;
    last = size - 1;
}

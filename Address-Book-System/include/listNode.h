//
// 链表元素模板类
// Created by 15928 on 2022/4/22.
//

#ifndef PROJECT_ABS_LISTNODE_H
#define PROJECT_ABS_LISTNODE_H

#include <iostream>
template <typename T>
class ListNode {
public:
    ListNode();

    T getData() const;

    T &getData();

    void setData(T data);

    ListNode *getNext() const;

    ListNode *getNext();

    void setNext(ListNode *next);

    ListNode *getPrior() const;

    ListNode *getPrior();

    void setPrior(ListNode *prior);

    template<typename T_>
    friend std::istream &operator>>(std::istream &in, ListNode<T_> &right);

    template<typename T_>
    friend std::ostream &operator<<(std::ostream &out, const ListNode<T_> &right);

private:
    T data;           // 元素数据
    ListNode *next;   // 后一节点
    ListNode *prior;  // 前一节点
};

template<typename T>
T ListNode<T>::getData() const {
    return data;
}

template<typename T>
void ListNode<T>::setData(T data) {
    ListNode::data = data;
}

template<typename T>
ListNode<T> *ListNode<T>::getNext() const {
    return next;
}

template<typename T>
void ListNode<T>::setNext(ListNode *next) {
    ListNode::next = next;
}

template<typename T>
ListNode<T> *ListNode<T>::getPrior() const {
    return prior;
}

template<typename T>
void ListNode<T>::setPrior(ListNode *prior) {
    ListNode::prior = prior;
}

template<typename T>
std::istream &operator>>(std::istream &in, ListNode<T> &right) {
    T temp = T();
    in >> temp;
    right.setData(temp);
    return in;
}

template<typename T>
std::ostream &operator<<(std::ostream &out, const ListNode<T> &right) {
    return out << right.getData() << std::endl;
}

template<typename T>
ListNode<T>::ListNode(): next(nullptr), prior(nullptr){}

template<typename T>
T &ListNode<T>::getData() {
    return data;
}

template<typename T>
ListNode<T> *ListNode<T>::getNext() {
    return next;
}

template<typename T>
ListNode<T> *ListNode<T>::getPrior() {
    return prior;
}

#endif //PROJECT_ABS_LISTNODE_H

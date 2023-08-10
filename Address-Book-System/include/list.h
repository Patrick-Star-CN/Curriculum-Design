//
// 双向链表模板类
// Created by 15928 on 2022/4/22.
//

#ifndef PROJECT_ABS_LIST_H
#define PROJECT_ABS_LIST_H

#include "../include/listNode.h"
template <typename T>
class List {
public:
    List();

    ~List();

    int getSize();

    ListNode<T> *getHead();

    T &addNode(std::istream&);

    void delNode(int);

    ListNode<T> &operator[](int);

    void display();

protected:
    int size;          // 链表长度
    ListNode<T> *head; // 头节点
    ListNode<T> *tail; // 尾节点
};

template<typename T>
T &List<T>::addNode(std::istream &in) {
    // 往尾节点的后面添加新的节点
    auto newNode = new ListNode<T>();
    T newData;
    in >> newData;
    newNode->setData(newData);
    newNode->setNext(nullptr);
    newNode->setPrior(tail);
    if(tail) {
        // 尾节点不为空时挂上新节点
        tail->setNext(newNode);
    }
    tail = newNode; // 更新尾节点
    if(head == nullptr) {
        // 头节点为空时初始化头节点
        head = newNode;
        head->setPrior(nullptr);
    }
    ++ size;
    return newNode->getData();
}

template<typename T>
List<T>::List() {
    // 初始化链表
    size = 0;
    head = nullptr;
    tail = nullptr;
}

template<typename T>
List<T>::~List() {
    ListNode<T> *ptr = tail;
    ListNode<T> *ptr_ = nullptr;
    while(ptr->getPrior() != nullptr) {
        // 从尾节点开始逐步释放空间
        ptr_ = ptr->getPrior();
        delete ptr;
        ptr = ptr_;
    }
    delete ptr;

    size = 0;
    ptr = nullptr;
    ptr_ = nullptr;
}

template<typename T>
ListNode<T> &List<T>::operator[](int index) {
    // 重载[]运算符以实现查询与修改功能
    // 用的简单的遍历，可能还有优化空间
    ListNode<T> *ptr = head;
    int size_ = 0;
    if(index >= size) {
        index = size - 1;
    }
    while(size_ < index) {
        ptr = ptr->getNext();
        ++ size_;
    }
    return *ptr;
}

template<typename T>
void List<T>::delNode(int index) {
    // 通过普通的遍历来实现删除节点
    ListNode<T> *ptr = head;
    ListNode<T> *ptr_ = nullptr;
    int size_ = 0;
    if(index >= size) {
        // 下标越界时删除尾节点
        index = size - 1;
    }
    while(size_ < index) {
        // 遍历寻找目标节点
        ptr_ = ptr;
        ptr = ptr->getNext();
        ++ size_;
    }

    if(ptr_ && ptr->getNext()) {
        // 普通情况
        ptr_->setNext(ptr->getNext());
        ptr->getNext()->setPrior(ptr_);
    } else if(ptr_) {
        // 目标节点为尾节点时
        ptr_->setNext(nullptr);
        tail = ptr_;
    } else if(ptr->getNext()) {
        // 目标节点为头节点时
        ptr->getNext()->setPrior(nullptr);
        head = ptr->getNext();
    } else {
        // 目标节点为链表中的唯一节点时
        tail = nullptr;
        head = nullptr;
    }

    -- size;
    delete ptr;
    ptr = nullptr;
}

template<typename T>
int List<T>::getSize() {
    // 获取链表长度
    return size;
}

template<typename T>
void List<T>::display() {
    // 输出链表内所有元素
    ListNode<T> *ptr = head;
    while(ptr) {
        std::cout << *ptr;
        ptr = ptr->getNext();
    }
}

template<typename T>
ListNode<T> *List<T>::getHead() {
    return head;
}

#endif //PROJECT_ABS_LIST_H

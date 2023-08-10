//
// ˫������ģ����
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
    int size;          // ������
    ListNode<T> *head; // ͷ�ڵ�
    ListNode<T> *tail; // β�ڵ�
};

template<typename T>
T &List<T>::addNode(std::istream &in) {
    // ��β�ڵ�ĺ�������µĽڵ�
    auto newNode = new ListNode<T>();
    T newData;
    in >> newData;
    newNode->setData(newData);
    newNode->setNext(nullptr);
    newNode->setPrior(tail);
    if(tail) {
        // β�ڵ㲻Ϊ��ʱ�����½ڵ�
        tail->setNext(newNode);
    }
    tail = newNode; // ����β�ڵ�
    if(head == nullptr) {
        // ͷ�ڵ�Ϊ��ʱ��ʼ��ͷ�ڵ�
        head = newNode;
        head->setPrior(nullptr);
    }
    ++ size;
    return newNode->getData();
}

template<typename T>
List<T>::List() {
    // ��ʼ������
    size = 0;
    head = nullptr;
    tail = nullptr;
}

template<typename T>
List<T>::~List() {
    ListNode<T> *ptr = tail;
    ListNode<T> *ptr_ = nullptr;
    while(ptr->getPrior() != nullptr) {
        // ��β�ڵ㿪ʼ���ͷſռ�
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
    // ����[]�������ʵ�ֲ�ѯ���޸Ĺ���
    // �õļ򵥵ı��������ܻ����Ż��ռ�
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
    // ͨ����ͨ�ı�����ʵ��ɾ���ڵ�
    ListNode<T> *ptr = head;
    ListNode<T> *ptr_ = nullptr;
    int size_ = 0;
    if(index >= size) {
        // �±�Խ��ʱɾ��β�ڵ�
        index = size - 1;
    }
    while(size_ < index) {
        // ����Ѱ��Ŀ��ڵ�
        ptr_ = ptr;
        ptr = ptr->getNext();
        ++ size_;
    }

    if(ptr_ && ptr->getNext()) {
        // ��ͨ���
        ptr_->setNext(ptr->getNext());
        ptr->getNext()->setPrior(ptr_);
    } else if(ptr_) {
        // Ŀ��ڵ�Ϊβ�ڵ�ʱ
        ptr_->setNext(nullptr);
        tail = ptr_;
    } else if(ptr->getNext()) {
        // Ŀ��ڵ�Ϊͷ�ڵ�ʱ
        ptr->getNext()->setPrior(nullptr);
        head = ptr->getNext();
    } else {
        // Ŀ��ڵ�Ϊ�����е�Ψһ�ڵ�ʱ
        tail = nullptr;
        head = nullptr;
    }

    -- size;
    delete ptr;
    ptr = nullptr;
}

template<typename T>
int List<T>::getSize() {
    // ��ȡ������
    return size;
}

template<typename T>
void List<T>::display() {
    // �������������Ԫ��
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

//
// 用户数据的链表类
// Created by 15928 on 2022/4/26.
//

#ifndef PROJECT_ABS_USERLIST_H
#define PROJECT_ABS_USERLIST_H
#include "list.h"
#include "user.h"

class UserList : public List<User> {
public:
    ListNode<User> *fetchNode(const std::string&, const std::string&);

    void fetchNode();

    void delNode(const std::string&, const std::string&);

    void changeNode(std::string, const std::string&);

    void sortNode();

private:
    void sort(bool (*cmp)(const ListNode<User>&, const ListNode<User>&));

    void swap(ListNode<User>*, ListNode<User>*);
};

#endif //PROJECT_ABS_USERLIST_H

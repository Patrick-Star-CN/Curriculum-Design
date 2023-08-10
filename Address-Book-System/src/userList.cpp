//
// 用户数据链表类的实现文件
// Created by 15928 on 2022/4/28.
//

#include "../include/userList.h"

  // 空对象

ListNode<User> *UserList::fetchNode(const std::string& context, const std::string& type) {
    // 查询功能,type控制查询的方法，支持用姓名、手机号、类型查询
    ListNode<User> *ptr = head;

    if(type == "phoneNum") {
        while(ptr) {
            if(ptr->getData().getPhoneNum() == context) {
                return ptr;
            }
            ptr = ptr->getNext();
        }
    } else if(type == "name") {
        // 处理重名情况
        int num = 0;
        ListNode<User> *ptr_[getSize()];
        while(ptr) {
            if(ptr->getData().getName() == context) {
                ptr_[num] = ptr;
                if(num == 1) {
                    std::cout << "查询到多个用户，请输入对应用户前的编号进行选择" << std::endl;
                    std::cout << "NO.1" << std::endl << *ptr_[0];
                    std::cout << "NO.2" << std::endl << *ptr;
                } else if(num) {
                    std::cout << "NO." << (num + 1) << std::endl << *ptr;
                }
                num ++;
            }
            ptr = ptr->getNext();
        }
        if(num > 1) {
            int val;
            std::cin >> val;
            if(val <= num) {
                return ptr_[val - 1];
            } else {
                return ptr_[num - 1];
            }
        } else if(num == 1) {
            return ptr_[0];
        }
    } else if(type == "type") {
        while(ptr) {
            if(ptr->getData().getType() == context) {
                std::cout << *ptr;
            }
            ptr = ptr->getNext();
        }
    }
    return nullptr;
}

void UserList::delNode(const std::string& context, const std::string& type) {
    // 删除功能，type控制查询的方法，支持用姓名与手机号查询
    ListNode<User> *ptr = fetchNode(context, type);
    if(!ptr) {
        std::cout << "不存在该用户" << std::endl;
        return;
    }
    ListNode<User> *ptr_ = ptr->getPrior();
    if(ptr_ && ptr->getNext()) {
        ptr_->setNext(ptr->getNext());
        ptr->getNext()->setPrior(ptr_);
    } else if(ptr_) {
        //删尾节点
        ptr_->setNext(nullptr);
        tail = ptr_;
    } else if(ptr->getNext()) {
        //删头节点
        ptr->getNext()->setPrior(nullptr);
        head = ptr->getNext();
    } else {
        //删链表唯一节点
        tail = nullptr;
        head = nullptr;
    }
    -- size;
    delete ptr;
    ptr = nullptr;
    std::cout << "----------------------------------------" << std::endl;
    display();
}

void UserList::changeNode(std::string context, const std::string& type) {
    // 修改功能，type控制查询的方法，支持用姓名与手机号修改
    auto user = type == "name"? fetchNode(context, "name"): fetchNode(context, "phoneNum");
    if(user) {
        std::cout << "请输入需要改变的数据名编号" << std::endl
                  << "1: 姓名" << std::endl
                  << "2: 性别" << std::endl
                  << "3: 电话" << std::endl
                  << "4: 地址" << std::endl
                  << "5: 邮编" << std::endl
                  << "6: 邮箱" << std::endl
                  << "7: QQ号" << std::endl
                  << "8: 类别" << std::endl
                  << "0: 退出" << std::endl;
        int typeNum;
        bool flag = true;
        std::string temp;
        while(flag && std::cin >> typeNum && typeNum ) {
            switch (typeNum) {
                case 1: {
                    std::cout << "请输入新的姓名:";
                    std::cin >> temp;
                    if (type == "name") {
                        user->getData().setName(temp);
                        context = temp;
                    } else if (type == "phoneNum") {
                        user->getData().setName(temp);
                    }
                    display();
                    flag = false;
                    break;
                }
                case 2: {
                    std::cout << "请输入新的性别:";
                    while (std::cin >> temp) {
                        if (temp == "男" || temp == "女" || temp == "male" || temp == "female") {
                            if (type == "name") {
                                user->getData().setSex(temp);
                            } else if (type == "phoneNum") {
                                user->getData().setSex(temp);
                            }
                            break;
                        } else {
                            std::cout << "输入的性别格式非法，应该为'男'或'女'或'male'或'female'，请重新输入：";
                        }
                    }
                    display();
                    flag = false;
                    break;
                }
                case 3: {
                    std::cout << "请输入新的电话号码:";
                    while (std::cin >> temp) {
                        if (regex_match(temp, std::regex("[0-9]{11}"))) {
                            if (type == "name") {
                                user->getData().setPhoneNum(temp);
                            } else if (type == "phoneNum") {
                                user->getData().setPhoneNum(temp);
                                context = temp;
                            }
                            break;
                        } else {
                            std::cout << "输入的电话号码格式非法，应该为11位非0开头的纯数字，请重新输入：";
                        }
                    }
                    display();
                    flag = false;
                    break;
                }
                case 4: {
                    std::cout << "请输入新的地址:";
                    Address address;
                    std::cin >> address;
                    if (type == "name") {
                        user->getData().setAddress(address);
                    } else if (type == "phoneNum") {
                        user->getData().setAddress(address);
                    }
                    display();
                    flag = false;
                    break;
                }
                case 5: {
                    std::cout << "请输入新的邮编:";
                    while (std::cin >> temp) {
                        if (regex_match(temp, std::regex("^[0-9]{6}"))) {
                            if (type == "name") {
                                user->getData().setPostalCode(temp);
                            } else if (type == "phoneNum") {
                                user->getData().setPostalCode(temp);
                            }
                            break;
                        } else if (temp == "0") {
                            if (type == "name") {
                                user->getData().setPostalCode("");
                            } else if (type == "phoneNum") {
                                user->getData().setPostalCode("");
                            }
                            break;
                        } else {
                            std::cout << "输入的邮政编码格式非法，应该为6为纯数字，请重新输入：";
                        }
                    }
                    display();
                    flag = false;
                    break;
                }
                case 6: {
                    std::cout << "请输入新的邮箱:";
                    while (std::cin >> temp) {
                        if (regex_match(temp, std::regex(R"(^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$)"))) {
                            if (type == "name") {
                                user->getData().setEMail(temp);
                            } else if (type == "phoneNum") {
                                user->getData().setEMail(temp);
                            }
                            break;
                        } else if (temp == "0") {
                            if (type == "name") {
                                user->getData().setEMail("");
                            } else if (type == "phoneNum") {
                                user->getData().setEMail("");
                            }
                            break;
                        } else {
                            std::cout << "输入的邮箱地址格式非法，正确的样例为：123456@zjut.edu.cn，请重新输入：";
                        }
                    }
                    display();
                    flag = false;
                    break;
                }
                case 7: {
                    std::cout << "请输入新的QQ号:";
                    while (std::cin >> temp) {
                        if (regex_match(temp, std::regex("[1-9][0-9]{4,}"))) {
                            if (type == "name") {
                                user->getData().setQqNum(temp);
                            } else if (type == "phoneNum") {
                                user->getData().setQqNum(temp);
                            }
                            break;
                        } else if (temp == "0") {
                            if (type == "name") {
                                user->getData().setQqNum("");
                            } else if (type == "phoneNum") {
                                user->getData().setQqNum("");
                            }
                            break;
                        } else {
                            std::cout << "输入的QQ号格式非法，应该为至少五位且首位非0的纯数字，请重新输入：";
                        }
                    }
                    display();
                    flag = false;
                    break;
                }
                case 8: {
                    std::cout << "请输入新的类型:";
                    std::cin >> temp;
                    if (type == "name") {
                        user->getData().setType(temp);
                    } else if (type == "phoneNum") {
                        user->getData().setType(temp);
                    }
                    display();
                    flag = false;
                    break;
                }
                default: {
                    std::cout << "输入有误，请重新输入\n>";
                    break;
                }
            }
        }
    } else {
        // 查询数据出错的情况
        std::cout << "未能找到该用户" << std::endl;
    }
}

void UserList::sortNode() {
    // 排序功能
    std::cout << "请输入排序关键词的编号" << std::endl
              << "1: 姓名" << std::endl
              << "2: 性别" << std::endl
              << "3: 电话" << std::endl
              << "4: 邮编" << std::endl
              << "5: 邮箱" << std::endl
              << "6: QQ号" << std::endl
              << "7: 类别" << std::endl;
    int type;
    std::cin >> type;
    switch(type) {
        case 1: {
            sort([](const ListNode<User> &left, const ListNode<User> &right) -> bool {
                return left.getData().getName() < right.getData().getName();
            });
            display();
            break;
        }
        case 2: {
            sort([](const ListNode<User> &left, const ListNode<User> &right) -> bool {
                if((left.getData().getSex() == "女" || left.getData().getSex() == "female")
                && (right.getData().getSex() == "男" || right.getData().getSex() == "male")) {
                    return true;
                } else if((left.getData().getSex() == "男" || left.getData().getSex() == "male")
                     && (right.getData().getSex() == "女" || right.getData().getSex() == "female")) {
                    return false;
                } else {
                    return left.getData().getSex() < right.getData().getSex();
                }
            });
            display();
            break;
        }
        case 3: {
            sort([](const ListNode<User> &left, const ListNode<User> &right) -> bool {
                return left.getData().getPhoneNum() < right.getData().getPhoneNum();
            });
            display();
            break;
        }
        case 4: {
            sort([](const ListNode<User> &left, const ListNode<User> &right) -> bool {
                return left.getData().getPostalCode() < right.getData().getPostalCode();
            });
            display();
            break;
        }
        case 5: {
            sort([](const ListNode<User> &left, const ListNode<User> &right) -> bool {
                return left.getData().getEMail() < right.getData().getEMail();
            });
            display();
            break;
        }
        case 6: {
            sort([](const ListNode<User> &left, const ListNode<User> &right) -> bool {
                return left.getData().getQqNum() < right.getData().getQqNum();
            });
            display();
            break;
        }
        case 7: {
            sort([](const ListNode<User> &left, const ListNode<User> &right) -> bool {
                return left.getData().getType() < right.getData().getType();
            });
            display();
            break;
        }
        default: {
            std::cout << "输入有误，已自动返回上级菜单" << std::endl;
            break;
        }
    }
}

void UserList::sort(bool (*cmp)(const ListNode<User>&, const ListNode<User>&)) {
    // 排序实现，现阶段利用插入排序
    ListNode<User> *ptr = head;
    ListNode<User> *ptr_ = nullptr;
    ListNode<User> *ptr_tmp = nullptr;
    for(int i = 0; i < getSize() - 1; i ++) {
        ptr_ = ptr->getNext();
        ptr_tmp = ptr;
        for(int j = i + 1; j < getSize(); j ++) {
            if(cmp(*ptr_, *ptr_tmp)) {
                ptr_tmp = ptr_;
            }
            ptr_ = ptr_->getNext();
        }
        if(ptr != ptr_tmp) {
            swap(ptr, ptr_tmp);
            ptr = ptr_tmp;
        }
        ptr = ptr->getNext();
    }
}

void UserList::swap(ListNode<User> *left, ListNode<User> *right) {
    // 交换两个节点的前后关联
    head = left == head ? right: head;
    tail = right == tail ? left: tail;

    if(left->getNext() == right) {
        // 交换的节点相邻
        ListNode<User> *prior = left->getPrior();
        ListNode<User> *next = right->getNext();

        left->setNext(next);
        if(next) {
            next->setPrior(left);
        }
        left->setPrior(right);

        right->setPrior(prior);
        if(prior) {
            prior->setNext(right);
        }
        right->setNext(left);
    } else {
        // 交换的节点不相邻
        if(left->getPrior()) {
            left->getPrior()->setNext(right);
        }
        ListNode<User> *prior = right->getPrior();
        prior->setNext(left);
        right->setPrior(left->getPrior());
        left->setPrior(prior);
        prior = nullptr;

        left->getNext()->setPrior(right);
        ListNode<User> *next = right->getNext();
        if(next) {
            next->setPrior(left);
        }
        right->setNext(left->getNext());
        left->setNext(next);
        next = nullptr;
    }
}

void UserList::fetchNode() {
    // 通过地址实现模糊查询
    Address address;
    ListNode<User> *ptr = head;
    bool flag = true;

    std::cout << "请输入目标地址，留空表示该位置的任何情况都符合" << std::endl;
    std::cin >> address;
    while(ptr) {
        if (address.getTown().empty()) {
            if ((address.getProvince().empty() || address.getProvince() == ptr->getData().getAddress().getProvince())
                && (address.getCity().empty() || address.getCity() == ptr->getData().getAddress().getCity())
                && (address.getDistrict().empty() || address.getDistrict() == ptr->getData().getAddress().getDistrict())) {
                std::cout << *ptr;
                flag = false;
            }
        } else {
            if ((address.getProvince().empty() || address.getProvince() == ptr->getData().getAddress().getProvince())
                && (address.getCity().empty() || address.getCity() == ptr->getData().getAddress().getCity())
                && (address.getDistrict().empty() || address.getDistrict() == ptr->getData().getAddress().getDistrict())
                && address.getTown() == ptr->getData().getAddress().getTown()) {
                std::cout << *ptr;
                flag = false;
            }
        }
        ptr = ptr->getNext();
    }
    if(flag) {
        std::cout << "未能查询到满足要求的用户" << std::endl;
    }
}
#include "../include/file.h"
#include "../include/menu.h"
#include "../include/admin.h"

int main() {
    Menu menu;
    UserList list;
    int userType = 1;
    bool flagMain = true;
    bool flagMain_ = true;
    File file("../data/data", ".txt");
    file.init(list);

    std::cout << "----------------------------------------" << std::endl;
    std::cout << "欢迎使用通讯录管理系统" << std::endl;
    std::cout << "请选择登录身份" << std::endl;
    std::cout << "1: 管理员" << std::endl;
    std::cout << "2: 用户" << std::endl;
    std::cout << "0: 退出" << std::endl;
    std::cout << ">";
    while(flagMain_ && std::cin >> userType && userType) {
        int typeNum;
        switch (userType) {
            case 1: {
                Admin admin("../data/admin", ".txt");
                if(!admin.login()) {
                    flagMain_ = false;
                    break;
                }
                menu.displayMainMenu();
                while (flagMain && std::cin >> typeNum) {
                    switch (typeNum) {
                        case 1: {
                            std::cout << "----------------------------------------" << std::endl;
                            file.add(list.addNode(std::cin));
                            break;
                        }
                        case 2: {
                            if (!list.getSize()) {
                                std::cout << "----------------------------------------" << std::endl;
                                std::cout << "当前链表为空" << std::endl;
                                break;
                            }
                            menu.displayFetchMenu();
                            int typeNum_;
                            bool flag = true;
                            while (flag && std::cin >> typeNum_ && typeNum_) {
                                switch (typeNum_) {
                                    case 1: {
                                        std::cout << "----------------------------------------" << std::endl;
                                        std::cout << "请输入想查询用户的姓名：";
                                        std::string name;
                                        std::cin >> name;
                                        auto user = list.fetchNode(name, "name");
                                        if (user) {
                                            std::cout << *user;
                                        } else {
                                            std::cout << "该用户不存在" << std::endl;
                                        }
                                        flag = false;
                                        break;
                                    }
                                    case 2: {
                                        std::cout << "----------------------------------------" << std::endl;
                                        std::cout << "请输入想查询用户的电话：";
                                        std::string phoneNum;
                                        std::cin >> phoneNum;
                                        auto user = list.fetchNode(phoneNum, "phoneNum");
                                        if (user) {
                                            std::cout << user;
                                        } else {
                                            std::cout << "该用户不存在" << std::endl;
                                        }
                                        flag = false;
                                        break;
                                    }
                                    case 3: {
                                        std::cout << "----------------------------------------" << std::endl;
                                        list.fetchNode();
                                        flag = false;
                                        break;
                                    }
                                    case 4: {
                                        std::cout << "----------------------------------------" << std::endl;
                                        std::cout << "请输入目标类型：";
                                        std::string type;
                                        std::cin >> type;
                                        list.fetchNode(type, "type");
                                        flag = false;
                                        break;
                                    }
                                    default: {
                                        std::cout << "输入有误，请重新输入\n>";
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                        case 3: {
                            std::cout << "----------------------------------------" << std::endl;
                            list.display();
                            break;
                        }
                        case 4: {
                            if (!list.getSize()) {
                                std::cout << "----------------------------------------" << std::endl;
                                std::cout << "当前链表为空" << std::endl;
                                break;
                            }
                            menu.displayChangeMenu();
                            int typeNum_;
                            bool flag = true;
                            while (flag && std::cin >> typeNum_ && typeNum_) {
                                switch (typeNum_) {
                                    case 1: {
                                        std::cout << "----------------------------------------" << std::endl;
                                        std::cout << "请输入想修改用户的姓名：";
                                        std::string name;
                                        std::cin >> name;
                                        std::cout << "----------------------------------------" << std::endl;
                                        list.changeNode(name, "name");
                                        file.change(list);
                                        flag = false;
                                        break;
                                    }
                                    case 2: {
                                        std::cout << "----------------------------------------" << std::endl;
                                        std::cout << "请输入想修改用户的电话：";
                                        std::string phoneNum;
                                        std::cin >> phoneNum;
                                        std::cout << "----------------------------------------" << std::endl;
                                        list.changeNode(phoneNum, "phoneNum");
                                        file.change(list);
                                        flag = false;
                                        break;
                                    }
                                    default: {
                                        std::cout << "输入有误，请重新输入\n>";
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                        case 5: {
                            if (!list.getSize()) {
                                std::cout << "----------------------------------------" << std::endl;
                                std::cout << "当前链表为空" << std::endl;
                                break;
                            }
                            menu.displayDeleteMenu();
                            int typeNum_;
                            bool flag = true;
                            while (flag && std::cin >> typeNum_ && typeNum_) {
                                switch (typeNum_) {
                                    case 1: {
                                        std::cout << "----------------------------------------" << std::endl;
                                        std::cout << "请输入想删除用户的姓名：";
                                        std::string name;
                                        std::cin >> name;
                                        list.delNode(name, "name");
                                        file.change(list);
                                        flag = false;
                                        break;
                                    }
                                    case 2: {
                                        std::cout << "----------------------------------------" << std::endl;
                                        std::cout << "请输入想删除用户的电话：";
                                        std::string phoneNum;
                                        std::cin >> phoneNum;
                                        std::cout << "----------------------------------------" << std::endl;
                                        list.delNode(phoneNum, "phoneNum");
                                        file.change(list);
                                        flag = false;
                                        break;
                                    }
                                    default: {
                                        std::cout << "输入有误，请重新输入\n>";
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                        case 6: {
                            if (!list.getSize()) {
                                std::cout << "----------------------------------------" << std::endl;
                                std::cout << "当前链表为空" << std::endl;
                                break;
                            }
                            std::cout << "----------------------------------------" << std::endl;
                            list.sortNode();
                            file.change(list);
                            break;
                        }
                        case 0: {
                            flagMain_ = false;
                            flagMain = false;
                            break;
                        }
                        default: {
                            std::cout << "输入有误，请重新输入\n>";
                            break;
                        }
                    }
                    if(typeNum) {
                        menu.displayMainMenu();
                    }
                }
                break;
            }
            case 2: {
                if (!list.getSize()) {
                    std::cout << "----------------------------------------" << std::endl;
                    std::cout << "当前链表为空" << std::endl;
                    break;
                }
                std::cout << "----------------------------------------" << std::endl;
                std::cout << "请输入对应功能前的编号来查询信息" << std::endl
                          << "1: 根据姓名进行精确查询" << std::endl
                          << "2: 根据电话进行精确查询" << std::endl
                          << "3: 根据地址进行模糊查询" << std::endl
                          << "4: 根据类别查询" << std::endl
                          << "5: 输出所有用户信息" << std::endl
                          << "0: 退出系统并保存数据" << std::endl;
                std::cout << ">";
                int typeNum_;
                bool flag = true;
                while (flag && std::cin >> typeNum_) {
                    switch (typeNum_) {
                        case 1: {
                            std::cout << "----------------------------------------" << std::endl;
                            std::cout << "请输入想查询用户的姓名：";
                            std::string name;
                            std::cin >> name;
                            auto user = list.fetchNode(name, "name");
                            if (user) {
                                std::cout << *user;
                            } else {
                                std::cout << "该用户不存在" << std::endl;
                            }
                            break;
                        }
                        case 2: {
                            std::cout << "----------------------------------------" << std::endl;
                            std::cout << "请输入想查询用户的电话：";
                            std::string phoneNum;
                            std::cin >> phoneNum;
                            auto user = list.fetchNode(phoneNum, "phoneNum");
                            if (user) {
                                std::cout << user;
                            } else {
                                std::cout << "该用户不存在" << std::endl;
                            }
                            break;
                        }
                        case 3: {
                            std::cout << "----------------------------------------" << std::endl;
                            list.fetchNode();
                            break;
                        }
                        case 4: {
                            std::cout << "----------------------------------------" << std::endl;
                            std::cout << "请输入目标类型：";
                            std::string type;
                            std::cin >> type;
                            list.fetchNode(type, "type");
                            break;
                        }
                        case 5: {
                            std::cout << "----------------------------------------" << std::endl;
                            list.display();
                            break;
                        }
                        case 0: {
                            flag = false;
                            flagMain_ = false;
                            break;
                        }
                        default: {
                            std::cout << "输入有误，请重新输入\n>";
                            break;
                        }
                    }
                    if(typeNum_) {
                        std::cout << "----------------------------------------" << std::endl;
                        std::cout << "请输入对应功能前的编号来查询信息" << std::endl
                                  << "1: 根据姓名进行精确查询" << std::endl
                                  << "2: 根据电话进行精确查询" << std::endl
                                  << "3: 根据地址进行模糊查询" << std::endl
                                  << "4: 根据类别查询" << std::endl
                                  << "5: 输出所有用户信息" << std::endl
                                  << "0: 退出系统并保存数据" << std::endl;
                        std::cout << ">";
                    }
                }
                break;
            }
            default: {
                std::cout << "输入有误，请重新输入\n>";
            }
        }
    }

    file.change(list);
    system("pause");
    return 0;
}

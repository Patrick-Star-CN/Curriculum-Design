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
    std::cout << "��ӭʹ��ͨѶ¼����ϵͳ" << std::endl;
    std::cout << "��ѡ���¼���" << std::endl;
    std::cout << "1: ����Ա" << std::endl;
    std::cout << "2: �û�" << std::endl;
    std::cout << "0: �˳�" << std::endl;
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
                                std::cout << "��ǰ����Ϊ��" << std::endl;
                                break;
                            }
                            menu.displayFetchMenu();
                            int typeNum_;
                            bool flag = true;
                            while (flag && std::cin >> typeNum_ && typeNum_) {
                                switch (typeNum_) {
                                    case 1: {
                                        std::cout << "----------------------------------------" << std::endl;
                                        std::cout << "���������ѯ�û���������";
                                        std::string name;
                                        std::cin >> name;
                                        auto user = list.fetchNode(name, "name");
                                        if (user) {
                                            std::cout << *user;
                                        } else {
                                            std::cout << "���û�������" << std::endl;
                                        }
                                        flag = false;
                                        break;
                                    }
                                    case 2: {
                                        std::cout << "----------------------------------------" << std::endl;
                                        std::cout << "���������ѯ�û��ĵ绰��";
                                        std::string phoneNum;
                                        std::cin >> phoneNum;
                                        auto user = list.fetchNode(phoneNum, "phoneNum");
                                        if (user) {
                                            std::cout << user;
                                        } else {
                                            std::cout << "���û�������" << std::endl;
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
                                        std::cout << "������Ŀ�����ͣ�";
                                        std::string type;
                                        std::cin >> type;
                                        list.fetchNode(type, "type");
                                        flag = false;
                                        break;
                                    }
                                    default: {
                                        std::cout << "������������������\n>";
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
                                std::cout << "��ǰ����Ϊ��" << std::endl;
                                break;
                            }
                            menu.displayChangeMenu();
                            int typeNum_;
                            bool flag = true;
                            while (flag && std::cin >> typeNum_ && typeNum_) {
                                switch (typeNum_) {
                                    case 1: {
                                        std::cout << "----------------------------------------" << std::endl;
                                        std::cout << "���������޸��û���������";
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
                                        std::cout << "���������޸��û��ĵ绰��";
                                        std::string phoneNum;
                                        std::cin >> phoneNum;
                                        std::cout << "----------------------------------------" << std::endl;
                                        list.changeNode(phoneNum, "phoneNum");
                                        file.change(list);
                                        flag = false;
                                        break;
                                    }
                                    default: {
                                        std::cout << "������������������\n>";
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                        case 5: {
                            if (!list.getSize()) {
                                std::cout << "----------------------------------------" << std::endl;
                                std::cout << "��ǰ����Ϊ��" << std::endl;
                                break;
                            }
                            menu.displayDeleteMenu();
                            int typeNum_;
                            bool flag = true;
                            while (flag && std::cin >> typeNum_ && typeNum_) {
                                switch (typeNum_) {
                                    case 1: {
                                        std::cout << "----------------------------------------" << std::endl;
                                        std::cout << "��������ɾ���û���������";
                                        std::string name;
                                        std::cin >> name;
                                        list.delNode(name, "name");
                                        file.change(list);
                                        flag = false;
                                        break;
                                    }
                                    case 2: {
                                        std::cout << "----------------------------------------" << std::endl;
                                        std::cout << "��������ɾ���û��ĵ绰��";
                                        std::string phoneNum;
                                        std::cin >> phoneNum;
                                        std::cout << "----------------------------------------" << std::endl;
                                        list.delNode(phoneNum, "phoneNum");
                                        file.change(list);
                                        flag = false;
                                        break;
                                    }
                                    default: {
                                        std::cout << "������������������\n>";
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                        case 6: {
                            if (!list.getSize()) {
                                std::cout << "----------------------------------------" << std::endl;
                                std::cout << "��ǰ����Ϊ��" << std::endl;
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
                            std::cout << "������������������\n>";
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
                    std::cout << "��ǰ����Ϊ��" << std::endl;
                    break;
                }
                std::cout << "----------------------------------------" << std::endl;
                std::cout << "�������Ӧ����ǰ�ı������ѯ��Ϣ" << std::endl
                          << "1: �����������о�ȷ��ѯ" << std::endl
                          << "2: ���ݵ绰���о�ȷ��ѯ" << std::endl
                          << "3: ���ݵ�ַ����ģ����ѯ" << std::endl
                          << "4: ��������ѯ" << std::endl
                          << "5: ��������û���Ϣ" << std::endl
                          << "0: �˳�ϵͳ����������" << std::endl;
                std::cout << ">";
                int typeNum_;
                bool flag = true;
                while (flag && std::cin >> typeNum_) {
                    switch (typeNum_) {
                        case 1: {
                            std::cout << "----------------------------------------" << std::endl;
                            std::cout << "���������ѯ�û���������";
                            std::string name;
                            std::cin >> name;
                            auto user = list.fetchNode(name, "name");
                            if (user) {
                                std::cout << *user;
                            } else {
                                std::cout << "���û�������" << std::endl;
                            }
                            break;
                        }
                        case 2: {
                            std::cout << "----------------------------------------" << std::endl;
                            std::cout << "���������ѯ�û��ĵ绰��";
                            std::string phoneNum;
                            std::cin >> phoneNum;
                            auto user = list.fetchNode(phoneNum, "phoneNum");
                            if (user) {
                                std::cout << user;
                            } else {
                                std::cout << "���û�������" << std::endl;
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
                            std::cout << "������Ŀ�����ͣ�";
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
                            std::cout << "������������������\n>";
                            break;
                        }
                    }
                    if(typeNum_) {
                        std::cout << "----------------------------------------" << std::endl;
                        std::cout << "�������Ӧ����ǰ�ı������ѯ��Ϣ" << std::endl
                                  << "1: �����������о�ȷ��ѯ" << std::endl
                                  << "2: ���ݵ绰���о�ȷ��ѯ" << std::endl
                                  << "3: ���ݵ�ַ����ģ����ѯ" << std::endl
                                  << "4: ��������ѯ" << std::endl
                                  << "5: ��������û���Ϣ" << std::endl
                                  << "0: �˳�ϵͳ����������" << std::endl;
                        std::cout << ">";
                    }
                }
                break;
            }
            default: {
                std::cout << "������������������\n>";
            }
        }
    }

    file.change(list);
    system("pause");
    return 0;
}

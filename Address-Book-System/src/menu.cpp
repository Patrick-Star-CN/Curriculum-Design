//
// Created by 15928 on 2022/5/3.
//

#include "../include/menu.h"

void Menu::displayMainMenu() {
    std::cout << "----------------------------------------" << std::endl;
    std::cout << "�������Ӧ����ǰ�ı��������ͨѶ¼����ϵͳ" << std::endl
              << "1: ������û���Ϣ" << std::endl
              << "2: ��ѯ�û���Ϣ" << std::endl
              << "3: ����û���Ϣ" << std::endl
              << "4: �޸��û���Ϣ" << std::endl
              << "5: ɾ���û���Ϣ" << std::endl
              << "6: ���û���Ϣ��������" << std::endl
              << "0: �˳�ϵͳ����������" << std::endl;
    std::cout << ">";
}

void Menu::displayFetchMenu() {
    std::cout << "----------------------------------------" << std::endl;
    std::cout << "�������Ӧ����ǰ�ı������ѯ��Ϣ" << std::endl
              << "1: �����������о�ȷ��ѯ" << std::endl
              << "2: ���ݵ绰���о�ȷ��ѯ" << std::endl
              << "3: ���ݵ�ַ����ģ����ѯ" << std::endl
              << "4: ��������ѯ" << std::endl
              << "0: �����ϼ��˵�" << std::endl;
    std::cout << ">";
}

void Menu::displayChangeMenu() {
    std::cout << "----------------------------------------" << std::endl;
    std::cout << "�������Ӧ����ǰ�ı�����޸���Ϣ" << std::endl
              << "1: �������������޸���Ϣ" << std::endl
              << "2: ���ݵ绰�����޸���Ϣ" << std::endl
              << "0: �����ϼ��˵�" << std::endl;
    std::cout << ">";
}

void Menu::displayDeleteMenu() {
    std::cout << "----------------------------------------" << std::endl;
    std::cout << "�������Ӧ����ǰ�ı����ɾ���û�" << std::endl
              << "1: ������������ɾ��" << std::endl
              << "2: ���ݵ绰����ɾ��" << std::endl
              << "0: �����ϼ��˵�" << std::endl;
    std::cout << ">";
}

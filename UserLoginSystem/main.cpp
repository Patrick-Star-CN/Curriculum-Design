#include <QApplication>
#include "MainWindow/mainwindow.h"

int main(int argc, char *argv[]) {
    UserLoginSystem system;
    system.init();
    QApplication a(argc, argv);
    MainWindow w;
    w.init(&system);
    w.setWindowTitle("用户登录系统");
    w.show();
    return QApplication::exec();
}

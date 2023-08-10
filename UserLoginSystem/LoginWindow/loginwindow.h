//
// Created by 卿无言 on 2022/11/20.
//

#ifndef USERLOGINSYSTEM_LOGINWINDOW_H
#define USERLOGINSYSTEM_LOGINWINDOW_H

#include <QWidget>
#include <QMessageBox>
#include "../LoginSystem/UserLoginSystem.h"

QT_BEGIN_NAMESPACE
namespace Ui { class LoginWindow; }
QT_END_NAMESPACE

class LoginWindow : public QWidget {
Q_OBJECT

public:
    explicit LoginWindow(QWidget *parent = nullptr);

    ~LoginWindow() override;

    void setSystem(UserLoginSystem *);

signals:

    void sendUsername(QString);

    void cancel();

private:
    Ui::LoginWindow *ui;

    UserLoginSystem *system;

private slots:

    void on_loginBtn_clicked();

    void on_cancelBtn_clicked();
};


#endif //USERLOGINSYSTEM_LOGINWINDOW_H

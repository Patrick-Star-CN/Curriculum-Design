//
// Created by 卿无言 on 2022/11/20.
//

#ifndef USERLOGINSYSTEM_REGWINDOW_H
#define USERLOGINSYSTEM_REGWINDOW_H

#include <QWidget>
#include <QMessageBox>
#include "../LoginSystem/UserLoginSystem.h"

QT_BEGIN_NAMESPACE
namespace Ui { class RegWindow; }
QT_END_NAMESPACE

class RegWindow : public QWidget {
Q_OBJECT

public:
    explicit RegWindow(QWidget *parent = nullptr);

    ~RegWindow() override;

    void setSystem(UserLoginSystem *s);

signals:

    void sendUsername(QString);

    void cancel();

private:
    Ui::RegWindow *ui;

    UserLoginSystem *system;

private slots:

    void on_regBtn_clicked();

    void on_cancelBtn_clicked();
};


#endif //USERLOGINSYSTEM_REGWINDOW_H

#ifndef USERLOGINSYSTEM_LOGOUTWINDOW_H
#define USERLOGINSYSTEM_LOGOUTWINDOW_H

#include <QWidget>
#include <QMessageBox>
#include "../LoginSystem/UserLoginSystem.h"

QT_BEGIN_NAMESPACE
namespace Ui { class LogoutWindow; }
QT_END_NAMESPACE

class LogoutWindow : public QWidget {
Q_OBJECT

public:
    explicit LogoutWindow(QWidget *parent = nullptr);

    ~LogoutWindow() override;

    void getData(QString);

    void setSystem(UserLoginSystem *s);

private:
    Ui::LogoutWindow *ui;

    UserLoginSystem *system;

signals:

    void sendUsername(QString);

    void cancel();

    void quitSignal();

private slots:

    void on_logoutBtn_clicked();

    void on_cancelBtn_clicked();
};


#endif //USERLOGINSYSTEM_LOGOUTWINDOW_H

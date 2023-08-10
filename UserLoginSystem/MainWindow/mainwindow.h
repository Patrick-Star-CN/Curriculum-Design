#ifndef USERLOGINSYSTEM_MAINWINDOW_H
#define USERLOGINSYSTEM_MAINWINDOW_H

#include <QWidget>
#include "../LoginWindow/loginwindow.h"
#include "../RegWindow/regwindow.h"
#include "../ChPwdWindow/chpwdwindow.h"
#include "../LogoutWindow/logoutwindow.h"

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class MainWindow : public QWidget {
Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = nullptr);

    ~MainWindow() override;

    void init(UserLoginSystem *);

signals:

    void sendUsername(QString);

    void cancel();

    void quitSignal();

private:
    Ui::MainWindow *ui;

    LoginWindow *loginWindow;

    RegWindow *regWindow;

    ChPwdWindow *chPwdWindow;

    LogoutWindow *logoutWindow;

    UserLoginSystem *system;

private slots:

    void on_regBtn_clicked();

    void on_loginBtn_clicked();

    void on_chPwdBtn_clicked();

    void on_logoutBtn_clicked();

    void on_cancelBtn_clicked();

    void quit();

    void mainFrame(const QString &);

    void reShow();
};


#endif //USERLOGINSYSTEM_MAINWINDOW_H

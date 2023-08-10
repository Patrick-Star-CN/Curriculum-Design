#include "loginwindow.h"
#include "ui_LoginWindow.h"


LoginWindow::LoginWindow(QWidget *parent) :
        QWidget(parent), ui(new Ui::LoginWindow) {
    ui->setupUi(this);
    ui->pwdInput->setEchoMode(QLineEdit::Password);
}

LoginWindow::~LoginWindow() {
    delete ui;
}

void LoginWindow::on_loginBtn_clicked() {
    String username(ui->usernameInput->text().toLatin1().data());
    String pwd(ui->pwdInput->text().toLatin1().data());
    try {
        system->login(username, pwd);
    } catch (UserNotFindException &e) {
        QString dlgTitle = "登录异常";
        QString strInfo = "这个用户好像不存在诶";
        QMessageBox::information(this, dlgTitle, strInfo,
                                 QMessageBox::Ok, QMessageBox::NoButton);
        return;
    } catch (WrongPwdException &e) {
        QString dlgTitle = "登录异常";
        QString strInfo = "密码好像输错了诶";
        QMessageBox::information(this, dlgTitle, strInfo,
                                 QMessageBox::Ok, QMessageBox::NoButton);
        return;
    }
    QString dlgTitle = "登录成功";
    QString strInfo = "恭喜你，登录成功了";
    QMessageBox::information(this, dlgTitle, strInfo,
                             QMessageBox::Ok, QMessageBox::NoButton);
    emit sendUsername(ui->usernameInput->text());
    ui->usernameInput->setText("");
    ui->pwdInput->setText("");
    this->close();
}

void LoginWindow::on_cancelBtn_clicked() {
    ui->usernameInput->setText("");
    ui->pwdInput->setText("");
    emit cancel();
    this->close();
}

void LoginWindow::setSystem(UserLoginSystem *s) {
    system = s;
}

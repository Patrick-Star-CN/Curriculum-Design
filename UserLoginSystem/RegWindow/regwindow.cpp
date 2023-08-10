#include "regwindow.h"
#include "ui_RegWindow.h"


RegWindow::RegWindow(QWidget *parent) :
        QWidget(parent), ui(new Ui::RegWindow) {
    ui->setupUi(this);
    ui->pwdInput->setEchoMode(QLineEdit::Password);
    ui->pwdInput_2->setEchoMode(QLineEdit::Password);
}

RegWindow::~RegWindow() {
    delete ui;
}

void RegWindow::on_regBtn_clicked() {
    String username(ui->usernameInput->text().toLatin1().data());
    String pwd(ui->pwdInput->text().toLatin1().data());
    String pwdAgain(ui->pwdInput_2->text().toLatin1().data());
    try {
        system->registor(username, pwd, pwdAgain);
    } catch (UserExistedException &e) {
        QString dlgTitle = "注册异常";
        QString strInfo = "这个用户名好像已经被注册了诶";
        QMessageBox::information(this, dlgTitle, strInfo,
                                 QMessageBox::Ok, QMessageBox::NoButton);
        return;
    } catch (WrongPwdException &e) {
        QString dlgTitle = "注册异常";
        QString strInfo = "两次密码好像输的不一致诶";
        QMessageBox::information(this, dlgTitle, strInfo,
                                 QMessageBox::Ok, QMessageBox::NoButton);
        return;
    } catch (UsernameException &e) {
        QString dlgTitle = "注册异常";
        QString strInfo = "用户名好像不符合要求诶，请确保用户名是由6-12位数字、大小写字母组成的";
        QMessageBox::information(this, dlgTitle, strInfo,
                                 QMessageBox::Ok, QMessageBox::NoButton);
        return;
    } catch (PwdException &e) {
        QString dlgTitle = "注册异常";
        QString strInfo = "密码好像不符合要求诶，请确保密码是由6-12位数字、大小写字母组成的";
        QMessageBox::information(this, dlgTitle, strInfo,
                                 QMessageBox::Ok, QMessageBox::NoButton);
        return;
    }
    QString dlgTitle = "注册成功";
    QString strInfo = "恭喜你，注册成功了";
    QMessageBox::information(this, dlgTitle, strInfo,
                             QMessageBox::Ok, QMessageBox::NoButton);
    emit sendUsername(ui->usernameInput->text());
    ui->usernameInput->setText("");
    ui->pwdInput->setText("");
    ui->pwdInput_2->setText("");
    this->close();
}

void RegWindow::on_cancelBtn_clicked() {
    ui->usernameInput->setText("");
    ui->pwdInput->setText("");
    ui->pwdInput_2->setText("");
    emit cancel();
    this->close();
}

void RegWindow::setSystem(UserLoginSystem *s) {
    system = s;
}

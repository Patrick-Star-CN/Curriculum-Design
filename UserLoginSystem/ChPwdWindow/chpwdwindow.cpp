#include "chpwdwindow.h"
#include "ui_ChPwdWindow.h"


ChPwdWindow::ChPwdWindow(QWidget *parent) :
        QWidget(parent), ui(new Ui::ChPwdWindow) {
    ui->setupUi(this);
    ui->oldPwdInput->setEchoMode(QLineEdit::Password);
    ui->pwdInput->setEchoMode(QLineEdit::Password);
    ui->pwdInput_2->setEchoMode(QLineEdit::Password);
}

ChPwdWindow::~ChPwdWindow() {
    delete ui;
}

void ChPwdWindow::on_chPwdBtn_clicked() {
    QString qUsername = ui->usernameLabel->text();
    String username(qUsername.toLatin1().data());
    String oldPwd(ui->oldPwdInput->text().toLatin1().data());
    String pwd(ui->pwdInput->text().toLatin1().data());
    String pwdAgain(ui->pwdInput_2->text().toLatin1().data());
    try {
        system->updatePassword(username, oldPwd, pwd, pwdAgain);
    } catch (UserNotFindException &e) {
        QString dlgTitle = "修改密码异常";
        QString strInfo = "这个用户好像不存在诶";
        QMessageBox::information(this, dlgTitle, strInfo,
                                 QMessageBox::Ok, QMessageBox::NoButton);
        ui->usernameLabel->setText(qUsername);
        return;
    } catch (WrongPwdException &e) {
        QString dlgTitle = "修改密码异常";
        QString strInfo = "密码好像输错了诶";
        QMessageBox::information(this, dlgTitle, strInfo,
                                 QMessageBox::Ok, QMessageBox::NoButton);
        ui->usernameLabel->setText(qUsername);
        return;
    } catch (WrongOldPwdException &e) {
        QString dlgTitle = "修改密码异常";
        QString strInfo = "原密码好像输错了诶";
        QMessageBox::information(this, dlgTitle, strInfo,
                                 QMessageBox::Ok, QMessageBox::NoButton);
        ui->usernameLabel->setText(qUsername);
        return;
    } catch (PwdException &e) {
        QString dlgTitle = "修改密码异常";
        QString strInfo = "密码好像不符合要求诶，请确保密码是由6-12位数字、大小写字母组成的";
        QMessageBox::information(this, dlgTitle, strInfo,
                                 QMessageBox::Ok, QMessageBox::NoButton);
        return;
    }
    QString dlgTitle = "修改密码成功";
    QString strInfo = "恭喜你，密码修改成功了";
    QMessageBox::information(this, dlgTitle, strInfo,
                             QMessageBox::Ok, QMessageBox::NoButton);
    ui->usernameLabel->setText("");
    ui->pwdInput->setText("");
    ui->oldPwdInput->setText("");
    ui->pwdInput_2->setText("");
    emit cancel();
    this->close();
}

void ChPwdWindow::on_cancelBtn_clicked() {
    ui->usernameLabel->setText("");
    ui->pwdInput->setText("");
    ui->oldPwdInput->setText("");
    ui->pwdInput_2->setText("");
    emit cancel();
    this->close();
}

void ChPwdWindow::getData(QString username) {
    ui->usernameLabel->setText(username);
}

void ChPwdWindow::setSystem(UserLoginSystem *s) {
    system = s;
}

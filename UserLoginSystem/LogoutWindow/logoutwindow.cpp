#include "logoutwindow.h"
#include "ui_LogoutWindow.h"


LogoutWindow::LogoutWindow(QWidget *parent) :
        QWidget(parent), ui(new Ui::LogoutWindow) {
    ui->setupUi(this);
    ui->pwdInput->setEchoMode(QLineEdit::Password);
}

LogoutWindow::~LogoutWindow() {
    delete ui;
}

void LogoutWindow::on_logoutBtn_clicked() {
    String username(ui->usernameLabel->text().toLatin1().data());
    String pwd(ui->pwdInput->text().toLatin1().data());
    try {
        system->deleteUser(username, pwd);
    } catch (UserNotFindException &e) {
        QString dlgTitle = "注销异常";
        QString strInfo = "这个用户好像不存在诶";
        QMessageBox::information(this, dlgTitle, strInfo,
                                 QMessageBox::Ok, QMessageBox::NoButton);
        return;
    } catch (WrongPwdException &e) {
        QString dlgTitle = "注销异常";
        QString strInfo = "密码好像输错了诶";
        QMessageBox::information(this, dlgTitle, strInfo,
                                 QMessageBox::Ok, QMessageBox::NoButton);
        return;
    }
    QString dlgTitle = "注销成功";
    QString strInfo = "恭喜你，注销成功了";
    QMessageBox::information(this, dlgTitle, strInfo,
                             QMessageBox::Ok, QMessageBox::NoButton);
    ui->usernameLabel->setText("");
    ui->pwdInput->setText("");
    emit quitSignal();
    this->close();
}

void LogoutWindow::on_cancelBtn_clicked() {
    ui->usernameLabel->setText("");
    ui->pwdInput->setText("");
    emit cancel();
    this->close();
}

void LogoutWindow::getData(QString username) {
    ui->usernameLabel->setText(username);
}

void LogoutWindow::setSystem(UserLoginSystem *s) {
    system = s;
}

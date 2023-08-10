#include "mainwindow.h"
#include "ui_MainWindow.h"

MainWindow::MainWindow(QWidget *parent) :
        QWidget(parent), ui(new Ui::MainWindow) {
    ui->setupUi(this);
}

MainWindow::~MainWindow() {
    system->save();
    delete ui;
}

void MainWindow::on_regBtn_clicked() {
    this->hide();
    regWindow->show();
}

void MainWindow::on_loginBtn_clicked() {
    this->hide();
    loginWindow->show();
}

void MainWindow::mainFrame(const QString &username) {
    this->show();
    ui->mainLabel->setVisible(false);
    ui->regBtn->setVisible(false);
    ui->loginBtn->setVisible(false);
    ui->welcomeLabel->setVisible(true);
    ui->chPwdBtn->setVisible(true);
    ui->cancelBtn->setVisible(true);
    ui->logoutBtn->setVisible(true);
    ui->usernameLabel->setVisible(true);
    ui->usernameLabel->setText(username);
}

void MainWindow::reShow() {
    this->show();
}

void MainWindow::on_chPwdBtn_clicked() {
    this->hide();
    chPwdWindow->getData(ui->usernameLabel->text());
    chPwdWindow->show();
}

void MainWindow::on_logoutBtn_clicked() {
    this->hide();
    logoutWindow->getData(ui->usernameLabel->text());
    logoutWindow->show();
}

void MainWindow::on_cancelBtn_clicked() {
    quit();
}

void MainWindow::quit() {
    ui->mainLabel->setVisible(true);
    ui->regBtn->setVisible(true);
    ui->loginBtn->setVisible(true);
    ui->welcomeLabel->setVisible(false);
    ui->chPwdBtn->setVisible(false);
    ui->cancelBtn->setVisible(false);
    ui->logoutBtn->setVisible(false);
    ui->usernameLabel->setVisible(false);
    this->show();
}

void MainWindow::init(UserLoginSystem *s) {
    system = s;
    loginWindow = new LoginWindow;
    loginWindow->setWindowTitle("登录窗口");
    loginWindow->setSystem(system);
    regWindow = new RegWindow;
    regWindow->setWindowTitle("注册窗口");
    regWindow->setSystem(system);
    chPwdWindow = new ChPwdWindow;
    chPwdWindow->setWindowTitle("修改密码窗口");
    chPwdWindow->setSystem(system);
    logoutWindow = new LogoutWindow;
    logoutWindow->setWindowTitle("注销窗口");
    logoutWindow->setSystem(system);
    connect(loginWindow, SIGNAL(sendUsername(QString)), this, SLOT(mainFrame(QString)));
    connect(regWindow, SIGNAL(sendUsername(QString)), this, SLOT(mainFrame(QString)));
    connect(loginWindow, SIGNAL(cancel()), this, SLOT(reShow()));
    connect(regWindow, SIGNAL(cancel()), this, SLOT(reShow()));
    connect(chPwdWindow, SIGNAL(cancel()), this, SLOT(reShow()));
    connect(logoutWindow, SIGNAL(cancel()), this, SLOT(reShow()));
    connect(logoutWindow, SIGNAL(quitSignal()), this, SLOT(quit()));
    ui->welcomeLabel->setVisible(false);
    ui->chPwdBtn->setVisible(false);
    ui->cancelBtn->setVisible(false);
    ui->logoutBtn->setVisible(false);
}

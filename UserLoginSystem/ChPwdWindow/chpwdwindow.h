#ifndef USERLOGINSYSTEM_CHPWDWINDOW_H
#define USERLOGINSYSTEM_CHPWDWINDOW_H

#include <QWidget>
#include <QMessageBox>
#include "../LoginSystem/UserLoginSystem.h"

QT_BEGIN_NAMESPACE
namespace Ui { class ChPwdWindow; }
QT_END_NAMESPACE

class ChPwdWindow : public QWidget {
Q_OBJECT

public:
    explicit ChPwdWindow(QWidget *parent = nullptr);

    ~ChPwdWindow() override;

    void getData(QString);

    void setSystem(UserLoginSystem *s);

private:
    Ui::ChPwdWindow *ui;

    UserLoginSystem *system;

signals:

    void cancel();

private slots:

    void on_chPwdBtn_clicked();

    void on_cancelBtn_clicked();

};


#endif //USERLOGINSYSTEM_CHPWDWINDOW_H

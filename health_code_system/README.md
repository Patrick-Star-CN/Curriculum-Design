# health_code_system

大二下 Java web 课设

一个用前端基础三件套 + jQuery + jsp + servlet + jdbc + mysql 实现的健康码管理系统。

## 环境要求

* jdk: openjdk version "11.0.16.1" 2022-08-12
* tomcat: apache-tomcat-10.1.0
* database：MySQL 8.0.30-arm64

## 启动方式

通过 maven 插件或 idea 将原码打成一个 jar 包，放入 tomcat 的 webapps 文件夹下，启动 tomcat 即可。

也可以通过 idea 配置 tomcat 的形式集成启动。

## 项目结构

```
.
├── README.md                                            // 项目介绍文件
├── logs                                                 // 日志保存路径
├── pom.xml                                              // 项目依赖配置文件
├── src
│   ├── main
│   │   ├── java
│   │   │   └── team
│   │   │       └── star
│   │   │           └── healthcodesystem
│   │   │               ├── config                        // 配置包
│   │   │               ├── constant                      // 常量包
│   │   │               ├── controller                    // servlet 包
│   │   │               ├── dao                           // 数据控制器包
│   │   │               ├── dto                           // 数据传输对象包
│   │   │               ├── exception                     // 自定义异常包
│   │   │               ├── handler                       // 过滤器包
│   │   │               ├── model                         // 实体包
│   │   │               ├── service                       // 服务（业务逻辑）包
│   │   │               └── util                          // 工具包
│   │   ├── resources                                     // 配置文件
│   │   │   ├── config.example.yaml                       // 配置文件样例
│   │   │   ├── log4j.properties                          // 日志配置文件
│   │   │   ├── student.xlsx                              // 学生数据导入文件
│   │   │   └── teacher.xlsx                              // 教师数据导入文件
│   │   └── webapp                                        // 前端目录
│   │       ├── WEB-INF
│   │       │   └── web.xml
│   │       ├── admin.jsp
│   │       ├── class.jsp
│   │       ├── class_query.jsp
│   │       ├── college.jsp
│   │       ├── index.jsp
│   │       ├── login.jsp
│   │       ├── major.jsp
│   │       ├── major_query.jsp
│   │       ├── record.jsp
│   │       ├── record_form.jsp
│   │       ├── record_query.jsp
│   │       ├── static                                    // 静态目录
│   │       │   └── style.css
│   │       ├── student.jsp
│   │       ├── student_query.jsp
│   │       ├── teacher.jsp
│   │       └── teacher_query.jsp
│   └── test                                              // 测试类
│       ├── java
│       │   └── team
│       │       └── star
│       │           └── healthcodesystem
│       │               ├── dao
│       │               │   ├── ClassDaoTest.java
│       │               │   ├── CollegeDaoTest.java
│       │               │   ├── MajorDaoTest.java
│       │               │   ├── RecordDaoTest.java
│       │               │   ├── StudentDaoTest.java
│       │               │   └── TeacherDtoTest.java
│       │               ├── service
│       │               │   ├── ClassServiceTest.java
│       │               │   ├── CollegeServiceTest.java
│       │               │   ├── MajorServiceTest.java
│       │               │   ├── RecordServiceTest.java
│       │               │   ├── StudentServiceTest.java
│       │               │   └── TeacherServiceTest.java
│       │               └── util
│       │                   └── UtilTest.java
│       └── resources
│           └── log4j.properties
└── target
```
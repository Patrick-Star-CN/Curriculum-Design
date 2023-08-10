package team.star.healthcodesystem.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import team.star.healthcodesystem.constant.ErrorCode;
import team.star.healthcodesystem.exception.AppException;
import team.star.healthcodesystem.model.Student;
import team.star.healthcodesystem.model.Teacher;
import team.star.healthcodesystem.service.StudentService;
import team.star.healthcodesystem.service.TeacherService;
import team.star.healthcodesystem.util.JWT;

import java.net.URLEncoder;

/**
 * 登录控制器
 *
 * @author Patrick_Star
 * @version 1.0
 */
@WebServlet(name = "LoginController", urlPatterns = "/auth/login")
public class LoginController extends HttpServlet {
    private final StudentService studentService = new StudentService();
    private final TeacherService teacherService = new TeacherService();

    @Override
    protected void doGet(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp) throws java.io.IOException {
        resp.sendRedirect("/login.jsp");
    }

    @Override
    protected void doPost(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp) throws java.io.IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String type = req.getParameter("type");
        if (type == null) {
            String s = URLEncoder.encode("请选择一个用户类型", "UTF-8");
            resp.sendRedirect("/login.jsp?error=" + s);
            return;
        }
        switch (type) {
            case "student":
                try {
                    Student student = studentService.login(Long.parseLong(username), password);
                    if (student != null) {
                        req.getSession().setAttribute("user_id", JWT.createJWT(String.valueOf(student.getId())));
                        req.getSession().setAttribute("type", "student");
                        resp.sendRedirect("/index.jsp");
                    } else {
                        resp.sendRedirect("/login.jsp");
                    }
                } catch (AppException e) {
                    String s = URLEncoder.encode(e.getMessage(), "UTF-8");
                    resp.sendRedirect("/login.jsp?error=" + s);
                }
                break;
            case "teacher":
                try {
                    Teacher teacher = teacherService.loginByIdentity(Long.parseLong(username), password);
                    if (teacher != null) {
                        req.getSession().setAttribute("user_id", JWT.createJWT(String.valueOf(teacher.getId())));
                        req.getSession().setAttribute("type", "teacher");
                        resp.sendRedirect("/index.jsp");
                    } else {
                        resp.sendRedirect("/login.jsp");
                    }
                } catch (AppException e) {
                    String s = URLEncoder.encode(e.getMessage(), "UTF-8");
                    resp.sendRedirect("/login.jsp?error=" + s);
                }
                break;
            case "admin":
                try {
                    Teacher teacher = teacherService.login(Long.parseLong(username), password);
                    if (teacher != null) {
                        if (teacher.getType() == Teacher.Type.TEACHER) {
                            resp.sendRedirect("/login.jsp?error=" + URLEncoder.encode(ErrorCode.NO_PERMISSION.getMessage(), "UTF-8"));
                            return;
                        }
                        req.getSession().setAttribute("user_id", JWT.createJWT(String.valueOf(teacher.getId())));
                        req.getSession().setAttribute("type", teacher.getType().name());
                        resp.sendRedirect("/admin.jsp");
                    } else {
                        resp.sendRedirect("/login.jsp");
                    }
                } catch (AppException e) {
                    resp.sendRedirect("/login.jsp?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
                }
                break;
        }
    }
}


package team.star.healthcodesystem.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import team.star.healthcodesystem.constant.ErrorCode;
import team.star.healthcodesystem.dto.ClassDto;
import team.star.healthcodesystem.dto.MajorDto;
import team.star.healthcodesystem.dto.PaginationDto;
import team.star.healthcodesystem.exception.AppException;
import team.star.healthcodesystem.model.Teacher;
import team.star.healthcodesystem.service.ClassService;
import team.star.healthcodesystem.service.MajorService;
import team.star.healthcodesystem.service.TeacherService;
import team.star.healthcodesystem.util.JWT;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Objects;

/**
 * 班级管理控制器
 *
 * @author Patrick_Star
 * @version 1.0
 */
@WebServlet(name = "ClassController", urlPatterns = {"/class/query/json", "/class/query", "/class/add", "/class/delete", "/class/update"})
public class ClassController extends HttpServlet {
    private final TeacherService teacherService = new TeacherService();
    private final ClassService classService = new ClassService();
    private final MajorService majorService = new MajorService();
    @Override
    protected void doGet(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp) throws jakarta.servlet.ServletException, java.io.IOException {
        if (!"/class/query/json".equals(req.getServletPath()) &&
                !"/class/query".equals(req.getServletPath())) {
            resp.sendRedirect("/admin.jsp");
            return;
        }
        HttpSession session = req.getSession();
        Teacher teacher = teacherService.queryTeacher(Long.parseLong(Objects.requireNonNull(JWT.verifyJWT((String) session.getAttribute("user_id")))));
        if (teacher.getType() == Teacher.Type.TEACHER || teacher.getType() == Teacher.Type.COLLEGE_ADMIN) {
            String s = URLEncoder.encode(ErrorCode.NO_PERMISSION.getMessage(), "UTF-8");
            resp.sendRedirect("/admin.jsp?error=" + s);
            return;
        }
        try {
            PaginationDto<ClassDto> classList = null;
            String name = req.getParameter("name");
            if ("/class/query/json".equals(req.getServletPath())) {
                classList = name == null ? classService.queryClassList(1, 100)
                        : classService.queryClassListByMajor(name, 1, 100);
                resp.setContentType("application/json;charset=utf-8");
                JSONArray jsonArray = JSON.parseArray(JSONObject.toJSONString(classList.getData()));
                resp.getWriter().println(jsonArray);
                return;
            }
            ArrayList<MajorDto> majorList = null;
            String type = req.getParameter("type");
            int page = Integer.parseInt(req.getParameter("page"));
            int size = Integer.parseInt(req.getParameter("size"));
            switch (type) {
                case "all":
                    classList = classService.queryClassList(page, size);
                    majorList = majorService.queryAllMajor(1, 100).getData();
                    break;
                case "major":
                    classList = classService.queryClassListByMajor(name, page, size);
                    majorList = new ArrayList<>();
                    majorList.add(MajorDto.builder()
                            .id(majorService.queryMajor(name).getId())
                            .name(name).build());
                    break;
            }
            if (classList == null) {
                resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(ErrorCode.PARAM_ERROR.getMessage(), "UTF-8"));
                return;
            }
            req.setAttribute("classes", classList.getData());
            req.setAttribute("page", classList.getPage());
            req.setAttribute("majors", majorList);
            req.setAttribute("total_page", (int) Math.ceil((double) classList.getTotal() / classList.getSize()));
            req.getRequestDispatcher("/class.jsp").forward(req, resp);
        } catch (AppException | ServletException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
        } catch (NumberFormatException | NullPointerException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(ErrorCode.PARAM_ERROR.getMessage(), "UTF-8"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        Teacher teacher = teacherService.queryTeacher(Long.parseLong(Objects.requireNonNull(JWT.verifyJWT((String) session.getAttribute("user_id")))));
        if (teacher.getType() != Teacher.Type.SUPER_ADMIN) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(ErrorCode.NO_PERMISSION.getMessage(), "UTF-8"));
        }
        switch (req.getRequestURI()) {
            case "/class/add":
                add(req, resp);
                break;
            case "/class/delete":
                delete(req, resp);
                break;
            case "/class/update":
                update(req, resp);
                break;
        }
    }

    void add(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String type = req.getParameter("type");
        try {
            String className = req.getParameter("className");
            String majorName = req.getParameter("majorName");
            classService.addClass(className, majorName);
            resp.sendRedirect("/class/query?" + type + "&page=1&size=10");
        } catch (AppException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
        } catch (NumberFormatException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(ErrorCode.PARAM_ERROR.getMessage(), "UTF-8"));
        }
    }

    void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String type = req.getParameter("type");
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            classService.deleteClass(id);
        } catch (NumberFormatException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(ErrorCode.PARAM_ERROR.getMessage(), "UTF-8"));
            return;
        } catch (AppException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
            return;
        }
        resp.sendRedirect("/class/query?" + type + "&page=1&size=10");
    }

    void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String type = req.getParameter("type");
        try {
            int id = Integer.parseInt(req.getParameter("classId"));
            String name = req.getParameter("className");
            String majorName = req.getParameter("classMajorName");
            classService.updateClass(id, name, majorName);
        } catch (NumberFormatException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(ErrorCode.PARAM_ERROR.getMessage(), "UTF-8"));
            return;
        } catch (AppException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
            return;
        }
        resp.sendRedirect("/class/query?" + type + "&page=1&size=10");
    }
}

package team.star.healthcodesystem.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.*;
import team.star.healthcodesystem.constant.ErrorCode;
import team.star.healthcodesystem.dto.PaginationDto;
import team.star.healthcodesystem.exception.AppException;
import team.star.healthcodesystem.model.College;
import team.star.healthcodesystem.model.Teacher;
import team.star.healthcodesystem.service.CollegeService;
import team.star.healthcodesystem.service.TeacherService;
import team.star.healthcodesystem.util.JWT;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Objects;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@WebServlet(name = "CollegeController", urlPatterns = {"/college/query/json", "/college/query", "/college/add", "/college/update", "/college/delete"})
public class CollegeController extends HttpServlet {
    private final TeacherService teacherService = new TeacherService();
    private final CollegeService collegeService = new CollegeService();
    @Override
    protected void doGet(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp) throws jakarta.servlet.ServletException, java.io.IOException {
        if (!"/college/query/json".equals(req.getServletPath()) &&
                !"/college/query".equals(req.getServletPath())) {
            resp.sendRedirect("/admin.jsp");
        }
        HttpSession session = req.getSession();
        Teacher teacher = teacherService.queryTeacher(Long.parseLong(Objects.requireNonNull(JWT.verifyJWT((String) session.getAttribute("user_id")))));
        if (teacher.getType() == Teacher.Type.TEACHER) {
            String s = URLEncoder.encode(ErrorCode.NO_PERMISSION.getMessage(), "UTF-8");
            resp.sendRedirect("/admin.jsp?error=" + s);
            return;
        }
        PaginationDto<College> collegeList = collegeService.queryCollegeList(1, 100);
        if ("/college/query/json".equals(req.getServletPath())) {
            resp.setContentType("application/json;charset=utf-8");
            if (teacher.getType() == Teacher.Type.COLLEGE_ADMIN) {
                collegeList.getData().removeIf(college -> !college.getId().equals(teacher.getCollegeId()));
            }
            JSONArray jsonArray = JSON.parseArray(JSONObject.toJSONString(collegeList.getData()));
            resp.getWriter().println(jsonArray);
            return;
        }
        req.setAttribute("colleges", collegeList.getData());
        req.setAttribute("page", collegeList.getPage());
        req.setAttribute("total_page", (int) Math.ceil((double) collegeList.getTotal() / collegeList.getSize()));
        req.getRequestDispatcher("/college.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession session = req.getSession();
        Teacher teacher = teacherService.queryTeacher(Long.parseLong(Objects.requireNonNull(JWT.verifyJWT((String) session.getAttribute("user_id")))));
        if (teacher.getType() == Teacher.Type.TEACHER) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(ErrorCode.NO_PERMISSION.getMessage(), "UTF-8"));
            return;
        }
        switch (req.getRequestURI()) {
            case "/college/add":
                add(req, resp);
                break;
            case "/college/delete":
                delete(req, resp);
                break;
            case "/college/update":
                update(req, resp);
                break;
        }
    }

    void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String collegeName = req.getParameter("collegeName");
            collegeService.addCollege(collegeName);
            resp.sendRedirect("/college/query?page=1&size=10");
        } catch (AppException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
        } catch (NumberFormatException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(ErrorCode.PARAM_ERROR.getMessage(), "UTF-8"));
        } catch (EmptyFileException e) {
            resp.sendRedirect("/college/query?page=1&size=10");
        }
    }

    void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            collegeService.deleteCollege(id);
        } catch (NumberFormatException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(ErrorCode.PARAM_ERROR.getMessage(), "UTF-8"));
            return;
        } catch (AppException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
            return;
        }
        resp.sendRedirect("/college/query?page=1&size=10");
    }

    void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("collegeId"));
            String name = req.getParameter("collegeName");
            collegeService.updateCollege(id, name);
        } catch (NumberFormatException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(ErrorCode.PARAM_ERROR.getMessage(), "UTF-8"));
            return;
        } catch (AppException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
            return;
        }
        resp.sendRedirect("/college/query?page=1&size=10");
    }
}

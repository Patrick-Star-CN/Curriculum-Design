package team.star.healthcodesystem.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import team.star.healthcodesystem.constant.ErrorCode;
import team.star.healthcodesystem.dto.MajorDto;
import team.star.healthcodesystem.dto.PaginationDto;
import team.star.healthcodesystem.exception.AppException;
import team.star.healthcodesystem.model.College;
import team.star.healthcodesystem.model.Teacher;
import team.star.healthcodesystem.service.CollegeService;
import team.star.healthcodesystem.service.MajorService;
import team.star.healthcodesystem.service.TeacherService;
import team.star.healthcodesystem.util.JWT;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Objects;

/**
 * 专业控制器
 *
 * @author Patrick_Star
 * @version 1.0
 */
@WebServlet(name = "MajorController", urlPatterns = {"/major/query/json", "/major/query", "/major/add", "/major/update", "/major/delete"})
public class MajorController extends HttpServlet {
    private final TeacherService teacherService = new TeacherService();
    private final MajorService majorService = new MajorService();
    private final CollegeService collegeService = new CollegeService();

    @Override
    protected void doGet(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp) throws jakarta.servlet.ServletException, java.io.IOException {
        if (!"/major/query/json".equals(req.getServletPath()) &&
                !"/major/query".equals(req.getServletPath())) {
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
            PaginationDto<MajorDto> majorList = null;
            String name = req.getParameter("name");
            if ("/major/query/json".equals(req.getServletPath())) {
                majorList = name == null ? majorService.queryAllMajor(1, 100)
                        : majorService.queryMajorListByCollege(name, 1, 100);
                resp.setContentType("application/json;charset=utf-8");
                JSONArray jsonArray = JSON.parseArray(JSONObject.toJSONString(majorList.getData()));
                resp.getWriter().println(jsonArray);
                return;
            }
            ArrayList<College> collegeList = null;
            String type = req.getParameter("type");
            int page = Integer.parseInt(req.getParameter("page"));
            int size = Integer.parseInt(req.getParameter("size"));
            switch (type) {
                case "all":
                    majorList = majorService.queryAllMajor(page, size);
                    collegeList = collegeService.queryCollegeList(1, 100).getData();
                    break;
                case "college":
                    majorList = majorService.queryMajorListByCollege(name, page, size);
                    collegeList = new ArrayList<>();
                    collegeList.add(collegeService.queryCollege(name));
                    break;
            }
            if (majorList == null) {
                resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(ErrorCode.PARAM_ERROR.getMessage(), "UTF-8"));
                return;
            }
            req.setAttribute("majors", majorList.getData());
            req.setAttribute("colleges", collegeList);
            req.setAttribute("page", majorList.getPage());
            req.setAttribute("total_page", (int) Math.ceil((double) majorList.getTotal() / majorList.getSize()));
            req.getRequestDispatcher("/major.jsp").forward(req, resp);
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
            case "/major/add":
                add(req, resp);
                break;
            case "/major/delete":
                delete(req, resp);
                break;
            case "/major/update":
                update(req, resp);
                break;
        }
    }

    void add(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String type = req.getParameter("type");
        try {
            String majorName = req.getParameter("majorName");
            String collegeName = req.getParameter("collegeName");
            majorService.addMajor(majorName, collegeName);
            resp.sendRedirect("/major/query?" + type + "&page=1&size=10");
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
            majorService.deleteMajor(id);
        } catch (NumberFormatException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(ErrorCode.PARAM_ERROR.getMessage(), "UTF-8"));
            return;
        } catch (AppException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
            return;
        }
        resp.sendRedirect("/major/query?" + type + "&page=1&size=10");
    }

    void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String type = req.getParameter("type");
        try {
            int id = Integer.parseInt(req.getParameter("majorId"));
            String name = req.getParameter("majorName");
            String collegeName = req.getParameter("majorCollegeName");
            majorService.updateMajor(id, name, collegeName);
        } catch (NumberFormatException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(ErrorCode.PARAM_ERROR.getMessage(), "UTF-8"));
            return;
        } catch (AppException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
            return;
        }
        resp.sendRedirect("/major/query?" + type + "&page=1&size=10");
    }
}

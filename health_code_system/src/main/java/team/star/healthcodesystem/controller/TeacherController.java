package team.star.healthcodesystem.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.*;
import team.star.healthcodesystem.constant.ErrorCode;
import team.star.healthcodesystem.dto.PaginationDto;
import team.star.healthcodesystem.dto.TeacherDto;
import team.star.healthcodesystem.exception.AppException;
import team.star.healthcodesystem.model.Teacher;
import team.star.healthcodesystem.service.TeacherService;
import team.star.healthcodesystem.util.JWT;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Objects;

/**
 * 教师控制器
 *
 * @author Patrick_Star
 * @version 1.0
 */
@MultipartConfig
@WebServlet(name = "TeacherController", urlPatterns = {"/teacher/add", "/teacher/delete", "/teacher/update", "/teacher/query", "/teacher/change_pwd"})
public class TeacherController extends HttpServlet {
    private final TeacherService teacherService = new TeacherService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!"/teacher/query".equals(req.getRequestURI())) {
            resp.sendRedirect("/admin.jsp");
        }
        HttpSession session = req.getSession();
        Teacher teacher = teacherService.queryTeacher(Long.parseLong(Objects.requireNonNull(JWT.verifyJWT((String) session.getAttribute("user_id")))));
        if (teacher.getType() == Teacher.Type.TEACHER) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(ErrorCode.NO_PERMISSION.getMessage(), "UTF-8"));
            return;
        }
        try {
            String type = req.getParameter("type");
            String name = req.getParameter("name");
            int page = Integer.parseInt(req.getParameter("page"));
            int size = Integer.parseInt(req.getParameter("size"));
            PaginationDto<TeacherDto> teachers = null;
            switch (type) {
                case "all":
                    teachers = teacherService.queryAllTeachers(page, size);
                    break;
                case "college":
                    teachers = teacherService.queryTeachersByCollege(name, page, size);
                    break;
            }
            if (teachers == null) {
                resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(ErrorCode.PARAM_ERROR.getMessage(), "UTF-8"));
                return;
            }
            req.setAttribute("teachers", teachers.getData());
            req.setAttribute("page", teachers.getPage());
            req.setAttribute("total_page", (int) Math.ceil((double) teachers.getTotal() / teachers.getSize()));
            req.getRequestDispatcher("/teacher.jsp").forward(req, resp);
        } catch (AppException | ServletException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        Teacher teacher = teacherService.queryTeacher(Long.parseLong(Objects.requireNonNull(JWT.verifyJWT((String) session.getAttribute("user_id")))));
        if (teacher.getType() == Teacher.Type.TEACHER) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(ErrorCode.NO_PERMISSION.getMessage(), "UTF-8"));
            return;
        }
        switch (req.getRequestURI()) {
            case "/teacher/add":
                add(req, resp);
                break;
            case "/teacher/delete":
                delete(req, resp);
                break;
            case "/teacher/update":
                update(req, resp);
                break;
            case "/teacher/change_pwd":
                changePwd(teacher, req, resp);
                break;
        }
    }

    void add(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String typeIn = req.getParameter("type");
        try {
            Part filePart = req.getPart("file");
            InputStream inputStream = filePart.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();
            rowIterator.next();
            rowIterator.next();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Cell cell1 = row.getCell(0);
                long id = Long.parseLong(cell1.getStringCellValue());
                Cell cell2 = row.getCell(1);
                String name = cell2.getStringCellValue();
                Cell cell3 = row.getCell(2);
                String iid = cell3.getStringCellValue();
                Cell cell4 = row.getCell(3);
                Teacher.Type type = Teacher.Type.valueOf(cell4.getStringCellValue());
                Cell cell5 = row.getCell(4);
                String collegeName = cell5.getStringCellValue();
                teacherService.addTeacher(id, name, iid, type, collegeName);
            }
            inputStream.close();
            resp.sendRedirect("/teacher/query?" + typeIn + "&page=1&size=10");
        } catch (AppException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
        } catch (NumberFormatException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(ErrorCode.PARAM_ERROR.getMessage(), "UTF-8"));
        } catch (EmptyFileException e) {
            resp.sendRedirect("/teacher/query?" + typeIn + "&page=1&size=10");
        }
    }

    void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String type = req.getParameter("type");
        try {
            long id = Long.parseLong(req.getParameter("id"));
            teacherService.deleteTeacher(id);
        } catch (NumberFormatException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(ErrorCode.PARAM_ERROR.getMessage(), "UTF-8"));
            return;
        } catch (AppException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
            return;
        }
        resp.sendRedirect("/teacher/query?" + type + "&page=1&size=10");
    }

    void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String typeIn = req.getParameter("type");
        try {
            long id = Long.parseLong(req.getParameter("teacherId"));
            String name = req.getParameter("teacherName");
            String iid = req.getParameter("teacherIid");
            String collegeName = req.getParameter("teacherCollegeName");
            Teacher.Type type = Teacher.Type.valueOf(req.getParameter("teacherType"));
            teacherService.changeInfo(id, name, collegeName, iid, type);
        } catch (NumberFormatException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(ErrorCode.PARAM_ERROR.getMessage(), "UTF-8"));
            return;
        } catch (AppException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
            return;
        }
        resp.sendRedirect("/teacher/query?" + typeIn + "&page=1&size=10");
    }

    void changePwd(Teacher teacher, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String type = req.getParameter("type");
        String idStr = req.getParameter("teacherId");
        try {
            long id = idStr != null ? Long.parseLong(idStr) : teacher.getId();
            String isAdmin = req.getParameter("isAdmin");
            String oldPwd = req.getParameter("oldPwd");
            String newPwd = req.getParameter("newPwd");
            teacherService.changePassword(id, oldPwd, newPwd, "admin".equals(isAdmin));
        } catch (NumberFormatException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(ErrorCode.PARAM_ERROR.getMessage(), "UTF-8"));
            return;
        } catch (AppException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
            return;
        }
        if (idStr == null) {
            resp.sendRedirect("/admin.jsp");
            return;
        }
        resp.sendRedirect("/teacher/query?" + type + "&page=1&size=10");
    }
}

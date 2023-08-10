package team.star.healthcodesystem.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.*;
import team.star.healthcodesystem.constant.ErrorCode;
import team.star.healthcodesystem.dto.PaginationDto;
import team.star.healthcodesystem.dto.StudentDto;
import team.star.healthcodesystem.exception.AppException;
import team.star.healthcodesystem.model.Teacher;
import team.star.healthcodesystem.service.StudentService;
import team.star.healthcodesystem.service.TeacherService;
import team.star.healthcodesystem.util.JWT;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Objects;

/**
 * 学生控制器
 *
 * @author Patrick_Star
 * @version 1.0
 */
@MultipartConfig
@WebServlet(name = "StudentController", urlPatterns = {"/student/add", "/student/delete", "/student/update", "/student/query"})
public class StudentController extends HttpServlet {
    private final TeacherService teacherService = new TeacherService();
    private final StudentService studentService = new StudentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!"/student/query".equals(req.getRequestURI())) {
            resp.sendRedirect("/admin.jsp");
            return;
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
            PaginationDto<StudentDto> students = null;
            switch (type) {
                case "all":
                    students = studentService.queryAllStudentList(page, size);
                    break;
                case "college":
                    students = studentService.queryStudentListByCollege(name, page, size);
                    break;
                case "major":
                    students = studentService.queryStudentListByMajor(name, page, size);
                    break;
                case "class":
                    students = studentService.queryStudentListByClass(name, page, size);
                    break;
            }
            if (students == null) {
                resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(ErrorCode.PARAM_ERROR.getMessage(), "UTF-8"));
                return;
            }
            req.setAttribute("students", students.getData());
            req.setAttribute("page", students.getPage());
            req.setAttribute("total_page", (int) Math.ceil((double) students.getTotal() / students.getSize()));
            req.getRequestDispatcher("/student.jsp").forward(req, resp);
        } catch (AppException | ServletException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
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
            case "/student/add":
                add(req, resp);
                break;
            case "/student/delete":
                delete(req, resp);
                break;
            case "/student/update":
                update(req, resp);
                break;
        }
    }

    void add(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String type = req.getParameter("type");
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
                String collegeName = cell4.getStringCellValue();
                Cell cell5 = row.getCell(4);
                String majorName = cell5.getStringCellValue();
                Cell cell6 = row.getCell(5);
                String className = cell6.getStringCellValue();
                studentService.addStudent(id, name, iid, collegeName, majorName, className);
            }
            inputStream.close();
            resp.sendRedirect("/student/query?" + type + "&page=1&size=10");
        } catch (AppException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
        } catch (NumberFormatException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(ErrorCode.PARAM_ERROR.getMessage(), "UTF-8"));
        } catch (EmptyFileException e) {
            resp.sendRedirect("/student/query?" + type + "&page=1&size=10");
        }
    }

    void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String type = req.getParameter("type");
        try {
            long id = Long.parseLong(req.getParameter("id"));
            studentService.deleteStudent(id);
        } catch (NumberFormatException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(ErrorCode.PARAM_ERROR.getMessage(), "UTF-8"));
            return;
        } catch (AppException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
            return;
        }
        resp.sendRedirect("/student/query?" + type + "&page=1&size=10");
    }

    void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String type = req.getParameter("type");
        try {
            long id = Long.parseLong(req.getParameter("studentId"));
            String name = req.getParameter("studentName");
            String iid = req.getParameter("studentIid");
            String collegeName = req.getParameter("studentCollegeName");
            String majorName = req.getParameter("studentMajorName");
            String className = req.getParameter("studentClassName");
            studentService.updateStudent(id, name, iid, collegeName, majorName, className);
        } catch (NumberFormatException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(ErrorCode.PARAM_ERROR.getMessage(), "UTF-8"));
            return;
        } catch (AppException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
            return;
        }
        resp.sendRedirect("/student/query?" + type + "&page=1&size=10");
    }
}

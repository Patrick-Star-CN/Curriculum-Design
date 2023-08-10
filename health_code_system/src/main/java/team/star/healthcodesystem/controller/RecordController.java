package team.star.healthcodesystem.controller;

import com.google.zxing.WriterException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.SneakyThrows;
import team.star.healthcodesystem.constant.ErrorCode;
import team.star.healthcodesystem.dto.*;
import team.star.healthcodesystem.exception.AppException;
import team.star.healthcodesystem.model.College;
import team.star.healthcodesystem.model.Record;
import team.star.healthcodesystem.model.Student;
import team.star.healthcodesystem.model.Teacher;
import team.star.healthcodesystem.service.*;
import team.star.healthcodesystem.util.JWT;
import team.star.healthcodesystem.util.QRCode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@WebServlet(name = "RecordController", urlPatterns = {"/record/add", "/record/query", "/record/query/info", "/health_code"})
public class RecordController extends HttpServlet {
    private final TeacherService teacherService = new TeacherService();
    private final StudentService studentService = new StudentService();
    private final RecordService recordService = new RecordService();
    private final CollegeService collegeService = new CollegeService();
    private final MajorService majorService = new MajorService();
    private final ClassService classService = new ClassService();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if ("/health_code".equals(req.getRequestURI())) {
            code(req, resp);
            return;
        }
        if (!"/record/query".equals(req.getRequestURI()) && !"/record/query/info".equals(req.getRequestURI())) {
            resp.sendRedirect("/admin.jsp");
            return;
        }
        HttpSession session = req.getSession();
        String type = req.getParameter("type");
        if ("/record/query/info".equals(req.getRequestURI())) {
            if ("student".equals(type)) {
                Student student = studentService.queryStudent(Long.parseLong(Objects.requireNonNull(JWT.verifyJWT((String) session.getAttribute("user_id")))));
                if (recordService.queryRecord(student.getId(), null) != null) {
                    resp.sendRedirect("/health_code?type=" + type);
                    return;
                }
                req.setAttribute("user", student);
            } else {
                Teacher teacher = teacherService.queryTeacher(Long.parseLong(Objects.requireNonNull(JWT.verifyJWT((String) session.getAttribute("user_id")))));
                if (recordService.queryRecord(teacher.getId(), null) != null) {
                    resp.sendRedirect("/health_code?type=" + type);
                    return;
                }
                req.setAttribute("user", teacher);
            }
            req.getRequestDispatcher("/record_form.jsp").forward(req, resp);
            return;
        }
        Teacher teacher = teacherService.queryTeacher(Long.parseLong(Objects.requireNonNull(JWT.verifyJWT((String) session.getAttribute("user_id")))));
        if (teacher.getType() == Teacher.Type.TEACHER) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(ErrorCode.NO_PERMISSION.getMessage(), "UTF-8"));
            return;
        }
        try {
            String userType = req.getParameter("userType");
            String name = req.getParameter("name");
            String date = req.getParameter("date");
            ArrayList<RecordCountDto> recordCounts = new ArrayList<>();
            ArrayList<RecordDto> records = new ArrayList<>();
            if ("student".equals(userType)) {
                switch (type) {
                    case "all":
                        recordCounts.add(RecordCountDto.builder()
                                .count(recordService.queryStudentCount(date))
                                .totalCount(studentService.queryStudentAllCount())
                                .name("全校").build());
                        PaginationDto<College> collegeList = collegeService.queryCollegeList(1, 100);
                        for (College college : collegeList.getData()) {
                            recordCounts.add(RecordCountDto.builder()
                                    .count(recordService.queryStudentCountByCollege(college.getName(), date))
                                    .totalCount(studentService.queryStudentCountByCollege(college.getName()))
                                    .name(college.getName()).build());
                        }
                        break;
                    case "college":
                        recordCounts.add(RecordCountDto.builder()
                                .count(recordService.queryStudentCountByCollege(name, date))
                                .totalCount(studentService.queryStudentCountByCollege(name))
                                .name("全学院").build());
                        PaginationDto<MajorDto> majorList = majorService.queryMajorListByCollege(name, 1, 100);
                        for (MajorDto major : majorList.getData()) {
                            recordCounts.add(RecordCountDto.builder()
                                    .count(recordService.queryStudentCountByMajor(major.getName(), date))
                                    .totalCount(studentService.queryStudentCountByMajor(major.getName()))
                                    .name(major.getName()).build());
                        }
                        break;
                    case "major":
                        recordCounts.add(RecordCountDto.builder()
                                .count(recordService.queryStudentCountByMajor(name, date))
                                .totalCount(studentService.queryStudentCountByMajor(name))
                                .name("全专业").build());
                        PaginationDto<ClassDto> classList = classService.queryClassListByMajor(name, 1, 100);
                        for (ClassDto clazz : classList.getData()) {
                            recordCounts.add(RecordCountDto.builder()
                                    .count(recordService.queryStudentCountByClass(clazz.getName(), date))
                                    .totalCount(studentService.queryStudentCountByClass(clazz.getName()))
                                    .name(clazz.getName()).build());
                        }
                        break;
                    case "class":
                        recordCounts.add(RecordCountDto.builder()
                                .count(recordService.queryStudentCountByClass(name, date))
                                .totalCount(studentService.queryStudentCountByClass(name))
                                .name("全班").build());
                        PaginationDto<StudentDto> studentList = studentService.queryStudentListByClass(name, 1, 100);
                        for (StudentDto student : studentList.getData()) {
                            Record record = recordService.queryRecord(student.getId(), date);
                            records.add(RecordDto.builder()
                                    .codeColor(record != null ? record.getColor().name() : "未填报")
                                    .name(student.getName()).build());
                        }
                        break;
                }
            } else {
                switch (type) {
                    case "all":
                        recordCounts.add(RecordCountDto.builder()
                                .count(recordService.queryTeacherCount(date))
                                .totalCount(teacherService.queryAllTeachersCount())
                                .name("全校").build());
                        PaginationDto<College> collegeList = collegeService.queryCollegeList(1, 100);
                        for (College college : collegeList.getData()) {
                            recordCounts.add(RecordCountDto.builder()
                                    .count(recordService.queryTeacherCountByCollege(college.getName(), date))
                                    .totalCount(teacherService.queryTeachersByCollegeCount(college.getName()))
                                    .name(college.getName()).build());
                        }
                        break;
                    case "college":
                        recordCounts.add(RecordCountDto.builder()
                                .count(recordService.queryTeacherCountByCollege(name, date))
                                .totalCount(teacherService.queryTeachersByCollegeCount(name))
                                .name("全学院").build());
                        PaginationDto<TeacherDto> teacherList = teacherService.queryTeachersByCollege(name, 1, 100);
                        for (TeacherDto teacherDto : teacherList.getData()) {
                            Record record = recordService.queryRecord(teacherDto.getId(), date);
                            records.add(RecordDto.builder()
                                    .codeColor(record != null ? record.getColor().name() : "未填报")
                                    .name(teacherDto.getName()).build());
                        }
                        break;
                }
            }
            req.setAttribute("recordCounts", recordCounts);
            req.setAttribute("records", records);
            req.getRequestDispatcher("/record.jsp").forward(req, resp);
        } catch (AppException | ServletException e) {
            resp.sendRedirect("/admin.jsp?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if ("/record/add".equals(req.getRequestURI())) {
            add(req, resp);
        }
    }

    void add(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            long userId = Long.parseLong(req.getParameter("userId"));
            String phoneNum = req.getParameter("phoneNum");
            Boolean haveBeenWuhan = "yes".equals(req.getParameter("haveBeenWuhan"));
            Boolean haveBeenAbroad = "yes".equals(req.getParameter("haveBeenAbroad"));
            Boolean haveBeenTouchPatient = "yes".equals(req.getParameter("haveBeenTouchPatient"));
            Boolean isPatient = "yes".equals(req.getParameter("isPatient"));
            int healthStatus = Integer.parseInt(req.getParameter("healthStatus"));
            recordService.addRecord(userId, phoneNum, haveBeenWuhan, haveBeenAbroad, haveBeenTouchPatient, isPatient, healthStatus);
        } catch (AppException e) {
            resp.sendRedirect("/record/query/info?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
        } catch (NumberFormatException e) {
            resp.sendRedirect("/record/query/info?error=" + URLEncoder.encode(ErrorCode.PARAM_ERROR.getMessage(), "UTF-8"));
        }
    }

    void code(HttpServletRequest req, HttpServletResponse resp) throws WriterException, IOException {
        String type = req.getParameter("type");
        long id;
        String name = null;
        String collegeName = null;
        if ("student".equals(type)) {
            Student student = studentService.queryStudent(Long.parseLong(Objects.requireNonNull(JWT.verifyJWT((String) req.getSession().getAttribute("user_id")))));
            id = student.getId();
            name = student.getName();
            collegeName = collegeService.queryCollege(student.getCollegeId()).getName();
        } else {
            Teacher teacher = teacherService.queryTeacher(Long.parseLong(Objects.requireNonNull(JWT.verifyJWT((String) req.getSession().getAttribute("user_id")))));
            id = teacher.getId();
            name = teacher.getName();
            collegeName = collegeService.queryCollege(teacher.getCollegeId()).getName();
        }
        String content = "{\"id\":" + id + ",\"name\":\"" + name + "\",\"college\":\"" + collegeName + "\"}";
        Record record = recordService.queryRecord(id, null);
        if (record == null) {
            resp.setStatus(404);
            return;
        }
        Color color = null;
        switch (record.getColor()) {
            case GREEN:
                color = Color.GREEN;
                break;
            case YELLOW:
                color = Color.YELLOW;
                break;
            case RED:
                color = Color.RED;
                break;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(QRCode.genBarcode(content, color), "png", baos);
        byte[] bytes = baos.toByteArray();
        resp.setContentType("image/png");
        try {
            OutputStream outputStream = resp.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

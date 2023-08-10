package team.star.score_system.controller;

import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.star.score_system.constant.ErrorCode;
import team.star.score_system.dto.*;
import team.star.score_system.entity.Admin;
import team.star.score_system.entity.College;
import team.star.score_system.exception.AppException;
import team.star.score_system.service.*;
import team.star.score_system.util.ExcelUtil;

import java.io.IOException;
import java.util.List;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Validated
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin
public class AdminController {
    final AdminService adminService;
    final CollegeService collegeService;
    final MajorService majorService;
    final TeacherService teacherService;
    final ClassService classService;
    final StudentService studentService;
    final CourseService courseService;
    final CourseScheduleServer courseScheduleService;
    final ScoreService scoreService;

    @PostMapping("/login")
    public Object login(@Param("id") Integer id, @Param("password") String password) {
        Admin admin = adminService.login(id, password);
        StpUtil.logout(admin.getId());
        StpUtil.login(admin.getId());
        return AjaxResp.SUCCESS();
    }

    @PutMapping("/password")
    public Object changePassword(@RequestBody ChangePasswordDto dto) {
        int userName = StpUtil.getLoginIdAsInt();
        adminService.changePassword(userName, dto.getOldPassword(), dto.getNewPassword());
        return AjaxResp.SUCCESS();
    }

    @PutMapping
    public Object updateAdmin(@RequestBody ChangeRoleDto dto) {
        int userName = StpUtil.getLoginIdAsInt();
        if (!adminService.getRole(userName).equals(Admin.Role.SUPER_ADMIN)) {
            throw new AppException(ErrorCode.PERMISSION_DENIED);
        }
        adminService.updateRole(dto.getId(), dto.getRole());
        return AjaxResp.SUCCESS();
    }

    @PostMapping
    public Object addAdmin(@RequestBody AdminDto admin) {
        adminService.insert(admin.getId(), admin.getPassword(), admin.getRole());
        return AjaxResp.SUCCESS();
    }

    @DeleteMapping
    public Object deleteAdmin(@Param("id") int id) {
        adminService.delete(id);
        return AjaxResp.SUCCESS();
    }

    @GetMapping("/college")
    public Object getCollege(@Param("page") int page, @Param("size") int size) {
        return AjaxResp.SUCCESS(collegeService.getColleges(page, size));
    }

    @PostMapping("/college")
    public Object addCollege(@RequestBody College college) {
        int userName = StpUtil.getLoginIdAsInt();
        if (adminService.getRole(userName).equals(Admin.Role.COLLEGE_ADMIN)) {
            throw new AppException(ErrorCode.PERMISSION_DENIED);
        }
        collegeService.addCollege(college.getId(), college.getName());
        return AjaxResp.SUCCESS();
    }

    @DeleteMapping("/college")
    public Object deleteCollege(@Param("id") int id) {
        int userName = StpUtil.getLoginIdAsInt();
        if (adminService.getRole(userName).equals(Admin.Role.COLLEGE_ADMIN)) {
            throw new AppException(ErrorCode.PERMISSION_DENIED);
        }
        collegeService.deleteCollege(id);
        return AjaxResp.SUCCESS();
    }

    @PutMapping("/college")
    public Object updateCollege(@RequestBody College college) {
        int userName = StpUtil.getLoginIdAsInt();
        if (adminService.getRole(userName).equals(Admin.Role.COLLEGE_ADMIN)) {
            throw new AppException(ErrorCode.PERMISSION_DENIED);
        }
        collegeService.updateCollege(college.getId(), college.getName());
        return AjaxResp.SUCCESS();
    }

    @PutMapping("/major")
    public Object updateMajor(@RequestBody MajorDto major) {
        majorService.updateMajor(major.getId(), major.getName(), major.getCollege());
        return AjaxResp.SUCCESS();
    }

    @GetMapping("/major")
    public Object getMajor(@Param("page") int page, @Param("size") int size, @Param("type") String type, @Param("condition") String condition) {
        int userName = StpUtil.getLoginIdAsInt();
        if (adminService.getRole(userName).equals(Admin.Role.COLLEGE_ADMIN) && "all".equals(type)) {
            throw new AppException(ErrorCode.PERMISSION_DENIED);
        }
        return AjaxResp.SUCCESS(majorService.getMajors(page, size, type, condition));
    }

    @PostMapping("/major")
    public Object addMajor(@RequestBody MajorDto major) {
        majorService.addMajor(major.getId(), major.getName(), major.getCollege());
        return AjaxResp.SUCCESS();
    }

    @DeleteMapping("/major")
    public Object deleteMajor(@Param("id") int id) {
        majorService.deleteMajor(id);
        return AjaxResp.SUCCESS();
    }

    @GetMapping("/teacher")
    public Object getTeacher(@Param("page") int page, @Param("size") int size,
                             @Param("type") String type, @Param("condition") String condition) {
        int userName = StpUtil.getLoginIdAsInt();
        if (adminService.getRole(userName).equals(Admin.Role.COLLEGE_ADMIN) && "all".equals(type)) {
            throw new AppException(ErrorCode.PERMISSION_DENIED);
        }
        return AjaxResp.SUCCESS(teacherService.getTeachers(page, size, type, condition));
    }

    @PostMapping("/teacher")
    public Object addTeacher(@RequestBody TeacherDto teacherDto) {
        teacherService.addTeacher(teacherDto.getId(), teacherDto.getName(), teacherDto.getGender(),
                teacherDto.getBirthYear(), teacherDto.getCollege(), teacherDto.getPhoneNum(), teacherDto.getJobTitle());
        return AjaxResp.SUCCESS();
    }

    @PostMapping("/teacher/file")
    public Object addTeacherByFile(@RequestParam("file") MultipartFile file) throws IOException {
        String name = file.getOriginalFilename();
        if(!name.substring(name.length()-5).equals(".xlsx")){
            throw new AppException(ErrorCode.FILE_ERROR);
        }
        List<TeacherDto> list = ExcelUtil.excelToTeacherList(file.getInputStream());
        for (TeacherDto teacherDto : list) {
            teacherService.addTeacher(teacherDto.getId(), teacherDto.getName(), teacherDto.getGender(),
                    teacherDto.getBirthYear(), teacherDto.getCollege(), teacherDto.getPhoneNum(), teacherDto.getJobTitle());
        }
        return AjaxResp.SUCCESS();
    }

    @DeleteMapping("/teacher")
    public Object deleteTeacher(@Param("id") int id) {
        teacherService.deleteTeacher(id);
        return AjaxResp.SUCCESS();
    }

    @PutMapping("/teacher")
    public Object updateTeacher(@RequestBody TeacherDto teacherDto) {
        teacherService.updateTeacher(teacherDto.getId(), teacherDto.getName(), teacherDto.getGender(),
                teacherDto.getBirthYear(), teacherDto.getCollege(), teacherDto.getPhoneNum(), teacherDto.getJobTitle());
        return AjaxResp.SUCCESS();
    }

    @PutMapping("/teacher/password")
    public Object changeTeacherPassword(@RequestBody ResetPasswordDto dto) {
        teacherService.changePassword(dto.getId(), null, null, true);
        return AjaxResp.SUCCESS();
    }

    @GetMapping("/class")
    public Object getClass(@Param("page") int page, @Param("size") int size, @Param("type") String type, @Param("condition") String condition) {
        int userName = StpUtil.getLoginIdAsInt();
        if (adminService.getRole(userName).equals(Admin.Role.COLLEGE_ADMIN) && "all".equals(type)) {
            throw new AppException(ErrorCode.PERMISSION_DENIED);
        }
        return AjaxResp.SUCCESS(classService.getClasses(page, size, type, condition));
    }

    @PostMapping("/class")
    public Object addClass(@RequestBody ClassDto classDto) {
        classService.addClass(classDto.getId(), classDto.getName(), classDto.getMajor(), classDto.getTeacherId());
        return AjaxResp.SUCCESS();
    }

    @DeleteMapping("/class")
    public Object deleteClass(@Param("id") int id) {
        classService.deleteClass(id);
        return AjaxResp.SUCCESS();
    }

    @PutMapping("/class")
    public Object updateClass(@RequestBody ClassDto classDto) {
        classService.updateClass(classDto.getId(), classDto.getName(), classDto.getMajor(), classDto.getTeacherId());
        return AjaxResp.SUCCESS();
    }

    @GetMapping("/student")
    public Object getStudent(@Param("page") int page, @Param("size") int size, @Param("type") String type, @Param("condition") String condition) {
        int userName = StpUtil.getLoginIdAsInt();
        if (adminService.getRole(userName).equals(Admin.Role.COLLEGE_ADMIN) && "all".equals(type)) {
            throw new AppException(ErrorCode.PERMISSION_DENIED);
        }
        return AjaxResp.SUCCESS(studentService.getStudents(page, size, type, condition));
    }

    @PostMapping("/student")
    public Object addStudent(@RequestBody StudentDto studentDto) {
        studentService.addStudent(studentDto.getId(), studentDto.getName(), studentDto.getGender(),
                studentDto.getBirthYear(), studentDto.getHometown(), studentDto.getClassName());
        return AjaxResp.SUCCESS();
    }

    @PostMapping("/student/file")
    public Object addStudentByFile(@RequestParam("file") MultipartFile file) throws IOException {
        String name = file.getOriginalFilename();
        if(!name.substring(name.length()-5).equals(".xlsx")){
            throw new AppException(ErrorCode.FILE_ERROR);
        }
        List<StudentDto> list = ExcelUtil.excelToStudentList(file.getInputStream());
        for (StudentDto studentDto : list) {
            studentService.addStudent(studentDto.getId(), studentDto.getName(), studentDto.getGender(),
                    studentDto.getBirthYear(), studentDto.getHometown(), studentDto.getClassName());
        }
        return AjaxResp.SUCCESS();
    }

    @DeleteMapping("/student")
    public Object deleteStudent(@Param("id") long id) {
        studentService.deleteStudent(id);
        return AjaxResp.SUCCESS();
    }

    @PutMapping("/student")
    public Object updateStudent(@RequestBody StudentDto studentDto) {
        studentService.updateStudent(studentDto.getId(), studentDto.getName(), studentDto.getGender(),
                studentDto.getBirthYear(), studentDto.getHometown(), studentDto.getClassName());
        return AjaxResp.SUCCESS();
    }

    @PutMapping("/student/password")
    public Object changeStudentPassword(@RequestBody ResetPasswordDto dto) {
        studentService.updatePassword(dto.getId(), null, null, true);
        return AjaxResp.SUCCESS();
    }

    @GetMapping("/student/hometown_count")
    public Object getStudentHometownCount() {
        return AjaxResp.SUCCESS(studentService.getHometownCounts());
    }

    @GetMapping("/student/hometown")
    public Object getStudentByHometown(@Param("page") int page, @Param("size") int size, @Param("hometown") String hometown) {
        return AjaxResp.SUCCESS(studentService.getStudentsByHometown(page, size, hometown));
    }

    @GetMapping("/course")
    public Object getCourse(@Param("page") int page, @Param("size") int size,
                            @Param("type") String type, @Param("condition") String condition) {
        int userName = StpUtil.getLoginIdAsInt();
        if (adminService.getRole(userName).equals(Admin.Role.COLLEGE_ADMIN) && "all".equals(type)) {
            throw new AppException(ErrorCode.PERMISSION_DENIED);
        }
        return AjaxResp.SUCCESS(courseService.getCourse(page, size, type, condition));
    }

    @PostMapping("/course")
    public Object addCourse(@RequestBody CourseDto courseDto) {
        courseService.addCourse(courseDto.getId(), courseDto.getName(), courseDto.getCollegeName(), courseDto.getTerm(),
                courseDto.getExaminationMethod(), courseDto.getCredit(), courseDto.getAttribute(), courseDto.getYear());
        return AjaxResp.SUCCESS();
    }

    @PutMapping("/course")
    public Object updateCourse(@RequestBody CourseDto courseDto) {
        courseService.updateCourse(courseDto.getId(), courseDto.getName(), courseDto.getCollegeName(), courseDto.getTerm(),
                courseDto.getExaminationMethod(), courseDto.getCredit(), courseDto.getHours(), courseDto.getAttribute(),
                courseDto.getYear());
        return AjaxResp.SUCCESS();
    }

    @DeleteMapping("/course")
    public Object deleteCourse(@Param("id") int id) {
        courseService.deleteCourse(id);
        return AjaxResp.SUCCESS();
    }

    @GetMapping("/course/term")
    public Object getTermList() {
        return AjaxResp.SUCCESS(courseService.getTermList());
    }

    @GetMapping("/course/attribute")
    public Object getAttributeList() {
        return AjaxResp.SUCCESS(courseService.getAttributeList());
    }

    @GetMapping("/course/examination_method")
    public Object getExaminationMethodList() {
        return AjaxResp.SUCCESS(courseService.getExaminationMethodList());
    }

    @GetMapping("/course_schedule")
    public Object getCourseSchedule(@Param("page") int page, @Param("size") int size,
                                    @Param("type") String type, @Param("condition") String condition) {
        int userName = StpUtil.getLoginIdAsInt();
        if (adminService.getRole(userName).equals(Admin.Role.COLLEGE_ADMIN) && "all".equals(type)) {
            throw new AppException(ErrorCode.PERMISSION_DENIED);
        }
        return AjaxResp.SUCCESS(courseScheduleService.getCourseSchedule(page, size, type, condition));
    }

    @PostMapping("/course_schedule")
    public Object addCourseSchedule(@RequestBody CourseScheduleDto courseScheduleDto) {
        courseScheduleService.addCourseSchedule(courseScheduleDto);
        return AjaxResp.SUCCESS();
    }

    @PutMapping("/course_schedule")
    public Object updateCourseSchedule(@RequestBody CourseScheduleDto courseScheduleDto) {
        courseScheduleService.updateCourseSchedule(courseScheduleDto);
        return AjaxResp.SUCCESS();
    }

    @DeleteMapping("/course_schedule")
    public Object deleteCourseSchedule(@RequestBody CourseScheduleDto courseScheduleDto) {
        courseScheduleService.deleteCourseSchedule(courseScheduleDto.getCourseId(), courseScheduleDto.getClassName());
        return AjaxResp.SUCCESS();
    }

    @GetMapping("/score")
    public Object getScore(@Param("page") int page, @Param("size") int size,
                           @Param("term") String term, @Param("type") String type, @Param("condition") String condition) {
        return AjaxResp.SUCCESS(scoreService.getScore(page, size, term, type, condition));
    }

    @GetMapping("/score/all_year")
    public Object getAllYear(@Param("year") int year, @Param("type") String type, @Param("condition") String condition) {
        return AjaxResp.SUCCESS(scoreService.getAllYearScore(year, type, condition));
    }

    @GetMapping("/score/ave")
    public Object getAveScore(@Param("page") int page, @Param("size") int size,
                              @Param("type") String type, @Param("condition") String condition) {
        return AjaxResp.SUCCESS(scoreService.getAvgScore(page, size, type, condition));
    }
}

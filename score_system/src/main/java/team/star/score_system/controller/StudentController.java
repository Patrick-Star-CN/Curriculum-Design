package team.star.score_system.controller;

import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.star.score_system.dto.AjaxResp;
import team.star.score_system.dto.ChangePasswordDto;
import team.star.score_system.entity.Student;
import team.star.score_system.service.CourseScheduleServer;
import team.star.score_system.service.CourseService;
import team.star.score_system.service.ScoreService;
import team.star.score_system.service.StudentService;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Validated
@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
@CrossOrigin
public class StudentController {
    final StudentService studentService;
    final CourseService courseService;
    final ScoreService scoreService;
    final CourseScheduleServer courseScheduleServer;

    @PostMapping("/login")
    public Object login(Long id, String password) {
        Student student = studentService.login(id, password);
        StpUtil.logout(student.getId());
        StpUtil.login(student.getId());
        return AjaxResp.SUCCESS();
    }

    @PutMapping("/password")
    public Object changePassword(@RequestBody ChangePasswordDto dto) {
        studentService.updatePassword(StpUtil.getLoginIdAsLong(), dto.getOldPassword(), dto.getNewPassword(), false);
        return AjaxResp.SUCCESS();
    }

    @GetMapping("/course")
    public Object getCourse(int page, int size, String term) {
        return AjaxResp.SUCCESS(courseService.getStudentCourseList(page, size, StpUtil.getLoginIdAsLong(), term));
    }

    @GetMapping("/score")
    public Object getScore(int page, int size, String term) {
        return AjaxResp.SUCCESS(scoreService.getScore(page, size, term, "student", String.valueOf(StpUtil.getLoginIdAsLong())));
    }

    @GetMapping("/class_table")
    public Object getClassTable(String term, int page, int size) {
        return AjaxResp.SUCCESS(courseScheduleServer.getCourseScheduleByClass(StpUtil.getLoginIdAsLong(), term, page, size));
    }

    @GetMapping("/all_score")
    public Object getAllScore() {
        return AjaxResp.SUCCESS(scoreService.getAvgScoreByStudentId(StpUtil.getLoginIdAsLong()));
    }
}

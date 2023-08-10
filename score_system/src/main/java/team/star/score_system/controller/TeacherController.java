package team.star.score_system.controller;

import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.star.score_system.dto.AjaxResp;
import team.star.score_system.dto.ChangePasswordDto;
import team.star.score_system.entity.Teacher;
import team.star.score_system.service.CourseScheduleServer;
import team.star.score_system.service.ScoreService;
import team.star.score_system.service.TeacherService;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Validated
@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
@CrossOrigin
public class TeacherController {
    final TeacherService teacherService;
    final ScoreService scoreService;
    final CourseScheduleServer courseScheduleServer;

    @PostMapping("/login")
    public Object login(@Param("id") int id, @Param("password") String password) {
        Teacher teacher = teacherService.login(id, password);
        StpUtil.logout(teacher.getId());
        StpUtil.login(teacher.getId());
        return AjaxResp.SUCCESS();
    }

    @PutMapping("/password")
    public Object changePassword(@RequestBody ChangePasswordDto dto) {
        int userName = StpUtil.getLoginIdAsInt();
        teacherService.changePassword(userName, dto.getOldPassword(), dto.getNewPassword(), false);
        return AjaxResp.SUCCESS();
    }

    @GetMapping("/score")
    public Object getScore(@RequestParam int page,
                           @RequestParam int size,
                           @RequestParam(name = "course_id") String courseId,
                           @RequestParam(name = "class_name") String className) {
        return AjaxResp.SUCCESS(scoreService.getScoreByTeacherId(StpUtil.getLoginIdAsLong(), className, courseId, page, size));
    }

    @GetMapping("/ave_score")
    public Object getAveScore(@RequestParam(name = "page") int page,
                           @RequestParam(name = "size") int size) {
        return AjaxResp.SUCCESS(scoreService.getAvgScore(page, size, "teacher", String.valueOf(StpUtil.getLoginIdAsLong())));
    }

    @GetMapping("/class_table")
    public Object getClassTable(@RequestParam(name = "page") int page,
                                @RequestParam(name = "size") int size,
                                @RequestParam(name = "term") String term) {
        return AjaxResp.SUCCESS(courseScheduleServer.getCourseScheduleByTeacher(StpUtil.getLoginIdAsLong(), term, page, size));
    }

    @PostMapping("/score")
    public Object addScore(@RequestParam(name = "student_id") long studentId,
                           @RequestParam(name = "course_id") String courseId,
                           @RequestParam(name = "score") double score,
                           @RequestParam(name = "term") String term) {
        scoreService.updateScore(StpUtil.getLoginIdAsLong(), studentId, courseId, term, score);
        return AjaxResp.SUCCESS();
    }
}

package team.star.score_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.star.score_system.constant.ErrorCode;
import team.star.score_system.dto.PaginationDto;
import team.star.score_system.entity.Teacher;
import team.star.score_system.exception.AppException;
import team.star.score_system.mapper.CollegeMapper;
import team.star.score_system.mapper.TeacherMapper;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class TeacherService {
    final TeacherMapper teacherMapper;
    final CollegeMapper collegeMapper;

    public int getTeacherCount() {
        return teacherMapper.selectCount();
    }

    public void addTeacher(int id, String name, String gender, int birthYear,
                           String college, String phone, String title) {
        if (teacherMapper.checkId(id) != 0
                || collegeMapper.checkName(college) != 1) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        teacherMapper.insert(id, name, gender, birthYear, college, phone, title);
    }

    public void deleteTeacher(int id) {
        if (teacherMapper.checkId(id) != 1) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        teacherMapper.delete(id);
    }

    public void updateTeacher(int id, String name, String gender, int birthYear,
                              String college, String phone, String title) {
        if (teacherMapper.checkId(id) != 1
                || collegeMapper.checkName(college) != 1) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        teacherMapper.update(id, name, gender, birthYear, college, phone, title);
    }

    public PaginationDto<Teacher> getTeachers(int page, int size, String type, String condition) {
        int offset = (page - 1) * size;
        if ("all".equals(type)) {
            return PaginationDto.<Teacher>builder()
                    .data(teacherMapper.selectAll(offset, size))
                    .total(teacherMapper.selectCount())
                    .size(size)
                    .page(page)
                    .build();
        } else {
            if (collegeMapper.checkName(condition) != 1) {
                throw new AppException(ErrorCode.PARAM_ERROR);
            }
            return PaginationDto.<Teacher>builder()
                    .data(teacherMapper.selectByCollegeName(offset, size, condition))
                    .total(teacherMapper.selectCount())
                    .size(size)
                    .page(page)
                    .build();
        }
    }

    public void changePassword(long id, String oldPassword, String newPassword, boolean isAdmin) {
        if (isAdmin) {
            teacherMapper.changePassword("zjut" + id, id);
            return;
        }
        if (teacherMapper.login(id, oldPassword) != 1) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        teacherMapper.changePassword(newPassword, id);
    }

    public Teacher login(int id, String password) {
        if (teacherMapper.login(id, password) != 1) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        return teacherMapper.selectOne(id);
    }
}

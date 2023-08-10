package team.star.score_system.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import team.star.score_system.constant.ErrorCode;
import team.star.score_system.dto.PaginationDto;
import team.star.score_system.entity.Class;
import team.star.score_system.exception.AppException;
import team.star.score_system.mapper.ClassMapper;
import team.star.score_system.mapper.CollegeMapper;
import team.star.score_system.mapper.MajorMapper;
import team.star.score_system.mapper.TeacherMapper;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class ClassService {
    final ClassMapper classMapper;
    final TeacherMapper teacherMapper;
    final MajorMapper majorMapper;
    final CollegeMapper collegeMapper;

    public void addClass(int id, String name, String major, long teacherId) {
        if (classMapper.checkId(id) != 0
                || teacherMapper.checkId(teacherId) != 1
                || majorMapper.checkName(major) != 1) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        classMapper.insert(id, name, major, teacherId);
    }

    public void deleteClass(int id) {
        if (classMapper.checkId(id) != 1) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        classMapper.delete(id);
    }

    public void updateClass(int id, String name, String major, long teacherId) {
        if (classMapper.checkId(id) != 1
                || teacherMapper.checkId(teacherId) != 1
                || majorMapper.checkName(major) != 1) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        classMapper.update(id, name, major, teacherId);
    }

    public PaginationDto<Class> getClasses(int page, int size, String type, String condition) {
        int offset = (page - 1) * size;
        if ("all".equals(type)) {
            return PaginationDto.<Class>builder()
                    .data(classMapper.selectAll(offset, size))
                    .total(classMapper.selectCount())
                    .size(size)
                    .page(page)
                    .build();
        } else if ("college".equals(type)) {
            if (collegeMapper.checkName(condition) != 1) {
                throw new AppException(ErrorCode.PARAM_ERROR);
            }
            return PaginationDto.<Class>builder()
                    .data(classMapper.selectByCollege(offset, size, condition))
                    .total(classMapper.selectCountByCollege(condition))
                    .size(size)
                    .page(page)
                    .build();
        } else {
            if (majorMapper.checkName(condition) != 1) {
                throw new AppException(ErrorCode.PARAM_ERROR);
            }
            return PaginationDto.<Class>builder()
                    .data(classMapper.selectByMajor(offset, size, condition))
                    .total(classMapper.selectCountByMajor(condition))
                    .size(size)
                    .page(page)
                    .build();
        }
    }
}

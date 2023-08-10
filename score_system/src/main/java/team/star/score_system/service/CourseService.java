package team.star.score_system.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import team.star.score_system.constant.ErrorCode;
import team.star.score_system.dto.PaginationDto;
import team.star.score_system.dto.StudentCourseDto;
import team.star.score_system.entity.Course;
import team.star.score_system.exception.AppException;
import team.star.score_system.mapper.CollegeMapper;
import team.star.score_system.mapper.CourseMapper;

import java.util.ArrayList;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class CourseService {
    final CollegeMapper collegeMapper;
    final CourseMapper courseMapper;

    public PaginationDto<Course> getCourse(int page, int size, String type, String condition) {
        int offset = (page - 1) * size;
        if ("all".equals(type)) {
            return PaginationDto.<Course>builder()
                    .data(courseMapper.selectAll(offset, size))
                    .total(courseMapper.selectCount())
                    .page(page)
                    .size(size)
                    .build();
        } else if ("term".equals(type)) {
            return PaginationDto.<Course>builder()
                    .data(courseMapper.selectByTerm(condition, offset, size))
                    .total(courseMapper.selectCountByTerm(condition))
                    .page(page)
                    .size(size)
                    .build();
        } else if ("examination_method".equals(type)) {
            return PaginationDto.<Course>builder()
                    .data(courseMapper.selectByExaminationMethod(condition, offset, size))
                    .total(courseMapper.selectCountByExaminationMethod(condition))
                    .page(page)
                    .size(size)
                    .build();
        } else if ("college_name".equals(type)) {
            return PaginationDto.<Course>builder()
                    .data(courseMapper.selectByCollege(condition, offset, size))
                    .total(courseMapper.selectCountByCollege(condition))
                    .page(page)
                    .size(size)
                    .build();
        } else {
            return PaginationDto.<Course>builder()
                    .data(courseMapper.selectByAttribute(condition, offset, size))
                    .total(courseMapper.selectCountByAttribute(condition))
                    .page(page)
                    .size(size)
                    .build();
        }
    }

    public void addCourse(int id, String name, String collegeName, String term, String examinationMethod,
                          double credit, String attribute, int year) {
        if (id == 0 || name == null || collegeName == null || term == null || examinationMethod == null
            || attribute == null || year == 0) {
            System.out.println(id);
            System.out.println(name);
            System.out.println(collegeName);
            System.out.println(term);
            System.out.println(examinationMethod);
            System.out.println(credit);
            System.out.println(attribute);
            System.out.println(year);
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        if (courseMapper.checkId(id) != 0
                || collegeMapper.checkName(collegeName) != 1) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        courseMapper.insert(id, name, collegeName, term, examinationMethod, credit, attribute, year);
    }

    public void deleteCourse(int id) {
        if (id == 0) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        if (courseMapper.checkId(id) != 1) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        courseMapper.delete(id);
    }

    public void updateCourse(int id, String name, String collegeName, String term, String examinationMethod,
                             double credit, int hours, String attribute, int year) {
        if (id == 0 || name == null || collegeName == null || term == null || examinationMethod == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        if (courseMapper.checkId(id) != 1
                || collegeMapper.checkName(collegeName) != 1) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        courseMapper.update(id, name, term, collegeName, examinationMethod, credit, hours, attribute, year);
    }

    public ArrayList<String> getTermList() {
        return courseMapper.selectAllTerm();
    }

    public ArrayList<String> getExaminationMethodList() {
        return courseMapper.selectAllExaminationMethod();
    }

    public ArrayList<String> getAttributeList() {
        return courseMapper.selectAllAttribute();
    }

    public PaginationDto<StudentCourseDto> getStudentCourseList(int page, int size, long studentId, String term) {
        int offset = (page - 1) * size;
        return PaginationDto.<StudentCourseDto>builder()
                .data(courseMapper.selectStudentCourseByTerm(studentId, term, offset, size))
                .total(courseMapper.selectStudentCourseByTermCount(studentId, term))
                .page(page)
                .size(size)
                .build();
    }
}

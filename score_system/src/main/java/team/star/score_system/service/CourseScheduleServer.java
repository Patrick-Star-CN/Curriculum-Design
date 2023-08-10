package team.star.score_system.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import team.star.score_system.dto.CourseScheduleDto;
import team.star.score_system.dto.PaginationDto;
import team.star.score_system.mapper.CourseScheduleMapper;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class CourseScheduleServer {
    final CourseScheduleMapper courseScheduleMapper;

    public PaginationDto<CourseScheduleDto> getCourseSchedule(int page, int size, String type, String condition) {
        int offset = (page - 1) * size;
        if ("all".equals(type)) {
            return PaginationDto.<CourseScheduleDto>builder()
                    .data(courseScheduleMapper.selectAll(offset, size))
                    .total(courseScheduleMapper.selectAllCount())
                    .page(page)
                    .size(size)
                    .build();
        } else if ("term".equals(type)) {
            return PaginationDto.<CourseScheduleDto>builder()
                    .data(courseScheduleMapper.selectByTerm(condition, offset, size))
                    .total(courseScheduleMapper.selectCountByTerm(condition))
                    .page(page)
                    .size(size)
                    .build();
        } else if ("year".equals(type)) {
            return PaginationDto.<CourseScheduleDto>builder()
                    .data(courseScheduleMapper.selectByYear(Integer.parseInt(condition), offset, size))
                    .total(courseScheduleMapper.selectCountByYear(Integer.parseInt(condition)))
                    .page(page)
                    .size(size)
                    .build();
        } else if ("teacherId".equals(type)) {
            return PaginationDto.<CourseScheduleDto>builder()
                    .data(courseScheduleMapper.selectByTeacherId(Long.parseLong(condition), offset, size))
                    .total(courseScheduleMapper.selectCountByTeacherId(Long.parseLong(condition)))
                    .page(page)
                    .size(size)
                    .build();
        } else if ("className".equals(type)) {
            return PaginationDto.<CourseScheduleDto>builder()
                    .data(courseScheduleMapper.selectByClassName(condition, offset, size))
                    .total(courseScheduleMapper.selectCountByClassName(condition))
                    .page(page)
                    .size(size)
                    .build();
        } else {
            return null;
        }
    }

    public void addCourseSchedule(CourseScheduleDto courseScheduleDto) {
        courseScheduleMapper.insertCourseSchedule(courseScheduleDto.getCourseName(),
                courseScheduleDto.getClassName(), courseScheduleDto.getTeacherId());
    }

    public void deleteCourseSchedule(String courseId, String className) {
        courseScheduleMapper.deleteCourseSchedule(courseId, className);
    }

    public void updateCourseSchedule(CourseScheduleDto courseScheduleDto) {
        courseScheduleMapper.updateCourseSchedule(courseScheduleDto.getCourseId(),
                courseScheduleDto.getClassName(), courseScheduleDto.getTeacherId());
    }

    public PaginationDto<CourseScheduleDto> getCourseScheduleByTeacher(long teacherId, String term, int page, int size) {
        int offset = (page - 1) * size;
        return PaginationDto.<CourseScheduleDto>builder()
                .data(courseScheduleMapper.selectByTermAndTeacherId(term, teacherId, offset, size))
                .total(courseScheduleMapper.selectcountByTermAndTeacherId(term, teacherId))
                .page(page)
                .size(size)
                .build();
    }

    public PaginationDto<CourseScheduleDto> getCourseScheduleByClass(long studentId, String term, int page, int size) {
        int offset = (page - 1) * size;
        return PaginationDto.<CourseScheduleDto>builder()
                .data(courseScheduleMapper.selectByClassNameAndTerm(studentId, term, offset, size))
                .total(courseScheduleMapper.selectCountByClassNameAndTerm(studentId, term))
                .page(page)
                .size(size)
                .build();
    }
}

package team.star.score_system.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import team.star.score_system.constant.ErrorCode;
import team.star.score_system.dto.AllYearScoreDto;
import team.star.score_system.dto.PaginationDto;
import team.star.score_system.entity.Score;
import team.star.score_system.exception.AppException;
import team.star.score_system.mapper.CourseScheduleMapper;
import team.star.score_system.mapper.ScoreMapper;

import java.util.ArrayList;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class ScoreService {
    final ScoreMapper scoreMapper;
    final CourseScheduleMapper courseScheduleMapper;

    public PaginationDto<Score> getScore(int page, int size, String term, String type, String condition) {
        int offset = (page - 1) * size;
        if ("course".equals(type)) {
            return PaginationDto.<Score>builder()
                    .data(scoreMapper.selectByCourseId(condition, term, offset, size))
                    .total(scoreMapper.selectCountByCourseId(condition, term))
                    .page(page)
                    .size(size)
                    .build();
        } else if ("student".equals(type)) {
            return PaginationDto.<Score>builder()
                    .data(scoreMapper.selectByStudentId(Long.parseLong(condition), term, offset, size))
                    .total(scoreMapper.selectCountByStudentId(Long.parseLong(condition), term))
                    .page(page)
                    .size(size)
                    .build();
        } else {
            return PaginationDto.<Score>builder()
                    .data(scoreMapper.selectAll(term, offset, size))
                    .total(scoreMapper.selectAllCount(term))
                    .page(page)
                    .size(size)
                    .build();
        }
    }

    public PaginationDto<AllYearScoreDto> getAllYearScore(int year, String type, String condition) {
        if ("major".equals(type)) {
            return PaginationDto.<AllYearScoreDto>builder()
                    .data(scoreMapper.selectAllYearScoreByMajor(year, condition))
                    .build();
        } else if ("class".equals(type)) {
            return PaginationDto.<AllYearScoreDto>builder()
                    .data(scoreMapper.selectAllYearScoreByClass(year, condition))
                    .build();
        } else {
            return PaginationDto.<AllYearScoreDto>builder()
                    .data(scoreMapper.selectAllYearScore(year))
                    .build();
        }
    }

    public PaginationDto<Score> getScoreByTeacherId(long teacherId, String className, String courseId, int page, int size) {
        int offset = (page - 1) * size;
        return PaginationDto.<Score>builder()
                .data(scoreMapper.selectByTeacherId(teacherId, className, courseId, offset, size))
                .total(scoreMapper.selectCountByTeacherId(teacherId, className, courseId))
                .page(page)
                .size(size)
                .build();
    }

    public PaginationDto<Score> getAvgScore(int page, int size, String type, String condition) {
        int offset = (page - 1) * size;
        if ("course".equals(type)) {
            return PaginationDto.<Score>builder()
                    .data(scoreMapper.selectAvgScoreByCourseId(offset, size, condition))
                    .total(scoreMapper.selectCountAvgScoreByCourseId(condition))
                    .page(page)
                    .size(size)
                    .build();
        } else if ("teacher".equals(type)) {
            return PaginationDto.<Score>builder()
                    .data(scoreMapper.selectAvgScoreByTeacherId(offset, size, Long.parseLong(condition)))
                    .total(scoreMapper.selectCountAvgScoreByTeacherId(Long.parseLong(condition)))
                    .page(page)
                    .size(size)
                    .build();
        } else {
            return PaginationDto.<Score>builder()
                    .data(scoreMapper.selectAllAvgScore(offset, size))
                    .total(scoreMapper.selectCountAllAvgScore())
                    .page(page)
                    .size(size)
                    .build();
        }
    }

    public void updateScore(Long teacherId, Long studentId, String courseId, String term, double score) {
        if (courseScheduleMapper.checkCourseIdAndTeacherId(courseId, teacherId) != 1) {
            throw new AppException(ErrorCode.PERMISSION_DENIED);
        }
        scoreMapper.updateScore(studentId, courseId, term, score);
    }

    public ArrayList<AllYearScoreDto> getAvgScoreByStudentId(long studentId) {
        return scoreMapper.selectAllYearScoreByStudent(studentId);
    }
}

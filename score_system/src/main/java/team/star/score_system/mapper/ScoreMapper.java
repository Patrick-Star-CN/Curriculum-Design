package team.star.score_system.mapper;

import org.apache.ibatis.annotations.*;
import team.star.score_system.dto.AllYearScoreDto;
import team.star.score_system.dto.CourseScheduleDto;
import team.star.score_system.entity.Score;

import java.util.ArrayList;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Mapper
public interface ScoreMapper {
    @Select("select count(*) from caix_teacher_course_pre_student where cx_term02=#{term}")
    int selectAllCount(String term);

    @Select("select count(*) from caix_teacher_course_pre_student where cx_course_id02=#{courseId} and cx_term02=#{term}")
    int selectCountByCourseId(String courseId, String term);

    @Select("select count(*) from caix_teacher_course_pre_student where cx_student_id02=#{studentId} and cx_term02=#{term}")
    int selectCountByStudentId(long studentId, String term);

    @Select("select count(*) from caix_teacher_course_pre_student where cx_teacher_id02=#{teacherId} and cx_term02=#{term}")
    int selectCountByTeacherId(long teacherId, String term);

    @Select("select * from caix_teacher_course_pre_student where cx_term02=#{term} limit #{offset}, #{limit}")
    @Results({
            @Result(column = "cx_course_id02", property = "courseId"),
            @Result(column = "cx_course_name02", property = "courseName"),
            @Result(column = "cx_class_name02", property = "className"),
            @Result(column = "cx_teacher_name02", property = "teacherName"),
            @Result(column = "cx_teacher_id02", property = "teacherId"),
            @Result(column = "cx_score02", property = "score"),
            @Result(column = "cx_term02", property = "term"),
            @Result(column = "cx_student_id02", property = "studentId"),
            @Result(column = "cx_student_name02", property = "studentName"),
            @Result(column = "cx_retake02", property = "retake")
    })
    ArrayList<Score> selectAll(String term, int offset, int limit);

    @Select("select * from caix_teacher_course_pre_student where cx_course_id02=#{courseId} and cx_term02=#{term} limit #{offset}, #{limit}")
    @Results({
            @Result(column = "cx_course_id02", property = "courseId"),
            @Result(column = "cx_course_name02", property = "courseName"),
            @Result(column = "cx_class_name02", property = "className"),
            @Result(column = "cx_teacher_name02", property = "teacherName"),
            @Result(column = "cx_teacher_id02", property = "teacherId"),
            @Result(column = "cx_score02", property = "score"),
            @Result(column = "cx_term02", property = "term"),
            @Result(column = "cx_student_id02", property = "studentId"),
            @Result(column = "cx_student_name02", property = "studentName"),
            @Result(column = "cx_retake02", property = "retake")
    })
    ArrayList<Score> selectByCourseId(String courseId, String term, int offset, int limit);

    @Select("select * from caix_teacher_course_pre_student where cx_student_id02=#{studentId} and cx_term02=#{term} limit #{offset}, #{limit}")
    @Results({
            @Result(column = "cx_course_id02", property = "courseId"),
            @Result(column = "cx_course_name02", property = "courseName"),
            @Result(column = "cx_class_name02", property = "className"),
            @Result(column = "cx_teacher_name02", property = "teacherName"),
            @Result(column = "cx_teacher_id02", property = "teacherId"),
            @Result(column = "cx_score02", property = "score"),
            @Result(column = "cx_term02", property = "term"),
            @Result(column = "cx_student_id02", property = "studentId"),
            @Result(column = "cx_student_name02", property = "studentName"),
            @Result(column = "cx_retake02", property = "retake")
    })
    ArrayList<Score> selectByStudentId(long studentId, String term, int offset, int limit);

    @Select("select * " +
            "from caix_teacher_course_pre_student " +
            "where cx_teacher_id02=#{teacherId} and cx_course_id02=#{courseId} " +
            "and cx_class_name02=#{className} " +
            "limit #{offset}, #{limit}")
    @Results({
            @Result(column = "cx_course_id02", property = "courseId"),
            @Result(column = "cx_course_name02", property = "courseName"),
            @Result(column = "cx_class_name02", property = "className"),
            @Result(column = "cx_teacher_name02", property = "teacherName"),
            @Result(column = "cx_teacher_id02", property = "teacherId"),
            @Result(column = "cx_score02", property = "score"),
            @Result(column = "cx_term02", property = "term"),
            @Result(column = "cx_student_id02", property = "studentId"),
            @Result(column = "cx_student_name02", property = "studentName"),
            @Result(column = "cx_retake02", property = "retake")
    })
    ArrayList<Score> selectByTeacherId(long teacherId, String className, String courseId, int offset, int limit);

    @Select("select count(*) " +
            "from caix_teacher_course_pre_student " +
            "where cx_teacher_id02=#{teacherId} and cx_course_id02=#{courseId}  " +
            "and cx_class_name02=#{className} ")
    int selectCountByTeacherId(long teacherId, String className, String courseId);

    @Select("select cx_student_id02, cx_student_name02, cx_year02, cx_gpa02, cx_credit02 " +
            "from caix_student_score_year " +
            "where cx_year02=#{year}")
    @Results({
            @Result(column = "cx_student_id02", property = "studentId"),
            @Result(column = "cx_student_name02", property = "studentName"),
            @Result(column = "cx_year02", property = "year"),
            @Result(column = "cx_gpa02", property = "gpa"),
            @Result(column = "cx_credit02", property = "credit")
    })
    ArrayList<AllYearScoreDto> selectAllYearScore(int year);

    @Select("select * from caix_student_score_year " +
            "where cx_class_name02=#{className}" +
            "and cx_year02=#{year}")
    @Results({
            @Result(column = "cx_student_id02", property = "studentId"),
            @Result(column = "cx_student_name02", property = "studentName"),
            @Result(column = "cx_year02", property = "year"),
            @Result(column = "cx_gpa02", property = "gpa"),
            @Result(column = "cx_credit02", property = "credit")
    })
    ArrayList<AllYearScoreDto> selectAllYearScoreByClass(int year, String className);

    @Select("select * from caix_student_score_year " +
            "where cx_student_id02=#{studentId}")
    @Results({
            @Result(column = "cx_student_id02", property = "studentId"),
            @Result(column = "cx_student_name02", property = "studentName"),
            @Result(column = "cx_year02", property = "year"),
            @Result(column = "cx_gpa02", property = "gpa"),
            @Result(column = "cx_credit02", property = "credit")
    })
    ArrayList<AllYearScoreDto> selectAllYearScoreByStudent(long studentId);

    @Select("select * from caix_student_score_year " +
            "where cx_major_name02=#{majorName}" +
            "and cx_year02=#{year}")
    @Results({
            @Result(column = "cx_student_id02", property = "studentId"),
            @Result(column = "cx_student_name02", property = "studentName"),
            @Result(column = "cx_year02", property = "year"),
            @Result(column = "cx_gpa02", property = "gpa"),
            @Result(column = "cx_credit02", property = "credit")
    })
    ArrayList<AllYearScoreDto> selectAllYearScoreByMajor(int year, String majorName);

    @Select("select * from caix_teacher_course_ave_score limit #{offset}, #{limit}")
    @Results({
            @Result(column = "cx_teacher_id02", property = "teacherId"),
            @Result(column = "cx_teacher_name02", property = "teacherName"),
            @Result(column = "cx_course_id02", property = "courseId"),
            @Result(column = "cx_course_name02", property = "courseName"),
            @Result(column = "cx_term02", property = "term"),
            @Result(column = "cx_avg_score02", property = "score"),
    })
    ArrayList<Score> selectAllAvgScore(int offset, int limit);

    @Select("select count(*) from caix_teacher_course_ave_score")
    int selectCountAllAvgScore();

    @Select("select * from caix_teacher_course_ave_score " +
            "where cx_teacher_id02=#{teacherId} " +
            "limit #{offset}, #{limit}")
    @Results({
            @Result(column = "cx_teacher_id02", property = "teacherId"),
            @Result(column = "cx_teacher_name02", property = "teacherName"),
            @Result(column = "cx_course_id02", property = "courseId"),
            @Result(column = "cx_course_name02", property = "courseName"),
            @Result(column = "cx_term02", property = "term"),
            @Result(column = "cx_avg_score02", property = "score"),
    })
    ArrayList<Score> selectAvgScoreByTeacherId(int offset, int limit, long teacherId);

    @Select("select count(*) from caix_teacher_course_ave_score " +
            "where cx_teacher_id02=#{teacherId}")
    int selectCountAvgScoreByTeacherId(long teacherId);

    @Select("select * from caix_teacher_course_ave_score " +
            "where cx_course_id02=#{courseId} " +
            "limit #{offset}, #{limit}")
    @Results({
            @Result(column = "cx_teacher_id02", property = "teacherId"),
            @Result(column = "cx_teacher_name02", property = "teacherName"),
            @Result(column = "cx_course_id02", property = "courseId"),
            @Result(column = "cx_course_name02", property = "courseName"),
            @Result(column = "cx_term02", property = "term"),
            @Result(column = "cx_avg_score02", property = "score"),
    })
    ArrayList<Score> selectAvgScoreByCourseId(int offset, int limit, String courseId);

    @Select("select count(*) from caix_teacher_course_ave_score " +
            "where cx_course_id02=#{courseId}")
    int selectCountAvgScoreByCourseId(String courseId);

    @Update("update caix_score " +
            "set cx_score02=#{score} " +
            "where cx_student_id02=#{studentId} " +
            "and cx_course_id02=#{courseId} " +
            "and cx_term02=#{term}")
    int updateScore(long studentId, String courseId, String term, double score);

}

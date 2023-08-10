package team.star.score_system.mapper;

import org.apache.ibatis.annotations.*;
import team.star.score_system.dto.CourseScheduleDto;

import java.util.ArrayList;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Mapper
public interface CourseScheduleMapper {
    @Select("select count(*) from caix_teacher_course_schedule")
    int selectAllCount();

    @Select("select count(*) from caix_teacher_course_schedule where cx_teacher_id02=#{teacherId}")
    int selectCountByTeacherId(long teacherId);

    @Select("select count(*) from caix_teacher_course_schedule where cx_term02=#{term}")
    int selectCountByTerm(String term);

    @Select("select count(*) from caix_teacher_course_schedule where cx_year02=#{year}")
    int selectCountByYear(int year);

    @Select("select count(*) from caix_teacher_course_schedule where cx_class_name02=#{className}")
    int selectCountByClassName(String className);

    @Select("select cx_course_id02, cx_course_name02, cx_class_name02, cx_teacher_name02, cx_teacher_id02, " +
            "cx_year02, cx_term02 " +
            "from caix_teacher_course_schedule " +
            "limit #{offset}, #{limit}")
    @Results({
            @Result(column = "cx_course_id02", property = "courseId"),
            @Result(column = "cx_course_name02", property = "courseName"),
            @Result(column = "cx_class_name02", property = "className"),
            @Result(column = "cx_teacher_name02", property = "teacherName"),
            @Result(column = "cx_teacher_id02", property = "teacherId"),
            @Result(column = "cx_year02", property = "year"),
            @Result(column = "cx_term02", property = "term")
    })
    ArrayList<CourseScheduleDto> selectAll(int offset, int limit);

    @Select("select cx_course_id02, cx_course_name02, cx_class_name02, cx_teacher_name02, cx_teacher_id02, " +
            "cx_year02, cx_term02 " +
            "from caix_teacher_course_schedule " +
            "where cx_teacher_id02=#{teacherId} " +
            "limit #{offset}, #{limit}")
    @Results({
            @Result(column = "cx_course_id02", property = "courseId"),
            @Result(column = "cx_course_name02", property = "courseName"),
            @Result(column = "cx_class_name02", property = "className"),
            @Result(column = "cx_teacher_name02", property = "teacherName"),
            @Result(column = "cx_teacher_id02", property = "teacherId"),
            @Result(column = "cx_year02", property = "year"),
            @Result(column = "cx_term02", property = "term")
    })
    ArrayList<CourseScheduleDto> selectByTeacherId(long teacherId, int offset, int limit);

    @Select("select cx_course_id02, cx_course_name02, cx_class_name02, cx_teacher_name02, cx_teacher_id02, " +
            "cx_year02, cx_term02 " +
            "from caix_teacher_course_schedule " +
            "where cx_term02=#{term} " +
            "limit #{offset}, #{limit}")
    @Results({
            @Result(column = "cx_course_id02", property = "courseId"),
            @Result(column = "cx_course_name02", property = "courseName"),
            @Result(column = "cx_class_name02", property = "className"),
            @Result(column = "cx_teacher_name02", property = "teacherName"),
            @Result(column = "cx_teacher_id02", property = "teacherId"),
            @Result(column = "cx_year02", property = "year"),
            @Result(column = "cx_term02", property = "term")
    })
    ArrayList<CourseScheduleDto> selectByTerm(String term, int offset, int limit);

    @Select("select cx_course_id02, cx_course_name02, cx_class_name02, cx_teacher_name02, cx_teacher_id02, " +
            "cx_year02, cx_term02 " +
            "from caix_teacher_course_schedule " +
            "where cx_year02=#{year} " +
            "limit #{offset}, #{limit}")
    @Results({
            @Result(column = "cx_course_id02", property = "courseId"),
            @Result(column = "cx_course_name02", property = "courseName"),
            @Result(column = "cx_class_name02", property = "className"),
            @Result(column = "cx_teacher_name02", property = "teacherName"),
            @Result(column = "cx_teacher_id02", property = "teacherId"),
            @Result(column = "cx_year02", property = "year"),
            @Result(column = "cx_term02", property = "term")
    })
    ArrayList<CourseScheduleDto> selectByYear(int year, int offset, int limit);

    @Select("select cx_course_id02, cx_course_name02, cx_class_name02, cx_teacher_name02, cx_teacher_id02, " +
            "cx_year02, cx_term02 " +
            "from caix_teacher_course_schedule " +
            "where cx_class_name02=#{className} " +
            "limit #{offset}, #{limit}")
    @Results({
            @Result(column = "cx_course_id02", property = "courseId"),
            @Result(column = "cx_course_name02", property = "courseName"),
            @Result(column = "cx_class_name02", property = "className"),
            @Result(column = "cx_teacher_name02", property = "teacherName"),
            @Result(column = "cx_teacher_id02", property = "teacherId"),
            @Result(column = "cx_year02", property = "year"),
            @Result(column = "cx_term02", property = "term")
    })
    ArrayList<CourseScheduleDto> selectByClassName(String className, int offset, int limit);

    @Select("select cx_course_id02, cx_course_name02, cx_class_name02, cx_teacher_name02, cx_teacher_id02, " +
            "cx_year02, cx_term02 " +
            "from caix_teacher_course_schedule " +
            "where cx_class_name02=" +
            "(select caix_class.cx_name02 from caix_class, caix_student " +
            "where caix_class.cx_id02=caix_student.cx_class_id02 and caix_student.cx_id02=#{studentId}) " +
            "and cx_term02=#{term} " +
            "limit #{offset}, #{limit}")
    @Results({
            @Result(column = "cx_course_id02", property = "courseId"),
            @Result(column = "cx_course_name02", property = "courseName"),
            @Result(column = "cx_class_name02", property = "className"),
            @Result(column = "cx_teacher_name02", property = "teacherName"),
            @Result(column = "cx_teacher_id02", property = "teacherId"),
            @Result(column = "cx_year02", property = "year"),
            @Result(column = "cx_term02", property = "term")
    })
    ArrayList<CourseScheduleDto> selectByClassNameAndTerm(Long studentId, String term, int offset, int limit);

    @Select("select count(*) " +
            "from caix_teacher_course_schedule " +
            "where cx_class_name02= " +
            "(select caix_class.cx_name02 from caix_class, caix_student " +
            "where caix_class.cx_id02=caix_student.cx_class_id02 and caix_student.cx_id02=#{studentId}) " +
            "and cx_term02=#{term} ")
    int selectCountByClassNameAndTerm(Long studentId, String term);

    @Select("select cx_course_id02, cx_course_name02, cx_class_name02, cx_teacher_name02, cx_teacher_id02, " +
            "cx_year02, cx_term02 " +
            "from caix_teacher_course_schedule " +
            "where cx_term02=#{term} and cx_teacher_id02=#{teacherId} " +
            "limit #{offset}, #{limit}")
    @Results({
            @Result(column = "cx_course_id02", property = "courseId"),
            @Result(column = "cx_course_name02", property = "courseName"),
            @Result(column = "cx_class_name02", property = "className"),
            @Result(column = "cx_teacher_name02", property = "teacherName"),
            @Result(column = "cx_teacher_id02", property = "teacherId"),
            @Result(column = "cx_year02", property = "year"),
            @Result(column = "cx_term02", property = "term")
    })
    ArrayList<CourseScheduleDto> selectByTermAndTeacherId(String term, long teacherId, int offset, int limit);

    @Select("select count(*) " +
            "from caix_teacher_course_schedule " +
            "where cx_term02=#{term} and cx_teacher_id02=#{teacherId}")
    int selectcountByTermAndTeacherId(String term, long teacherId);

    @Insert("call increase_course_schedule(#{courseId},#{className}, #{teacherId})")
    void insertCourseSchedule(String courseId, String className, long teacherId);

    @Update("update caix_course_schedule " +
            "set cx_teacher_id02=#{teacherId} " +
            "where cx_course_id02=#{courseId} and cx_class_id02 = (select cx_id02 from caix_class where cx_name02=#{className})")
    void updateCourseSchedule(String courseId, String className, long teacherId);

    @Delete("delete from caix_course_schedule " +
            "where cx_course_id02=#{courseId} and cx_class_id02 = (select cx_id02 from caix_class where cx_name02=#{className})")
    void deleteCourseSchedule(String courseId, String className);

    @Select("select count(*) from caix_course_schedule " +
            "where cx_course_id02=#{courseId} and cx_teacher_id02=#{teacherId}")
    int checkCourseIdAndTeacherId(String courseId, long teacherId);
}

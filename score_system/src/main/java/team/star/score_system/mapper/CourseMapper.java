package team.star.score_system.mapper;

import org.apache.ibatis.annotations.*;
import team.star.score_system.dto.StudentCourseDto;
import team.star.score_system.entity.Course;

import java.util.ArrayList;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Mapper
public interface CourseMapper {
    @Select("select count(*) from caix_course")
    int selectCount();

    @Select("select count(*) from caix_course where cx_id02 = #{id}")
    int checkId(long id);

    @Select("select count(*) from caix_course " +
            "where cx_college_id02 = (select cx_id02 from caix_college where cx_name02 = #{college}) ")
    int selectCountByCollege(String college);

    @Select("select count(*) from caix_course " +
            "where cx_term02 = #{term}")
    int selectCountByTerm(String term);

    @Select("select count(*) from caix_course " +
            "where cx_examination_method02 = #{examinationMethod}")
    int selectCountByExaminationMethod(String examinationMethod);

    @Select("select count(*) from caix_course " +
            "where cx_attribute02 = #{attribute}")
    int selectCountByAttribute(String attribute);

    @Select("select distinct cx_term02 from caix_course")
    ArrayList<String> selectAllTerm();

    @Select("select distinct cx_examination_method02 from caix_course")
    ArrayList<String> selectAllExaminationMethod();

    @Select("select distinct cx_attribute02 from caix_course")
    ArrayList<String> selectAllAttribute();

    @Select("select caix_course.cx_id02, caix_course.cx_name02, cx_term02, cx_examination_method02, cx_credit02, " +
            "cx_hours02, cx_attribute02, cx_year02, caix_college.cx_name02 as cx_college_name02 " +
            "from caix_course, caix_college " +
            "where caix_course.cx_college_id02 = caix_college.cx_id02 " +
            "limit #{offset}, #{size}")
    @Results({
            @Result(column = "cx_id02", property = "id"),
            @Result(column = "cx_name02", property = "name"),
            @Result(column = "cx_term02", property = "term"),
            @Result(column = "cx_examination_method02", property = "examinationMethod"),
            @Result(column = "cx_credit02", property = "credit"),
            @Result(column = "cx_hours02", property = "hours"),
            @Result(column = "cx_attribute02", property = "attribute"),
            @Result(column = "cx_year02", property = "year"),
            @Result(column = "cx_college_name02", property = "collegeName")
    })
    ArrayList<Course> selectAll(@Param("offset") int offset, @Param("size") int size);

    @Select("select caix_course.cx_id02, caix_course.cx_name02, cx_term02, cx_examination_method02, cx_credit02, " +
            "cx_hours02, cx_attribute02, cx_year02, caix_college.cx_name02 as cx_college_name02 " +
            "from caix_course, caix_college " +
            "where caix_course.cx_college_id02 = caix_college.cx_id02 " +
            "and caix_college.cx_name02 = #{college} " +
            "limit #{offset}, #{size}")
    @Results({
            @Result(column = "cx_id02", property = "id"),
            @Result(column = "cx_name02", property = "name"),
            @Result(column = "cx_term02", property = "term"),
            @Result(column = "cx_examination_method02", property = "examinationMethod"),
            @Result(column = "cx_credit02", property = "credit"),
            @Result(column = "cx_hours02", property = "hours"),
            @Result(column = "cx_attribute02", property = "attribute"),
            @Result(column = "cx_year02", property = "year"),
            @Result(column = "cx_college_name02", property = "collegeName")
    })
    ArrayList<Course> selectByCollege(@Param("college") String college, @Param("offset") int offset, @Param("size") int size);

    @Select("select caix_course.cx_id02, caix_course.cx_name02, cx_term02, cx_examination_method02, cx_credit02, " +
            "cx_hours02, cx_attribute02, cx_year02, caix_college.cx_name02 as cx_college_name02 " +
            "from caix_course, caix_college " +
            "where caix_course.cx_college_id02 = caix_college.cx_id02 " +
            "and cx_term02 = #{term} " +
            "limit #{offset}, #{size}")
    @Results({
            @Result(column = "cx_id02", property = "id"),
            @Result(column = "cx_name02", property = "name"),
            @Result(column = "cx_term02", property = "term"),
            @Result(column = "cx_examination_method02", property = "examinationMethod"),
            @Result(column = "cx_credit02", property = "credit"),
            @Result(column = "cx_hours02", property = "hours"),
            @Result(column = "cx_attribute02", property = "attribute"),
            @Result(column = "cx_year02", property = "year"),
            @Result(column = "cx_college_name02", property = "collegeName")
    })
    ArrayList<Course> selectByTerm(@Param("term") String term, @Param("offset") int offset, @Param("size") int size);

    @Select("select caix_course.cx_id02, caix_course.cx_name02, cx_term02, cx_examination_method02, cx_credit02, " +
            "cx_hours02, cx_attribute02, cx_year02, caix_college.cx_name02 as cx_college_name02 " +
            "from caix_course, caix_college " +
            "where caix_course.cx_college_id02 = caix_college.cx_id02 " +
            "and cx_examination_method02 = #{examinationMethod} " +
            "limit #{offset}, #{size}")
    @Results({
            @Result(column = "cx_id02", property = "id"),
            @Result(column = "cx_name02", property = "name"),
            @Result(column = "cx_term02", property = "term"),
            @Result(column = "cx_examination_method02", property = "examinationMethod"),
            @Result(column = "cx_credit02", property = "credit"),
            @Result(column = "cx_hours02", property = "hours"),
            @Result(column = "cx_attribute02", property = "attribute"),
            @Result(column = "cx_year02", property = "year"),
            @Result(column = "cx_college_name02", property = "collegeName")
    })
    ArrayList<Course> selectByExaminationMethod(@Param("examinationMethod") String examinationMethod, @Param("offset") int offset, @Param("size") int size);

    @Select("select caix_course.cx_id02, caix_course.cx_name02, cx_term02, cx_examination_method02, cx_credit02, " +
            "cx_hours02, cx_attribute02, cx_year02, caix_college.cx_name02 as cx_college_name02 " +
            "from caix_course, caix_college " +
            "where caix_course.cx_college_id02 = caix_college.cx_id02 " +
            "and cx_attribute02 = #{attribute} " +
            "limit #{offset}, #{size}")
    @Results({
            @Result(column = "cx_id02", property = "id"),
            @Result(column = "cx_name02", property = "name"),
            @Result(column = "cx_term02", property = "term"),
            @Result(column = "cx_examination_method02", property = "examinationMethod"),
            @Result(column = "cx_credit02", property = "credit"),
            @Result(column = "cx_hours02", property = "hours"),
            @Result(column = "cx_attribute02", property = "attribute"),
            @Result(column = "cx_year02", property = "year"),
            @Result(column = "cx_college_name02", property = "collegeName")
    })
    ArrayList<Course> selectByAttribute(@Param("attribute") String attribute, @Param("offset") int offset, @Param("size") int size);

    @Update("update caix_course set " +
            "cx_name02 = #{name}, " +
            "cx_term02 = #{term}, " +
            "cx_college_id02 = (select cx_id02 from caix_college where cx_name02 = #{collegeName}), " +
            "cx_examination_method02 = #{examinationMethod}, " +
            "cx_credit02 = #{credit}, " +
            "cx_hours02 = #{hours}, " +
            "cx_attribute02 = #{attribute}, " +
            "cx_year02 = #{year} " +
            "where cx_id02 = #{id}")
    void update(@Param("id") int id, @Param("name") String name, @Param("term") String term, @Param("collegeName") String collegeName,
                @Param("examinationMethod") String examinationMethod, @Param("credit") double credit, @Param("hours") int hours,
                @Param("attribute") String attribute, @Param("year") int year);

    @Insert("call increase_course(#{id}, #{name}, #{collegeName}, #{term}, #{examinationMethod}, #{credit}, #{attribute}, #{year})")
    void insert(@Param("id") int id, @Param("name") String name, @Param("collegeName") String collegeName, @Param("term") String term,
                @Param("examinationMethod") String examinationMethod, @Param("credit") double credit, @Param("attribute") String attribute,
                @Param("year") int year);

    @Delete("delete from caix_course where cx_id02 = #{id}")
    void delete(@Param("id") int id);


    @Select("select * from caix_student_course " +
            "where cx_student_id02 = #{studentId} " +
            "and cx_term02 = #{term} " +
            "limit #{offset}, #{size}")
    @Results({
            @Result(column = "cx_student_id02", property = "studentId"),
            @Result(column = "cx_course_id02", property = "courseId"),
            @Result(column = "cx_course_name02", property = "courseName"),
            @Result(column = "cx_term02", property = "term"),
            @Result(column = "cx_teacher_id02", property = "teacherId"),
            @Result(column = "cx_teacher_name02", property = "teacherName"),
            @Result(column = "cx_credit02", property = "credit")
    })
    ArrayList<StudentCourseDto> selectStudentCourseByTerm(@Param("studentId") long studentId, @Param("term") String term, @Param("offset") int offset, @Param("size") int size);

    @Select("select count(*) from caix_student_course " +
            "where cx_student_id02 = #{studentId} " +
            "and cx_term02 = #{term} ")
    int selectStudentCourseByTermCount(@Param("studentId") long studentId, @Param("term") String term);
}

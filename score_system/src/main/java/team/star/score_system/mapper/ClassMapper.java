package team.star.score_system.mapper;

import org.apache.ibatis.annotations.*;
import team.star.score_system.entity.Class;

import java.util.ArrayList;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Mapper
public interface ClassMapper {
    @Select("select count(*) from caix_class")
    int selectCount();

    @Select("select count(*) from caix_class " +
            "where cx_major_id02 = " +
            "(select cx_id02 from caix_major where cx_name02 = #{major_name})")
    int selectCountByMajor(@Param("major_name") String majorName);

    @Select("select count(*) from caix_class " +
            "where cx_major_id02 in " +
            "(select caix_major.cx_id02 " +
            "from caix_major, caix_college " +
            "where cx_college_id02 = caix_college.cx_id02 " +
            "and caix_college.cx_name02 = #{college_name})")
    int selectCountByCollege(@Param("college_name") String collegeName);

    @Select("select " +
            "caix_class.cx_id02, " +
            "caix_class.cx_name02, " +
            "cx_count_of_students02, " +
            "caix_major.cx_name02 as cx_major_name02, " +
            "caix_teacher.cx_name02 as cx_teacher_name02 " +
            "from " +
            "caix_class, caix_major, caix_teacher " +
            "where caix_class.cx_major_id02 = caix_major.cx_id02 " +
            "and caix_class.cx_head_teacher_id02 = caix_teacher.cx_id02 " +
            "limit #{offset}, #{size}")
    @Results({
            @Result(column = "cx_id02", property = "id"),
            @Result(column = "cx_name02", property = "name"),
            @Result(column = "cx_major_name02", property = "majorName"),
            @Result(column = "cx_teacher_name02", property = "teacherName")
    })
    ArrayList<Class> selectAll(@Param("offset") int offset, @Param("size") int size);

    @Select("select " +
            "caix_class.cx_id02, " +
            "caix_class.cx_name02, " +
            "cx_count_of_students02, " +
            "caix_major.cx_name02 as cx_major_name02, " +
            "caix_teacher.cx_name02 as cx_teacher_name02 " +
            "from " +
            "caix_class, caix_major, caix_teacher " +
            "where caix_class.cx_major_id02 = caix_major.cx_id02 " +
            "and caix_class.cx_head_teacher_id02 = caix_teacher.cx_id02 " +
            "and caix_major.cx_college_id02 = " +
            "(select cx_id02 from caix_college " +
            "where cx_name02 = #{college_name}) " +
            "limit #{offset}, #{size}")
    @Results({
            @Result(column = "cx_id02", property = "id"),
            @Result(column = "cx_name02", property = "name"),
            @Result(column = "cx_major_name02", property = "majorName"),
            @Result(column = "cx_teacher_name02", property = "teacherName")
    })
    ArrayList<Class> selectByCollege(@Param("offset") int offset, @Param("size") int size, @Param("college_name") String college);

    @Select("select " +
            "caix_class.cx_id02, " +
            "caix_class.cx_name02, " +
            "cx_count_of_students02, " +
            "caix_major.cx_name02 as cx_major_name02, " +
            "caix_teacher.cx_name02 as cx_teacher_name02 " +
            "from " +
            "caix_class, caix_major, caix_teacher " +
            "where caix_class.cx_major_id02 = caix_major.cx_id02 " +
            "and caix_class.cx_head_teacher_id02 = caix_teacher.cx_id02 " +
            "and caix_major.cx_name02 = #{major_name} " +
            "limit #{offset}, #{size}")
    @Results({
            @Result(column = "cx_id02", property = "id"),
            @Result(column = "cx_name02", property = "name"),
            @Result(column = "cx_major_name02", property = "majorName"),
            @Result(column = "cx_teacher_name02", property = "teacherName")
    })
    ArrayList<Class> selectByMajor(@Param("offset") int offset, @Param("size") int size, @Param("major_name") String major);

    @Update("update " +
            "caix_class " +
            "set " +
            "cx_name02 = #{name}, " +
            "cx_major_id02 = (select cx_id02 from caix_major where cx_name02 = #{major_name}), " +
            "cx_head_teacher_id02 = #{teacher_id} " +
            "where " +
            "cx_id02 = #{id}")
    int update(@Param("id") int id, @Param("name") String name, @Param("major_name") String majorName, @Param("teacher_id") long teacherId);

    @Insert("call increase_class(#{id}, #{name}, #{major_name}, #{teacher_id})")
    int insert(@Param("id") int id, @Param("name") String name, @Param("major_name") String majorName, @Param("teacher_id") long teacherId);

    @Delete("delete from caix_class where cx_id02 = #{id}")
    int delete(@Param("id") int id);

    @Select("select count(*) from caix_class where cx_id02 = #{id}")
    int checkId(@Param("id") int id);

    @Select("select count(*) from caix_class where cx_name02 = #{name}")
    int checkName(@Param("name") String name);
}

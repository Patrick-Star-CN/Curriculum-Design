package team.star.score_system.mapper;

import org.apache.ibatis.annotations.*;
import team.star.score_system.entity.Student;

import java.util.ArrayList;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Mapper
public interface StudentMapper {
    @Select("call count_students_by_hometown(#{p_hometown, jdbcType=VARCHAR, mode=IN}, 1)")
    int getCountByHometown(@Param("p_hometown") String hometown);

    @Insert("call increase_student(#{id}, #{name}, #{gender}, #{birth_year}, #{class_name}, #{hometown})")
    void insert(@Param("id") long id, @Param("name") String name,
                @Param("gender") String gender, @Param("birth_year") int birthYear,
                @Param("class_name") String className, @Param("hometown") String hometown);

    @Delete("delete from caix_student where cx_id02 = #{id}")
    void delete(@Param("id") long id);

    @Update("update caix_student " +
            "set cx_name02 = #{name}, cx_gender02 = #{gender}, cx_birth_year02 = #{birth_year}, " +
            "cx_class_id02 = (select cx_id02 from caix_class where cx_name02 = #{class_name}), " +
            "cx_hometown02 = #{hometown} " +
            "where cx_id02 = #{id}")
    void update(@Param("id") long id, @Param("name") String name,
                @Param("gender") String gender, @Param("birth_year") int birthYear,
                @Param("class_name") String className, @Param("hometown") String hometown);

    @Select("select count(*) from caix_student where cx_id02 = #{id}")
    int checkId(@Param("id") long id);

    @Select("select " +
            "caix_student.cx_id02, caix_student.cx_name02, cx_gender02, " +
            "cx_birth_year02, cx_hometown02, caix_class.cx_name02 as cx_class_name02, " +
            "cx_credits02 " +
            "from caix_student, caix_class " +
            "where cx_class_id02 = caix_class.cx_id02 " +
            "limit #{offset}, #{limit}")
    @Results({
            @Result(column = "cx_id02", property = "id"),
            @Result(column = "cx_name02", property = "name"),
            @Result(column = "cx_gender02", property = "gender"),
            @Result(column = "cx_credits02", property = "credits"),
            @Result(column = "cx_birth_year02", property = "birthYear"),
            @Result(column = "cx_hometown02", property = "hometown"),
            @Result(column = "cx_class_name02", property = "className")
    })
    ArrayList<Student> selectAll(@Param("offset") int offset, @Param("limit") int limit);

    @Select("select " +
            "caix_student.cx_id02, caix_student.cx_name02, cx_gender02, " +
            "cx_birth_year02, cx_hometown02, caix_class.cx_name02 as cx_class_name02, " +
            "cx_credits02 " +
            "from caix_student, caix_class " +
            "where cx_class_id02 = caix_class.cx_id02 " +
            "and caix_class.cx_name02 = #{class_name}")
    @Results({
            @Result(column = "cx_id02", property = "id"),
            @Result(column = "cx_name02", property = "name"),
            @Result(column = "cx_gender02", property = "gender"),
            @Result(column = "cx_credits02", property = "credits"),
            @Result(column = "cx_birth_year02", property = "birthYear"),
            @Result(column = "cx_hometown02", property = "hometown"),
            @Result(column = "cx_class_name02", property = "className")
    })
    ArrayList<Student> selectByClass(@Param("offset") int offset, @Param("limit") int limit, @Param("class_name") String className);

    @Select("select " +
            "caix_student.cx_id02, caix_student.cx_name02, cx_gender02, " +
            "cx_birth_year02, cx_hometown02, caix_class.cx_name02 as cx_class_name02, " +
            "cx_credits02 " +
            "from caix_student, caix_class " +
            "where cx_class_id02 = caix_class.cx_id02 " +
            "and caix_class.cx_major_id02 = " +
            "(select cx_id02 from caix_major where cx_name02 = #{major_name})")
    @Results({
            @Result(column = "cx_id02", property = "id"),
            @Result(column = "cx_name02", property = "name"),
            @Result(column = "cx_gender02", property = "gender"),
            @Result(column = "cx_credits02", property = "credits"),
            @Result(column = "cx_birth_year02", property = "birthYear"),
            @Result(column = "cx_hometown02", property = "hometown"),
            @Result(column = "cx_class_name02", property = "className")
    })
    ArrayList<Student> selectByMajor(@Param("offset") int offset, @Param("limit") int limit, @Param("major_name") String majorName);

    @Select("select " +
            "caix_student.cx_id02, caix_student.cx_name02, cx_gender02, " +
            "cx_birth_year02, cx_hometown02, caix_class.cx_name02 as cx_class_name02, " +
            "cx_credits02 " +
            "from caix_student, caix_class " +
            "where cx_class_id02 = caix_class.cx_id02 " +
            "and caix_class.cx_major_id02 in " +
            "(select cx_id02 from caix_major " +
            "where cx_college_id02 = " +
            "(select cx_id02 from caix_college where cx_name02 = #{college_name}))")
    @Results({
            @Result(column = "cx_id02", property = "id"),
            @Result(column = "cx_name02", property = "name"),
            @Result(column = "cx_gender02", property = "gender"),
            @Result(column = "cx_credits02", property = "credits"),
            @Result(column = "cx_birth_year02", property = "birthYear"),
            @Result(column = "cx_hometown02", property = "hometown"),
            @Result(column = "cx_class_name02", property = "className")
    })
    ArrayList<Student> selectByCollege(@Param("offset") int offset, @Param("limit") int limit, @Param("college_name") String collegeName);

    @Select("select count(*) from caix_student")
    int selectCount();

    @Select("select count(*) from caix_student " +
            "where cx_class_id02 = (select cx_id02 from caix_class where cx_name02 = #{class_name})")
    int selectCountByClass(@Param("class_name") String className);

    @Select("select count(*) from caix_student " +
            "where cx_class_id02 in " +
            "(select cx_id02 from caix_class " +
            "where cx_major_id02 = " +
            "(select cx_id02 from caix_major where cx_name02 = #{major_name}))")
    int selectCountByMajor(@Param("major_name") String majorName);

    @Select("select count(*) from caix_student " +
            "where cx_class_id02 in " +
            "(select cx_id02 from caix_class " +
            "where cx_major_id02 in " +
            "(select cx_id02 from caix_major " +
            "where cx_college_id02 = " +
            "(select cx_id02 from caix_college where cx_name02 = #{college_name})))")
    int selectCountByCollege(@Param("college_name") String collegeName);

    @Select("select count(*) from caix_student where cx_id02 = #{id} and cx_password02 = #{password}")
    int login(@Param("id") long id, @Param("password") String password);

    @Select("select " +
            "caix_student.cx_id02, caix_student.cx_name02, cx_gender02, " +
            "cx_birth_year02, cx_hometown02, caix_class.cx_name02 as cx_class_name02, " +
            "cx_credits02 " +
            "from caix_student, caix_class " +
            "where cx_class_id02 = caix_class.cx_id02 " +
            "and caix_student.cx_id02 = #{id} ")
    @Results({
            @Result(column = "cx_id02", property = "id"),
            @Result(column = "cx_name02", property = "name"),
            @Result(column = "cx_gender02", property = "gender"),
            @Result(column = "cx_credits02", property = "credits"),
            @Result(column = "cx_birth_year02", property = "birthYear"),
            @Result(column = "cx_hometown02", property = "hometown"),
            @Result(column = "cx_class_name02", property = "className")
    })
    Student selectOne(@Param("id") long id);

    @Update("call student_change_password(#{password}, #{id})")
    int changePassword(@Param("password") String password, @Param("id") long id);

    @Select("select distinct cx_hometown02 from caix_student")
    ArrayList<String> selectHometownList();

    @Select("select " +
            "caix_student.cx_id02, caix_student.cx_name02, cx_gender02, " +
            "cx_birth_year02, cx_hometown02, caix_class.cx_name02 as cx_class_name02, " +
            "cx_credits02 " +
            "from caix_student, caix_class " +
            "where cx_class_id02 = caix_class.cx_id02 " +
            "and cx_hometown02 = #{hometown} ")
    @Results({
            @Result(column = "cx_id02", property = "id"),
            @Result(column = "cx_name02", property = "name"),
            @Result(column = "cx_gender02", property = "gender"),
            @Result(column = "cx_credits02", property = "credits"),
            @Result(column = "cx_birth_year02", property = "birthYear"),
            @Result(column = "cx_hometown02", property = "hometown"),
            @Result(column = "cx_class_name02", property = "className")
    })
    ArrayList<Student> selectByHometown(@Param("offset") int offset, @Param("limit") int limit, @Param("hometown") String hometown);
}

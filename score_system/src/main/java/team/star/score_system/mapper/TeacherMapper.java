package team.star.score_system.mapper;

import org.apache.ibatis.annotations.*;
import team.star.score_system.entity.Teacher;

import java.util.ArrayList;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Mapper
public interface TeacherMapper {
    @Select("select count(*) from caix_teacher")
    int selectCount();

    @Insert("call increase_teacher(#{id}, #{name}, #{gender}, #{birth_year}, #{college_name}, #{phone}, #{title})")
    int insert(@Param("id") long id, @Param("name") String name, @Param("gender") String gender,
               @Param("birth_year") int birthYear, @Param("college_name") String collegeName,
               @Param("phone") String phone, @Param("title") String title);

    @Select("select count(*) from caix_teacher where cx_id02=#{id}")
    int checkId(@Param("id") long id);

    @Update("update caix_teacher " +
            "set cx_name02=#{name}, cx_gender02=#{gender}, cx_birth_year02=#{birth_year}, " +
            "cx_college_id02=(select cx_id02 from caix_college where cx_name02=#{college_name}), " +
            "cx_phone02=#{phone}, cx_title02=#{title} " +
            "where cx_id02=#{id}")
    int update(@Param("id") long id, @Param("name") String name, @Param("gender") String gender,
               @Param("birth_year") int birthYear, @Param("college_name") String collegeName,
               @Param("phone") String phone, @Param("title") String title);

    @Delete("delete from caix_teacher where cx_id02=#{id}")
    int delete(@Param("id") long id);

    @Select("select caix_teacher.cx_id02, caix_teacher.cx_name02, cx_gender02, cx_birth_year02, cx_phone02, cx_title02, " +
            "caix_college.cx_name02 as cx_college_name02 " +
            "from caix_teacher, caix_college " +
            "where caix_teacher.cx_college_id02 = caix_college.cx_id02 " +
            "limit #{offset}, #{limit}")
    @Results({
            @Result(column = "cx_id02", property = "id"),
            @Result(column = "cx_name02", property = "name"),
            @Result(column = "cx_gender02", property = "gender"),
            @Result(column = "cx_birth_year02", property = "birthYear"),
            @Result(column = "cx_phone02", property = "phone"),
            @Result(column = "cx_title02", property = "jobTitle"),
            @Result(column = "cx_college_name02", property = "college")
    })
    ArrayList<Teacher> selectAll(@Param("offset") int offset, @Param("limit") int limit);

    @Select("select caix_teacher.cx_id02, caix_teacher.cx_name02, cx_gender02, cx_birth_year02, cx_phone02, cx_title02, " +
            "caix_college.cx_name02 as cx_college_name02 " +
            "from caix_teacher, caix_college " +
            "where caix_teacher.cx_college_id02 = caix_college.cx_id02 " +
            "and caix_college.cx_name02=#{college_name} " +
            "limit #{offset}, #{limit} ")
    @Results({
            @Result(column = "cx_id02", property = "id"),
            @Result(column = "cx_name02", property = "name"),
            @Result(column = "cx_gender02", property = "gender"),
            @Result(column = "cx_birth_year02", property = "birthYear"),
            @Result(column = "cx_college_name02", property = "college"),
            @Result(column = "cx_phone02", property = "phone"),
            @Result(column = "cx_title02", property = "jobTitle")
    })
    ArrayList<Teacher> selectByCollegeName(@Param("offset") int offset, @Param("limit") int limit, @Param("college_name") String collegeName);

    @Update("call teacher_change_password(#{password}, #{id})")
    int changePassword(@Param("password") String password, @Param("id") long id);

    @Select("select count(*) from caix_teacher where cx_id02=#{id} and cx_password02=#{password}")
    int login(@Param("id") long id, @Param("password") String password);

    @Select("select caix_teacher.cx_id02, caix_teacher.cx_name02, cx_gender02, cx_birth_year02, cx_phone02, cx_title02, " +
            "caix_college.cx_name02 as cx_college_name02 " +
            "from caix_teacher, caix_college " +
            "where caix_teacher.cx_college_id02 = caix_college.cx_id02 " +
            "and caix_teacher.cx_id02=#{id} ")
    @Results({
            @Result(column = "cx_id02", property = "id"),
            @Result(column = "cx_name02", property = "name"),
            @Result(column = "cx_gender02", property = "gender"),
            @Result(column = "cx_birth_year02", property = "birthYear"),
            @Result(column = "cx_college_name02", property = "college"),
            @Result(column = "cx_phone02", property = "phone"),
            @Result(column = "cx_title02", property = "jobTitle")
    })
    Teacher selectOne(@Param("id") long id);
}

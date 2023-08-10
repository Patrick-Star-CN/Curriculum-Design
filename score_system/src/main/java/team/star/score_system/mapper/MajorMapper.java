package team.star.score_system.mapper;

import org.apache.ibatis.annotations.*;
import team.star.score_system.entity.Major;

import java.util.ArrayList;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Mapper
public interface MajorMapper {
    @Select("select count(*) from caix_major")
    int selectCount();

    @Select("select " +
            "caix_major.cx_id02 as major_id, " +
            "caix_major.cx_name02 as major_name, " +
            "caix_college.cx_name02 as college_name " +
            "from caix_major, caix_college " +
            "where caix_college.cx_id02 = caix_major.cx_college_id02 " +
            "limit #{offset}, #{size}")
    @Results({
            @Result(column = "major_id", property = "id"),
            @Result(column = "major_name", property = "name"),
            @Result(column = "college_name", property = "collegeName")
    })
    ArrayList<Major> selectAll(@Param("offset") int offset, @Param("size") int size);

    @Select("select " +
            "caix_major.cx_id02 as major_id, " +
            "caix_major.cx_name02 as major_name, " +
            "caix_college.cx_name02 as college_name " +
            "from caix_major, caix_college " +
            "where caix_college.cx_id02 = caix_major.cx_college_id02 " +
            "and caix_college.cx_name02 = #{college_name} " +
            "limit #{offset}, #{size}")
    @Results({
            @Result(column = "major_id", property = "id"),
            @Result(column = "major_name", property = "name"),
            @Result(column = "college_name", property = "collegeName")
    })
    ArrayList<Major> selectAllByCollegeName(@Param("offset") int offset, @Param("size") int size, @Param("college_name") String collegeName);

    @Insert("call increase_major(#{id}, #{name}, #{college_name})")
    int insert(@Param("id") int id, @Param("name") String name, @Param("college_name") String collegeName);

    @Update("update " +
            "caix_major " +
            "set " +
            "cx_name02 = #{name}, " +
            "cx_college_id02 = (select cx_id02 from caix_college where cx_name02 = #{college_name}) " +
            "where " +
            "cx_id02 = #{id}")
    int update(@Param("id") int id, @Param("name") String name, @Param("college_name") String collegeName);

    @Delete("delete from caix_major where cx_id02 = #{id}")
    int delete(@Param("id") int id);

    @Select("select count(*) from caix_major where cx_id02 = #{id}")
    int checkId(@Param("id") int id);

    @Select("select count(*) from caix_major where cx_name02 = #{name}")
    int checkName(@Param("name") String name);
}

package team.star.score_system.mapper;

import org.apache.ibatis.annotations.*;
import team.star.score_system.entity.College;

import java.util.ArrayList;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Mapper
public interface CollegeMapper {
    @Select("select count(*) from caix_college")
    int selectCount();

    @Select("select * from caix_college limit #{offset}, #{size}")
    @Results({
            @Result(column = "cx_id02", property = "id"),
            @Result(column = "cx_name02", property = "name")
    })
    ArrayList<College> selectAll(@Param("offset") int offset, @Param("size") int size);

    @Select("select count(*) from caix_college where cx_id02 = #{id}")
    int checkId(@Param("id") int id);

    @Select("select count(*) from caix_college where cx_name02 = #{name}")
    int checkName(@Param("name") String name);

    @Insert("call increase_college(#{id}, #{name})")
    int insert(@Param("id") int id, @Param("name") String name);

    @Update("update caix_college set cx_name02 = #{name} where cx_id02 = #{id}")
    int update(@Param("id") int id, @Param("name") String name);

    @Delete("delete from caix_college where cx_id02 = #{id}")
    int delete(@Param("id") int id);
}

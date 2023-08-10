package team.star.score_system.mapper;

import org.apache.ibatis.annotations.*;
import team.star.score_system.entity.Admin;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Mapper
public interface AdminMapper {
    @Select("select * from caix_admin where cx_id02=#{username} and cx_password02=#{password}")
    Admin login(Integer username, String password);

    @Select("select * from caix_admin where cx_id02=#{username}")
    Admin selectByUsername(Integer username);

    @Select("select count(*) from caix_admin where cx_id02=#{username}")
    int checkUsername(Integer username);

    @Update("call admin_change_password(#{password}, #{username})")
    void changePassword(Integer username, String password);

    @Insert("call increase_admin(#{id}, #{password}, #{role})")
    void insert(Integer id, String password, String role);

    @Update("update caix_admin set cx_role02=#{role} where cx_id02=#{username}")
    void updateRole(Integer username, String role);

    @Delete("delete from caix_admin where cx_id02=#{username}")
    void delete(Integer username);

}

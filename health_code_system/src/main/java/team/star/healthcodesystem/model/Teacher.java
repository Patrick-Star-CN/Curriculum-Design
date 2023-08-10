package team.star.healthcodesystem.model;

import lombok.Builder;
import lombok.Data;

/**
 * 教师数据模型
 *
 * @author Patrick_Star
 * @version 1.3
 */
@Data
@Builder
public class Teacher {
    /**
     * 工号
     */
    private Long id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private String iid;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户类型
     */
    private Type type;
    /**
     * 学院 id
     */
    private Integer collegeId;

    public enum Type {
        /**
         * 教师
         */
        TEACHER,
        /**
         * 系统管理员
         */
        SUPER_ADMIN,
        /**
         * 校级管理员
         */
        SCHOOL_ADMIN,
        /**
         * 院级管理员
         */
        COLLEGE_ADMIN;
    }
}

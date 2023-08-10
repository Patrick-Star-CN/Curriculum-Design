package team.star.healthcodesystem.model;

import lombok.Builder;
import lombok.Data;

/**
 * 学生数据模型
 *
 * @author Patrick_Star
 * @version 1.1
 */
@Data
@Builder
public class Student {
    /**
     * 学号
     */
    private Long id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 学院 id
     */
    private Integer collegeId;
    /**
     * 身份证号
     */
    private String iid;
    /**
     * 专业 id
     */
    private Integer majorId;
    /**
     * 班级 id
     */
    private Integer classId;
}

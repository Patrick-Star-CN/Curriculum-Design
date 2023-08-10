package team.star.healthcodesystem.model;

import lombok.Builder;
import lombok.Data;

/**
 * 班级数据模型
 *
 * @author Patrick_Star
 * @version 1.0
 */
@Data
@Builder
public class Class {
    /**
     * 班级 id
     */
    private Integer id;
    /**
     * 专业 id
     */
    private Integer majorId;
    /**
     * 班级名称
     */
    private String name;
}

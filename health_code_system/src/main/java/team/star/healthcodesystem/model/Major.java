package team.star.healthcodesystem.model;

import lombok.Builder;
import lombok.Data;

/**
 * 专业数据模型
 *
 * @author Patrick_Star
 * @version 1.0
 */
@Data
@Builder
public class Major {
    /**
     * 专业 id
     */
    private Integer id;
    /**
     * 学院 id
     */
    private Integer collegeId;
    /**
     * 专业名称
     */
    private String name;
}

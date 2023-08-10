package team.star.healthcodesystem.model;

import lombok.Builder;
import lombok.Data;

/**
 * 学院数据模型
 *
 * @author Patrick_Star
 * @version 1.0
 */
@Data
@Builder
public class College {
    /**
     * 学院 id
     */
    private Integer id;
    /**
     * 学院名称
     */
    private String name;
}

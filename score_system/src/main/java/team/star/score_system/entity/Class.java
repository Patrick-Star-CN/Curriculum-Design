package team.star.score_system.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Data
@Builder
public class Class {
    private Integer id;
    private String name;
    private String majorName;
    private String teacherName;
}

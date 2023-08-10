package team.star.healthcodesystem.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 班级数据传输对象
 *
 * @author Patrick_Star
 * @version 1.0
 */
@Data
@Builder
public class ClassDto {
    private int id;
    private String name;
    private String majorName;
}

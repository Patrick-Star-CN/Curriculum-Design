package team.star.healthcodesystem.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Data
@Builder
public class TeacherDto {
    private Long id;
    private String name;
    private String iid;
    private String password;
    private String type;
    private String collegeName;
}

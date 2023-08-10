package team.star.healthcodesystem.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Data
@Builder
public class MajorDto {
    private int id;
    private String name;
    private String collegeName;
}

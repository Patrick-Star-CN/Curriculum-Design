package team.star.healthcodesystem.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Data
@Builder
public class RecordDto {
    private long id;
    private String name;
    private String codeColor;
}

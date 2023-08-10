package team.star.score_system.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CourseDto {
    private Integer id;
    private String name;
    private Double credit;
    private String collegeName;
    private String term;
    private Integer hours;
    private String examinationMethod;
    private String attribute;
    private Integer year;
}

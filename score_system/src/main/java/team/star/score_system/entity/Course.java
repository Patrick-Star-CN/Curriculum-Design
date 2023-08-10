package team.star.score_system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {
    private String id;
    private String name;
    private String term;
    private String collegeName;
    private String examinationMethod;
    private double credit;
    private int hours;
    private String attribute;
    private int year;
}

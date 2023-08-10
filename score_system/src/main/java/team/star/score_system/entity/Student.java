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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    private long id;
    private String name;
    private String password;
    private String className;
    private String gender;
    private double credits;
    private int birthYear;
    private String hometown;

}

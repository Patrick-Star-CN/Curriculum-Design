package team.star.score_system.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Data
@Builder
public class Admin {
    private Integer id;
    private String password;
    private Role role;

    public enum Role {
        SUPER_ADMIN, COLLEGE_ADMIN, SCHOOL_ADMIN
    }
}

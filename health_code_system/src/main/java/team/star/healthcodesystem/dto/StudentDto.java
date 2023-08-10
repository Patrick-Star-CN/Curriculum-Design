package team.star.healthcodesystem.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 学生数据传输对象
 *
 * @author Patrick_Star
 * @version 1.0
 */
@Data
@Builder
public class StudentDto {
    private long id;
    private String name;
    private String iid;
    private String collegeName;
    private String className;
    private String majorName;
}

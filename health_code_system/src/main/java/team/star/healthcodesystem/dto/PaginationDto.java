package team.star.healthcodesystem.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Data
@Builder
public class PaginationDto<T> {
    private ArrayList<T> data;
    private int size;
    private int page;
    private int total;
}

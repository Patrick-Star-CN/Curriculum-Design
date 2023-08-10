package team.star.healthcodesystem.model;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

/**
 * 记录数据类型
 *
 * @author Patrick_Star
 * @version 1.2
 */
@Data
@Builder
public class Record {
    /**
     * 记录 id
     */
    private Integer id;
    /**
     * 工号或学号
     */
    private Long userId;
    /**
     * 手机号
     */
    private String phoneNum;
    /**
     * 健康码颜色
     */
    private Color color;
    /**
     * 记录时间
     */
    private Date createTime;

    public enum Color {
        /**
         * 绿色
         */
        GREEN,
        /**
         * 黄色
         */
        YELLOW,
        /**
         * 红色
         */
        RED,
        /**
         * 灰色
         */
        GREY
    }
}

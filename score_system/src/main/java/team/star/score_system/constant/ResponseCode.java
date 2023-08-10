package team.star.score_system.constant;

import lombok.Getter;

/**
 * @author patrick_star
 * @version 1.0
 */
@Getter
public enum ResponseCode {
    /**
     * 成功
     */
    SUCCESS(200),
    /**
     * 失败
     */
    FAIL(400),
    /**
     * 无权限
     */
    UN_AUTH(401),
    /**
     * 禁止
     */
    FORBIDDEN(403);

    private final Integer code;

    ResponseCode(Integer code) {
        this.code = code;
    }
}
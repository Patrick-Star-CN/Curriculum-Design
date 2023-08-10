package team.star.healthcodesystem.constant;

import lombok.Getter;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Getter
public enum ErrorCode {
    /** 身份认证失败 */
    NOT_LOGIN(200100, "未登陆"),
    /** 用户已存在 */
    USER_EXISTED(200101, "用户已存在"),
    /** 用户不存在 */
    USER_NOT_EXISTED(200102, "用户不存在"),
    /** 密码错误 */
    PASSWORD_ERROR(200103, "密码错误"),
    /** 无权限 */
    NO_PERMISSION(200104, "无权限"),
    /** 服务器异常 */
    SERVER_ERROR(200300, "服务器异常"),
    /** 今日已提交健康码 */
    RECORD_EXISTED(200201, "今日已提交健康码"),
    /** 参数有误 */
    PARAM_ERROR(200302, "参数有误");

    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

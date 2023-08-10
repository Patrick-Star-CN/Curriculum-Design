package team.star.score_system.constant;

import lombok.Getter;

/**
 * 错误类型枚举类
 *
 * @author patrick_star
 * @version 1.4
 */
@Getter
public enum ErrorCode {
    /** 身份认证失败 */
    NOT_LOGIN(200100, "未登陆"),
    /** 用户已存在 */
    USER_EXISTED(200101, "用户已存在"),
    /** 用户不存在 */
    USER_NOT_EXISTED(200102, "用户不存在"),
    /** 权限不足 */
    PERMISSION_DENIED(200103, "权限不足"),
    /** 文件错误 */
    FILE_ERROR(200104, "文件错误"),

    /** 服务器异常 */
    SERVER_ERROR(200300, "服务器异常"),
    /** 参数有误 */
    PARAM_ERROR(200302, "参数有误"),

    /** 非法请求 */
    ILLEGAL_REQUEST(200404, "非法请求"),
    ;

    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

package team.star.score_system.exception;

import lombok.Getter;
import team.star.score_system.constant.ErrorCode;

/**
 * 自定义异常
 *
 * @author patrick_star
 * @version 1.0
 */
@Getter
public class AppException extends RuntimeException{
    private final ErrorCode code;
    private final Object data;

    public AppException(ErrorCode code) {
        this.code = code;
        this.data = null;
    }

    public AppException(ErrorCode code, Object data) {
        this.code = code;
        this.data = data;
    }
}

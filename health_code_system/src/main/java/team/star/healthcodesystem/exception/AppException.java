package team.star.healthcodesystem.exception;


import lombok.Getter;
import team.star.healthcodesystem.constant.ErrorCode;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Getter
public class AppException extends RuntimeException {
    private final ErrorCode code;
    private final Object data;

    public AppException(ErrorCode code) {
        super(code.getMessage());
        this.code = code;
        this.data = null;
    }

    public AppException(ErrorCode code, Object data) {
        super(code.getMessage());
        this.code = code;
        this.data = data;
    }

}

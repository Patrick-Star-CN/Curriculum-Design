package team.star.score_system.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import team.star.score_system.config.ProfileConfig;
import team.star.score_system.dto.AjaxResp;
import team.star.score_system.dto.ErrorDetail;

import java.time.Instant;

/**
 * 未被其余handler处理，则最终进入该handler处理，处理Exception子类
 *
 * @author patrick_star
 * @version 1.0
 */
@ControllerAdvice
@Order(1000)
@RequiredArgsConstructor
public class GlobalExceptionHandler {


    final ProfileConfig profileConfig;

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public AjaxResp<Object> handleGlobalException(Exception e, HttpServletRequest request) {
        if (profileConfig.isDev()) {
            // dev环境时返回的异常信息
            ErrorDetail errorDetail = ErrorDetail.builder()
                    .requestId(request.getAttribute("requestId").toString())
                    .message(e.getMessage()).path(request.getRequestURI())
                    .timestamp(Instant.now()).build();
            e.printStackTrace();
            return AjaxResp.FAIL("全局异常", errorDetail);
        } else {
            // 其他环境时返回的异常信息
            ErrorDetail errorDetail = ErrorDetail.builder()
                    .timestamp(Instant.now()).build();

            return AjaxResp.FAIL("请求失败", errorDetail);
        }

    }

}
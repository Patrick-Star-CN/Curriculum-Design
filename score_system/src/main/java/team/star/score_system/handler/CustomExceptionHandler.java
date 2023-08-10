package team.star.score_system.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import team.star.score_system.config.ProfileConfig;
import team.star.score_system.dto.AjaxResp;
import team.star.score_system.dto.ErrorDetail;
import team.star.score_system.exception.AppException;

import java.time.Instant;

/**
 * 处理AppException及其子类，最先执行
 * 
 * @author patrick_star
 * @version 1.0
 */
@ControllerAdvice
@Slf4j
@Order(80)
@RequiredArgsConstructor
public class CustomExceptionHandler {

    final ProfileConfig profileConfig;

    @ExceptionHandler(AppException.class)
    @ResponseBody
    public AjaxResp<Object> handleAppException(AppException e, HttpServletRequest request) {
        ErrorDetail errorDetail;
        if (profileConfig.isDev()) {
            errorDetail = ErrorDetail.builder().code(e.getCode().getCode())
                    .message(e.getCode().getMessage())
                    .requestId(request.getAttribute("requestId").toString())
                    .data(e.getData()).path(request.getRequestURI())
                    .timestamp(Instant.now()).build();
            log.error(errorDetail.toString());
        } else {
            errorDetail = ErrorDetail.builder().code(e.getCode().getCode())
                    .message(e.getCode().getMessage())
                    .timestamp(Instant.now()).build();
        }

        return AjaxResp.FAIL(errorDetail);
    }

}
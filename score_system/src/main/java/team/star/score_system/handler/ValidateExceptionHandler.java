package team.star.score_system.handler;

import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import team.star.score_system.config.ProfileConfig;
import team.star.score_system.dto.AjaxResp;
import team.star.score_system.dto.ErrorDetail;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;

/**
 * 处理参数校验相关异常
 *
 * @author patrick_star
 * @version 1.0
 */
@Order(10)
@ControllerAdvice
@RequiredArgsConstructor
public class ValidateExceptionHandler {
    final ProfileConfig profileConfig;

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public AjaxResp<Object> handleSaTokenException(ConstraintViolationException e, HttpServletRequest request) {
        ErrorDetail errorDetail;
        if (profileConfig.isDev()) {
            errorDetail = ErrorDetail.builder()
                    .requestId(request.getAttribute("requestId").toString())
                    .message(e.getMessage()).path(request.getRequestURI())
                    .timestamp(Instant.now()).build();
        } else {
            errorDetail = ErrorDetail.builder()
                    .timestamp(Instant.now()).build();
        }

        return AjaxResp.FAIL("格式校验错误", errorDetail);
    }

    /**
     * 参数校验错误拦截处理
     *
     * @param exception 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public AjaxResp<Object> validationBodyException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        ErrorDetail errorDetail;
        if (profileConfig.isDev()) {
            errorDetail = ErrorDetail.builder()
                    .requestId(request.getAttribute("requestId").toString())
                    .message(exception.getMessage()).path(request.getRequestURI())
                    .timestamp(Instant.now()).build();
        } else {
            errorDetail = ErrorDetail.builder()
                    .timestamp(Instant.now()).build();
        }

        return AjaxResp.FAIL("参数校验错误", errorDetail);
    }

    /**
     * SQL执行失败错误（重复主键）拦截处理
     *
     * @param exception 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseBody
    public AjaxResp<Object> validationBodyException(SQLIntegrityConstraintViolationException exception, HttpServletRequest request) {
        ErrorDetail errorDetail;
        if (profileConfig.isDev()) {
            errorDetail = ErrorDetail.builder()
                    .requestId(request.getAttribute("requestId").toString())
                    .message(exception.getMessage()).path(request.getRequestURI())
                    .timestamp(Instant.now()).build();
        } else {
            errorDetail = ErrorDetail.builder()
                    .timestamp(Instant.now()).build();
        }

        return AjaxResp.FAIL("SQL执行失败，请检查是否字段填写错误", errorDetail);
    }

    /**
     * Json解析失败错误（字段填写错误或漏填必选值）拦截处理
     *
     * @param exception 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler(JsonMappingException.class)
    @ResponseBody
    public AjaxResp<Object> validationBodyException(JsonMappingException exception, HttpServletRequest request) {
        ErrorDetail errorDetail;
        if (profileConfig.isDev()) {
            errorDetail = ErrorDetail.builder()
                    .requestId(request.getAttribute("requestId").toString())
                    .message(exception.getMessage()).path(request.getRequestURI())
                    .timestamp(Instant.now()).build();
        } else {
            errorDetail = ErrorDetail.builder()
                    .timestamp(Instant.now()).build();
        }

        return AjaxResp.FAIL("Json解析失败，", errorDetail);
    }

}
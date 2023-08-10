package team.star.score_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import team.star.score_system.constant.ResponseCode;

/**
 * @author patrick_star
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class AjaxResp<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <N> AjaxResp<N> SUCCESS() { return new AjaxResp<>(ResponseCode.SUCCESS.getCode(), "success",null); }

    public static <N> AjaxResp<N> SUCCESS(N data) { return new AjaxResp<>(ResponseCode.SUCCESS.getCode(), "success", data); }

    public static <N> AjaxResp<N> SUCCESS(String msg, N data) { return new AjaxResp<>(ResponseCode.SUCCESS.getCode(), msg, data); }

    public static <N> AjaxResp<N> FAIL() { return new AjaxResp<>(ResponseCode.FAIL.getCode(), "fail", null); }

    public static <N> AjaxResp<N> FAIL(N data) { return new AjaxResp<>(ResponseCode.FAIL.getCode(), "fail", data); }

    public static <N> AjaxResp<N> FAIL(String msg, N data) { return new AjaxResp<>(ResponseCode.FAIL.getCode(), msg, data); }
}
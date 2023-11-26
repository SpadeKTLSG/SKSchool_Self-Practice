package com.tlsg.skxy.common;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 全局统一返回结果类
 */
@Data
public class Result<T> {

    @Schema(name = "code", type = "Integer", description = "状态码", example = "200")
    private Integer code;

    @Schema(name = "message", type = "String", description = "消息", example = "操作成功")
    private String message;

    @Schema(name = "data", type = "Object", description = "数据")
    private T data;


    public Result() {
    }


    //    // 返回数据
//    @Operation(summary = "返回数据", parameters = {
//            @Parameter(name = "data", description = "数据", required = true)
//    })
    protected static <T> Result<T> build(T data) {
        Result<T> result = new Result<T>();
        if (data != null)
            result.setData(data);
        return result;
    }

    public static <T> Result<T> build(T body, ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(body);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    public static <T> Result<T> ok() {
        return Result.ok(null);
    }

    /**
     * 操作成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> ok(T data) {
        Result<T> result = build(data);
        return build(data, ResultCodeEnum.SUCCESS);
    }

    public static <T> Result<T> fail() {
        return Result.fail(null);
    }

    /**
     * 操作失败
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> fail(T data) {
        Result<T> result = build(data);
        return build(data, ResultCodeEnum.FAIL);
    }

    public Result<T> message(String msg) {
        this.setMessage(msg);
        return this;
    }

    public Result<T> code(Integer code) {
        this.setCode(code);
        return this;
    }


    public boolean isOk() {
        if (this.getCode().intValue() == ResultCodeEnum.SUCCESS.getCode().intValue()) {
            return true;
        }
        return false;
    }
}

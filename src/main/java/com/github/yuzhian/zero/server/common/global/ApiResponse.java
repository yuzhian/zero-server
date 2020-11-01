package com.github.yuzhian.zero.server.common.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * 基础返回类型
 *
 * @param <T>
 * @author yuzhian
 * @since 2020-10-31
 */
@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "ApiResponse", description = "基础返回类型")
public class ApiResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "状态码")
    private final Integer code;

    @ApiModelProperty(value = "提示信息")
    private final String message;

    @ApiModelProperty(value = "返回数据")
    private final T data;

    public static <T> ApiResponse<T> of(Integer code, String message, T data) {
        return new ApiResponse<>(code, message, data);
    }

    public static <T> ApiResponse<T> of(Integer code, T data) {
        return new ApiResponse<>(code, null, data);
    }

    public static <T> ApiResponse<T> of(Integer code, String message) {
        return new ApiResponse<>(code, message, null);
    }
}

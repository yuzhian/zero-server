package com.github.yuzhian.zero.server.common.global;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 业务逻辑异常
 *
 * @author yuzhian
 * @since 2020-10-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApiRuntimeException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;

    public ApiRuntimeException(HttpStatus status, String message) {
        this.code = status.value();
        this.message = message;
    }

    public ApiRuntimeException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}

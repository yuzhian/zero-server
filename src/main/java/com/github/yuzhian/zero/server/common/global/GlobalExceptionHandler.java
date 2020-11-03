package com.github.yuzhian.zero.server.common.global;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author yuzhian
 * @since 2020-10-31
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常处理
     */
    @ExceptionHandler(value = ApiRuntimeException.class)
    public ResponseEntity<String> apiRuntimeExceptionHandler(ApiRuntimeException e) {
        if (log.isWarnEnabled()) log.warn("apiRuntimeExceptionHandler: {}", e.getMessage());
        return ResponseEntity.status(e.getCode()).body(e.getMessage());
    }

    /**
     * 参数校验未通过 400
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> validExceptionHandler(MethodArgumentNotValidException e) {
        if (log.isWarnEnabled()) log.warn("validExceptionHandler: {}", e.getMessage());
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        StringBuilder msg = new StringBuilder();
        for (ObjectError error : errors) {
            msg.append(error.getDefaultMessage()).append(",");
        }
        return ResponseEntity.badRequest().body(msg.substring(0, msg.length() - 1));
    }

    /**
     * 未认证
     */
    @ExceptionHandler(value = AuthorizationException.class)
    public ResponseEntity<String> authorizationExceptionHandler(AuthorizationException e) {
        if (log.isWarnEnabled()) log.warn("authorizationExceptionHandler: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("未认证");
    }

    /**
     * 无权限
     */
    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity<String> unauthorizedExceptionHandler(UnauthorizedException e) {
        if (log.isWarnEnabled()) log.warn("unauthorizedExceptionHandler: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("未授权");
    }

    /**
     * 未处理的异常 500
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> defaultExceptionHandler(Exception e) {
        if (log.isErrorEnabled()) log.error("Internal Server Error: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("服务器异常");
    }

}

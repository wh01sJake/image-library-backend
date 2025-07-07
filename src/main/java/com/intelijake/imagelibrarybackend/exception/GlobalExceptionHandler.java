package com.intelijake.imagelibrarybackend.exception;

import com.intelijake.imagelibrarybackend.common.BaseResponse;
import com.intelijake.imagelibrarybackend.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ClassName: GlobalExceptionHandler
 * Description:
 * <p>
 * Datetime: 2025/7/5 19:27
 * Author: @Likun.Fang
 * Version: 1.0
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e){

        log.error("businessException",e);

        return ResultUtils.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseResponse<?> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e){

        log.error("JSON parse error",e);

        // 检查是否是枚举转换错误
        if (e.getMessage() != null && e.getMessage().contains("UserRoleEnum")) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "Invalid user role value. Please use 'user' or 'admin'");
        }

        return ResultUtils.error(ErrorCode.PARAMS_ERROR, "Invalid request format");
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e){

        log.error("runtimeException",e);

        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"System Error");
    }
}

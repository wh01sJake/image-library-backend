package com.intelijake.imagelibrarybackend.exception;

import lombok.Getter;

/**
 * ClassName: BusinessException
 * Description:
 * <p>
 * Datetime: 2025/7/5 19:05
 * Author: @Likun.Fang
 * Version: 1.0
 */

@Getter
public class BusinessException extends RuntimeException {

    private final int code;

    public BusinessException(String message, int code) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {

        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode,String message){

        super(message);
        this.code = errorCode.getCode();

    }
}

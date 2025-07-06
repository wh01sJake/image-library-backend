package com.intelijake.imagelibrarybackend.common;

import com.intelijake.imagelibrarybackend.exception.ErrorCode;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: BaseResponse
 * Description:
 * <p>
 * Datetime: 2025/7/5 19:15
 * Author: @Likun.Fang
 * Version: 1.0
 */

@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code,T data) {
       this(code,data,"");
    }

    public BaseResponse(ErrorCode errorCode){
        this(errorCode.getCode(),null,errorCode.getMessage());
    }


}

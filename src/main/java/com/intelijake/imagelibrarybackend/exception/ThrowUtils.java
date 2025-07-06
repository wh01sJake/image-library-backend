package com.intelijake.imagelibrarybackend.exception;

/**
 * ClassName: ThrowUtils
 * Description:
 * throw exception utils
 * Datetime: 2025/7/5 19:09
 * Author: @Likun.Fang
 * Version: 1.0
 */
public class ThrowUtils {

    /**
     * @param condition
     * @param runtimeException
     *
     * */
    public static void throwIf (boolean condition, RuntimeException runtimeException){
        if (condition){
            throw runtimeException;
        }
    }

    /**
     * @param condition
     * @param errorCode
     *
     * */
    public static void throwIf (boolean condition, ErrorCode errorCode){

        throwIf(condition,new BusinessException(errorCode));
    }

    /**
     * @param condition
     * @param errorCode
     * @param message
     *
     * */
    public static void throwIf (boolean condition, ErrorCode errorCode,String message){

        throwIf(condition,new BusinessException(errorCode,message));
    }

}

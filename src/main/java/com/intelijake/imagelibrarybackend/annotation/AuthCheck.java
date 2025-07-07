package com.intelijake.imagelibrarybackend.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName: AuthCheck
 * Description:
 * <p>
 * Datetime: 06/07/2025 23:13
 * Author: @Likun.Fang
 * Version: 1.0
 */

@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {


    /**
     * must have certain role
     *
     * @return
     */
    String mustRole() default "";
}

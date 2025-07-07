package com.intelijake.imagelibrarybackend.aop;

import com.intelijake.imagelibrarybackend.annotation.AuthCheck;
import com.intelijake.imagelibrarybackend.exception.BusinessException;
import com.intelijake.imagelibrarybackend.exception.ErrorCode;
import com.intelijake.imagelibrarybackend.model.entity.User;
import com.intelijake.imagelibrarybackend.model.enums.UserRoleEnum;
import com.intelijake.imagelibrarybackend.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * ClassName: LoginInterceptor
 * Description:
 * <p>
 * Datetime: 06/07/2025 23:15
 * Author: @Likun.Fang
 * Version: 1.0
 */


@Aspect
@Component
public class AuthInterceptor {


    @Resource
    UserService userService;


    @Around(value = "@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {

        UserRoleEnum requiredRole = UserRoleEnum.fromValue(authCheck.mustRole());

        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();

        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        //get current user login info
        User loginUser = userService.getLoginUser(request);


        //get current user role

        UserRoleEnum userRole = loginUser.getUserRole();

        if (userRole == null) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        if (!userRole.hasPermission(requiredRole)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        return joinPoint.proceed();

    }
}

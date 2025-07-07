package com.intelijake.imagelibrarybackend.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ClassName: UserRoleEnum
 * Description:
 * <p>
 * Datetime: 06/07/2025 17:41
 * Author: @Likun.Fang
 * Version: 1.0
 */

@Getter
public enum UserRoleEnum {

    USER("user", 0),
    ADMIN("admin", 1);


    @EnumValue
    @JsonValue
    private final String value;

    private final int level;


    UserRoleEnum(String value, int level) {

        this.value = value;
        this.level = level;
    }


    private static final Map<String, UserRoleEnum> VALUE_MAP = Arrays.stream(values())
            .collect(Collectors.toMap(UserRoleEnum::getValue, Function.identity()));


    @JsonCreator
    public static UserRoleEnum fromValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null; // 允许空值，让业务逻辑处理
        }
        return VALUE_MAP.get(value);
    }

    public boolean hasPermission(UserRoleEnum requiredRole) {
        if (requiredRole == null) {
            return true; // 如果接口不需要特定角色，则所有登录用户都有权限
        }
        return this.level >= requiredRole.level; // 核心：当前用户级别 >= 接口要求级别
    }
}

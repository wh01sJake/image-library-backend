package com.intelijake.imagelibrarybackend.model.dto.user;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.intelijake.imagelibrarybackend.model.enums.UserRoleEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserAddRequest implements Serializable {

    private static final long serialVersionUID = -7850060453290296151L;
    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色: user, admin
     */
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private UserRoleEnum userRole;


}

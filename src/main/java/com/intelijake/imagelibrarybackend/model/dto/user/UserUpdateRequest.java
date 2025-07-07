package com.intelijake.imagelibrarybackend.model.dto.user;

import com.intelijake.imagelibrarybackend.model.enums.UserRoleEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserUpdateRequest implements Serializable {

    private static final long serialVersionUID = -3820798436915856980L;
    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin
     */
    private UserRoleEnum userRole;

}

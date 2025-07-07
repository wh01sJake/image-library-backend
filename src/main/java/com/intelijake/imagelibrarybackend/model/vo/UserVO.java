package com.intelijake.imagelibrarybackend.model.vo;

import com.intelijake.imagelibrarybackend.model.enums.UserRoleEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserVO implements Serializable {

    private static final long serialVersionUID = 5914479125570026080L;
    /**
     * id
     */
    private Long id;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin
     */
    private UserRoleEnum userRole;

    /**
     * 创建时间
     */
    private Date createTime;

}

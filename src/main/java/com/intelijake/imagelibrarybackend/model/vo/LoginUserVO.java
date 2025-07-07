package com.intelijake.imagelibrarybackend.model.vo;

import com.intelijake.imagelibrarybackend.model.enums.UserRoleEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * LoginUserVO
 */
@Data
public class LoginUserVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * userAccount
     */
    private String userAccount;


    /**
     * username
     */
    private String userName;

    /**
     * userAvatar
     */
    private String userAvatar;

    /**
     * userProfile
     */
    private String userProfile;

    /**
     * role：user/admin
     */
    private UserRoleEnum userRole;

    /**
     * edit time
     */
    private Date editTime;

    /**
     * create time
     */
    private Date createTime;

    /**
     * update time
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
package com.intelijake.imagelibrarybackend.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.intelijake.imagelibrarybackend.model.enums.UserRoleEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

/**
 * user
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * userAccount
     */
    private String userAccount;

    /**
     * password
     */
    private String userPassword;

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
    @Enumerated(EnumType.STRING)
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

    /**
     * 0 for not deleted, 1 for is deleted
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
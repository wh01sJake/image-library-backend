package com.intelijake.imagelibrarybackend.model.dto.user;

import com.intelijake.imagelibrarybackend.common.PageRequest;
import com.intelijake.imagelibrarybackend.model.enums.UserRoleEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {


    private static final long serialVersionUID = 7675532095991293352L;
    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    private UserRoleEnum userRole;

}

package com.intelijake.imagelibrarybackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: UserRegisterRequest
 * Description:
 * <p>
 * Datetime: 06/07/2025 17:50
 * Author: @Likun.Fang
 * Version: 1.0
 */

@Data
public class UserRegisterRequest implements Serializable {


    private static final long serialVersionUID = 6406092034005870966L;


    private String userAccount;

    private String userPassword;

    private String checkPassword;
}

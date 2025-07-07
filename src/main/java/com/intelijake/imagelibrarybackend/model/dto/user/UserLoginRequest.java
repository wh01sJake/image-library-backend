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
public class UserLoginRequest implements Serializable {


    private static final long serialVersionUID = -5274913267720922862L;

    private String userAccount;

    private String userPassword;

}

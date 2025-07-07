package com.intelijake.imagelibrarybackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.intelijake.imagelibrarybackend.annotation.AuthCheck;
import com.intelijake.imagelibrarybackend.common.BaseResponse;
import com.intelijake.imagelibrarybackend.common.DeleteRequest;
import com.intelijake.imagelibrarybackend.common.ResultUtils;
import com.intelijake.imagelibrarybackend.constant.UserConstant;
import com.intelijake.imagelibrarybackend.exception.ErrorCode;
import com.intelijake.imagelibrarybackend.exception.ThrowUtils;
import com.intelijake.imagelibrarybackend.model.dto.user.*;
import com.intelijake.imagelibrarybackend.model.entity.User;
import com.intelijake.imagelibrarybackend.model.vo.LoginUserVO;
import com.intelijake.imagelibrarybackend.model.vo.UserVO;
import com.intelijake.imagelibrarybackend.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: MainController
 * Description:
 * <p>
 * Datetime: 2025/7/5 19:39
 * Author: @Likun.Fang
 * Version: 1.0
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;


    /**
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {

        ThrowUtils.throwIf(userRegisterRequest == null, ErrorCode.PARAMS_ERROR);

        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        Long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }


    /**
     * @param userLoginRequest
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {

        ThrowUtils.throwIf(userLoginRequest == null, ErrorCode.PARAMS_ERROR);

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword, request);

        return ResultUtils.success(loginUserVO);
    }


    /**
     * get current login user
     *
     * @param request
     * @return
     */
    @GetMapping("/get/login")
    public BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest request) {

        User loginUser = userService.getLoginUser(request);

        LoginUserVO loginUserVO = userService.getLoginUserVO(loginUser);

        return ResultUtils.success(loginUserVO);
    }


    /**
     * user log out(has to be logged in first)
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {

        Boolean result = userService.userLogout(request);

        return ResultUtils.success(result);
    }


    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addUser(@RequestBody UserAddRequest userAddRequest) {

        Long result = userService.addUser(userAddRequest);

        return ResultUtils.success(result);
    }

    /**
     * get user by id, admin only
     * @param id
     * @return
     */
    @GetMapping("/get")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<User> getUserById(long id){

        ThrowUtils.throwIf(id<=0,ErrorCode.PARAMS_ERROR);

        User user = userService.getById(id);

        ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR);

        return ResultUtils.success(user);
    }


    /**
     * get UserVO by id
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<UserVO> getUserVOById(long id){

        UserVO userVO = userService.getUserVOById(id);

        return ResultUtils.success(userVO);
    }

    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest){

        ThrowUtils.throwIf(deleteRequest == null || deleteRequest.getId() == null,ErrorCode.PARAMS_ERROR);

        boolean result = userService.removeById(deleteRequest.getId());

        ThrowUtils.throwIf(!result,ErrorCode.OPERATION_ERROR,"delete failed");

        return ResultUtils.success(result);
    }

    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest){

        Boolean result = userService.updateUser(userUpdateRequest);

        return ResultUtils.success(result);
    }


    @PostMapping("/list/page/vo")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<UserVO>> listUserVOByPage(@RequestBody UserQueryRequest userQueryRequest){

        Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);

        return ResultUtils.success(userVOPage);

    }











}

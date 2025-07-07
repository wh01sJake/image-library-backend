package com.intelijake.imagelibrarybackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.intelijake.imagelibrarybackend.model.dto.user.UserAddRequest;
import com.intelijake.imagelibrarybackend.model.dto.user.UserQueryRequest;
import com.intelijake.imagelibrarybackend.model.dto.user.UserUpdateRequest;
import com.intelijake.imagelibrarybackend.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.intelijake.imagelibrarybackend.model.vo.LoginUserVO;
import com.intelijake.imagelibrarybackend.model.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author likunfang
* @description 针对表【user(user)】的数据库操作Service
* @createDate 2025-07-06 17:28:43
*/
public interface UserService extends IService<User> {


    Long userRegister(String userAccount,String userPassword,String checkPassword);

    String getEncryptPassword(String userPassword);


    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);


    /**
     * get LoginUserVO without sensitive data
     * @param user
     * @return
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * Convert User to UserVO
     * @param user
     * @return
     */
    UserVO getUserVO(User user);

    /**
     * Convert User list to UserVO list
     * @param userList
     * @return
     */
    List<UserVO> getUserListVO(List<User> userList);


    /**
     * get current login user
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);


    /**
     * user logout
     * @param request
     * @return
     */
    Boolean userLogout(HttpServletRequest request);


    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

    /**
     * Add user (admin only)
     * @param userAddRequest
     * @return user id
     */
    Long addUser(UserAddRequest userAddRequest);

    /**
     * Update user (admin only)
     * @param userUpdateRequest
     * @return success
     */
    Boolean updateUser(UserUpdateRequest userUpdateRequest);

    /**
     * Get UserVO by id
     * @param id
     * @return UserVO
     */
    UserVO getUserVOById(long id);

    /**
     * Get paginated UserVO list (admin only)
     * @param userQueryRequest
     * @return Page<UserVO>
     */
    Page<UserVO> listUserVOByPage(UserQueryRequest userQueryRequest);
}

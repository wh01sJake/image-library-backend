package com.intelijake.imagelibrarybackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.intelijake.imagelibrarybackend.constant.UserConstant;
import com.intelijake.imagelibrarybackend.exception.BusinessException;
import com.intelijake.imagelibrarybackend.exception.ErrorCode;
import com.intelijake.imagelibrarybackend.exception.ThrowUtils;
import com.intelijake.imagelibrarybackend.model.dto.user.UserAddRequest;
import com.intelijake.imagelibrarybackend.model.dto.user.UserQueryRequest;
import com.intelijake.imagelibrarybackend.model.dto.user.UserUpdateRequest;
import com.intelijake.imagelibrarybackend.model.entity.User;
import com.intelijake.imagelibrarybackend.model.enums.UserRoleEnum;
import com.intelijake.imagelibrarybackend.model.vo.LoginUserVO;
import com.intelijake.imagelibrarybackend.model.vo.UserVO;
import com.intelijake.imagelibrarybackend.service.UserService;
import com.intelijake.imagelibrarybackend.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author likunfang
* @description 针对表【user(user)】的数据库操作Service实现
* @createDate 2025-07-06 17:28:43
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{


    /**
     *
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return
     */
    @Override
    public Long userRegister(String userAccount, String userPassword, String checkPassword) {

        // 1. params check

        ThrowUtils.throwIf(StrUtil.hasBlank(userAccount,userPassword,checkPassword), ErrorCode.PARAMS_ERROR,"blank params");

        ThrowUtils.throwIf(userAccount.length()<6,ErrorCode.PARAMS_ERROR,"userAccount too short");

        ThrowUtils.throwIf(userPassword.length()<8,ErrorCode.PARAMS_ERROR,"userPassword too short");

        ThrowUtils.throwIf(!(userPassword.equals(checkPassword)),ErrorCode.PARAMS_ERROR,"password has to be the same");

        // 2. check if account already exist

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("userAccount",userAccount);

        Long count = this.baseMapper.selectCount(queryWrapper);

        ThrowUtils.throwIf(count > 0,ErrorCode.PARAMS_ERROR,"account already exist");

        // save new user

        User user = new User();


        user.setUserAccount(userAccount);

        // encrypt password
        String encryptedPassword = getEncryptPassword(userPassword);

        user.setUserPassword(encryptedPassword);

        user.setUserRole(UserRoleEnum.USER);

        user.setUserName("user" + encryptedPassword.substring(0,5));

        boolean saveResult = this.save(user);


        ThrowUtils.throwIf(!saveResult,ErrorCode.SYSTEM_ERROR,"Register failed, please try again later");

        return user.getId();
    }


    /**
     *  password salt and encrypt
     *
     * @param userPassword
     * @return
     */
    @Override
    public String getEncryptPassword(String userPassword){

        final String SALT = "AUTHORISJAKE";

       return DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());
    }


    /**
     * userLogin
     * @param userAccount
     * @param userPassword
     * @param request
     * @return
     */
    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {

        //1.validate data

        ThrowUtils.throwIf(StrUtil.hasBlank(userAccount,userPassword), ErrorCode.PARAMS_ERROR,"blank params");

        ThrowUtils.throwIf(userAccount.length()<6,ErrorCode.PARAMS_ERROR,"userAccount too short");

        ThrowUtils.throwIf(userPassword.length()<8,ErrorCode.PARAMS_ERROR,"userPassword too short");

        //2.encrypt password
        String encryptPassword = getEncryptPassword(userPassword);

        //3. check credential match

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("userAccount",userAccount);

        queryWrapper.eq("userPassword",encryptPassword);

        User user = this.baseMapper.selectOne(queryWrapper);

        ThrowUtils.throwIf(user == null, ErrorCode.PARAMS_ERROR,"invalid username or password");

        //4. save use login state

        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE,user);


        // 5. return LoginUserVO
        return getLoginUserVO(user);
    }

    @Override
    public LoginUserVO getLoginUserVO(User user) {

        if (user == null){
            return null;
        }

        LoginUserVO loginUserVO = new LoginUserVO();

        BeanUtil.copyProperties(user,loginUserVO);

        return loginUserVO;
    }

    @Override
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }

        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setUserAccount(user.getUserAccount());
        userVO.setUserName(user.getUserName());
        userVO.setUserAvatar(user.getUserAvatar());
        userVO.setUserProfile(user.getUserProfile());
        userVO.setUserRole(user.getUserRole());
        userVO.setCreateTime(user.getCreateTime());

        return userVO;
    }

    @Override
    public List<UserVO> getUserListVO(List<User> userList) {
        if (userList == null) {
            return null;
        }

        return userList.stream()
                .map(this::getUserVO)
                .collect(Collectors.toList());
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {

        //1. check if user logged in
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);

        User currentUser = (User) userObj;

        if (currentUser == null || currentUser.getId() == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        //query again in case user edited something

        Long id = currentUser.getId();

        currentUser = this.baseMapper.selectById(id);

        if (currentUser == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        return currentUser;
    }

    @Override
    public Boolean userLogout(HttpServletRequest request) {

        //1. check if user logged in
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);

        if (userObj == null ){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }

        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);

        return true;
    }

    @Override
    public QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest) {

        if (userQueryRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"blank params");
        }

        Long id = userQueryRequest.getId();
        String userName = userQueryRequest.getUserName();
        String userAccount = userQueryRequest.getUserAccount();
        String userProfile = userQueryRequest.getUserProfile();
        UserRoleEnum userRole = userQueryRequest.getUserRole();
//        int current = userQueryRequest.getCurrent();
//        int pageSize = userQueryRequest.getPageSize();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(ObjectUtil.isNotNull(id),"id",id);

        queryWrapper.eq(ObjectUtil.isNotNull(userRole),"userRole",userRole);

        queryWrapper.like(StrUtil.isNotBlank(userName),"userName",userName);

        queryWrapper.like(StrUtil.isNotBlank(userAccount),"userAccount",userAccount);

        queryWrapper.like(StrUtil.isNotBlank(userProfile),"userProfile",userProfile);

        queryWrapper.orderBy(StrUtil.isNotEmpty(sortField),sortOrder.equals("ascend"),sortField);


        return queryWrapper;
    }

    @Override
    public Long addUser(UserAddRequest userAddRequest) {
        ThrowUtils.throwIf(userAddRequest == null, ErrorCode.PARAMS_ERROR);

        String userName = userAddRequest.getUserName();
        String userAccount = userAddRequest.getUserAccount();
        String userAvatar = userAddRequest.getUserAvatar();
        String userProfile = userAddRequest.getUserProfile();

        User user = new User();

        final String DEFAULT_PASSWORD = "QWERTY";

        String encryptedPassword = getEncryptPassword(DEFAULT_PASSWORD);

        user.setUserAccount(userAccount);
        user.setUserName(userName);
        user.setUserPassword(encryptedPassword);
        user.setUserAvatar(userAvatar);
        user.setUserProfile(userProfile);
        user.setUserRole(userAddRequest.getUserRole());

        boolean result = this.save(user);

        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR, "add user failed");

        return user.getId();
    }

    @Override
    public Boolean updateUser(UserUpdateRequest userUpdateRequest) {
        ThrowUtils.throwIf(userUpdateRequest == null || userUpdateRequest.getId() == null, ErrorCode.PARAMS_ERROR);

        User user = new User();
        BeanUtil.copyProperties(userUpdateRequest, user);

        boolean result = this.updateById(user);

        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR, "update failed");

        return result;
    }

    @Override
    public UserVO getUserVOById(long id) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);

        User user = this.getById(id);

        UserVO userVO = getUserVO(user);

        ThrowUtils.throwIf(userVO == null, ErrorCode.NOT_FOUND_ERROR);

        return userVO;
    }

    @Override
    public Page<UserVO> listUserVOByPage(UserQueryRequest userQueryRequest) {
        ThrowUtils.throwIf(userQueryRequest == null, ErrorCode.PARAMS_ERROR);

        int current = userQueryRequest.getCurrent();
        int pageSize = userQueryRequest.getPageSize();

        Page<User> userPage = this.page(new Page<>(current, pageSize), getQueryWrapper(userQueryRequest));

        Page<UserVO> userVOPage = new Page<>(current, pageSize, userPage.getTotal());

        List<UserVO> userListVO = getUserListVO(userPage.getRecords());

        userVOPage.setRecords(userListVO);

        return userVOPage;
    }


}





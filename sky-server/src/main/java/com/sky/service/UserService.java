package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

/**
 * @author NXRUI
 * @version 1.0
 * @date 2023/6/11 15:14
 * @description: TODO
 */
public interface UserService {
    /**
     * 微信登录
     */
    User wxLogin(UserLoginDTO userLoginDTO);
}

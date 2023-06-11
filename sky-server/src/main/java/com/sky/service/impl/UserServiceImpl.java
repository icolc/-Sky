package com.sky.service.impl;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author NXRUI
 * @version 1.0
 * @date 2023/6/11 16:22
 * @description:
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    /**
     * 微信登录
     *
     * @param userLoginDTO
     */
    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
        return null;
    }
}

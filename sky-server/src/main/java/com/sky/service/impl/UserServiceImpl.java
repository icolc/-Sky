package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author NXRUI
 * @version 1.0
 * @date 2023/6/11 16:22
 * @description:
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    //微信服务接口地址
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    private final UserMapper userMapper;
    private final WeChatProperties weChatProperties;

    public UserServiceImpl(WeChatProperties weChatProperties, UserMapper userMapper) {
        this.weChatProperties = weChatProperties;
        this.userMapper = userMapper;
    }

    /**
     * 微信登录
     *
     * @param userLoginDTO
     */
    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
        //调用微信接口服务，用来获取当前微信用户的OpenId
        String openid = getString(userLoginDTO);
        //判断这个id是否为空
        if (Objects.isNull(openid)) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        //不为空，是否是新用户
        User user = userMapper.selectByOpenid(openid);
        if (Objects.isNull(user)) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            //调用mapper的新增方法
            userMapper.insert(user);
        }
        //新用户，存到表

        //不是，返回

        return user;
    }

    /**
     *
     * @param userLoginDTO
     * @return
     */
    private String getString(UserLoginDTO userLoginDTO) {
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", userLoginDTO.getCode());
        map.put("grant_type", "authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN, map);
        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");
        return openid;
    }
}

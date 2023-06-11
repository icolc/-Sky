package com.sky.controller.user;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.utils.JwtUtils;
import com.sky.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author NXRUI
 * @version 1.0
 * @date 2023/6/11 14:57
 * @description:
 */
@RestController
@RequestMapping("/user/user")
@Api(tags = "用户相关接口")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 微信登录接口
     * @param userLoginDTO
     * @return
     */
    @ApiOperation("/微信登录")
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
        log.info("login() called with parameters => 【userLoginDTO = {}】",userLoginDTO);

        User user = userService.wxLogin(userLoginDTO);
        //为用户生产JWT令牌
        HashMap<String, Object> map = new HashMap<>();
        map.put(JwtClaimsConstant.USER_ID,user.getId());
        String token = JwtUtils.generateJwt(map);

        UserLoginVO build = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();

        return Result.success(build);
    }
}

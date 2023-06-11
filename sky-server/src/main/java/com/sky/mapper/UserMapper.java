package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author NXRUI
 * @version 1.0
 * @date 2023/6/11 16:36
 * @description: TODO
 */
@Mapper
public interface UserMapper {
    /**
     * 根据Openid查询用户
     * @param openid
     * @return
     */
    @Select("select * from user where openid = #{openid}")
    User selectByOpenid(String openid);

    void insert(User user);
}

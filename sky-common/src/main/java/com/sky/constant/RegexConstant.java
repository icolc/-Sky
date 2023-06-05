package com.sky.constant;

/**
 * @author NXRUI
 * @version 1.0
 * @date 2023/6/5 17:02
 * @description:
 */
public class RegexConstant {
    /**
     * 手机号正则
     */
    public static final String PHONE_REGEX = "^(?:(?:\\+|00)86)?1(?:(?:3[\\d])|(?:4[5-7|9])|(?:5[0-3|5-9])|(?:6[5-7])|(?:7[0-8])|(?:8[\\d])|(?:9[1|8|9]))\\d{8}$";
    /**
     * 身份证正则
     */
    public static final String ID_NUMBER_REGEX = "(^\\d{8}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}$)|(^\\d{6}(18|19|20)\\d{2}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}(\\d|X|x)$)";

}

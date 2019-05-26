package com.mozhumz.usermanage.enums;

/**
 * @author huyuanjia
 * @date 2019/5/6 20:53
 * 错误码
 */
public enum ErrorCode {
    LOGIN_ERR(10001,"账号或密码错误"),
    PARAM_ERR(10002,"参数错误"),
    ROLE_ERR(10003,"角色错误"),
    USER_ERR(10004,"当前用户错误"),
    PWD_ERR(10005,"密码错误"),
    ;
    public Integer code;
    public String desc;

    ErrorCode(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}

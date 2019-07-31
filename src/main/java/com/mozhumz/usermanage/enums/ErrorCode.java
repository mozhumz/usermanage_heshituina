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
    USER_ERR(10004,"当前用户已经被删除"),
    LOGIN_EXP_ERR(10005,"登录失效，请重新登录"),
    PWD_ERR(10006,"密码错误"),
    SAME_PWD_ERR(10007,"新旧密码不能相同"),
    SYS_EMAIL_NOT_SET_ERR(10008,"系统邮箱未设置，不能发送邮件，请先设置"),
    SEND_EMAIL_ERR(10009,"发送邮件出错:"),
    EMAIL_EXIST_ERR(10010,"该邮箱账号已存在"),
    EMAIL_CODE_ERR(10011,"验证码错误"),

    USER_NOT_EXIST_ERR(20001,"该用户不存在或已经被删除"),
    FREEZE_SELF_ERR(20002,"您不能冻结自己"),
    USERNAME_ERR(20003,"该用户不存在"),
    USER_EMAIL_ERR(20004,"该账户未设置邮箱或与输入的邮箱不匹配"),
    ;
    public Integer code;
    public String desc;

    ErrorCode(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}

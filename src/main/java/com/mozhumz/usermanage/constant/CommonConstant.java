package com.mozhumz.usermanage.constant;

/**
 * @author huyuanjia
 * @date 2019/5/7 9:09
 */
public class CommonConstant {
    public static  final String token="token";

    /**
     * 全局会话 根据token存储在redis
     */
    public static  final String globalSessionUser="globalSessionUser";

    /**
     * 局部会话
     */
    public static  final String sessionUser="sessionUser";

    /**
     * 客户验证码前缀
     */
    public static final String customerCode="customerCode";

    public static final String userCode="userCode";

    public static final long customerCodeSeconds=60*5;

    public static final String[]remoteUrls={"/api/sys/sendEmailCode","/api/user/changePwd"};

    public static final String emailCodeTitle="何氏推拿-验证码";




}

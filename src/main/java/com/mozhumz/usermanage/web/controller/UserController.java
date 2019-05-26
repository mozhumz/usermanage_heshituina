package com.mozhumz.usermanage.web.controller;

import com.hyj.util.param.CheckParamsUtil;
import com.mozhumz.usermanage.model.dto.AddUserDto;
import com.mozhumz.usermanage.model.dto.ChangePwdDto;
import com.mozhumz.usermanage.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.lshaci.framework.web.model.JsonResponse;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Duration;

/**
 * @author huyuanjia
 * @date 2019/4/2 16:55
 */
@RestController
@Api(value = "登录相关接口", description = "登录相关接口")
@RequestMapping("/api/user")
public class UserController {
    @Resource
    private HttpServletRequest request;
    @Resource
    private HttpServletResponse response;

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private IUserService userService;

    @Value("${login.url}")
    private String loginUrl;

    @Value("${session-redis.timeout}")
    private Long sessionSeconds;



    @ApiOperation(value = "退出")
    @RequestMapping(value = "/logOut", method = RequestMethod.POST)
    public JsonResponse<String> logOut() {
        request.getSession().invalidate();
        return JsonResponse.success(null);
    }

    @ApiOperation(value = "添加用户")
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public JsonResponse addUser(@RequestBody AddUserDto addUserDto) {

        return JsonResponse.success(userService.addUser(addUserDto));
    }

    @ApiOperation(value = "修改密码")
    @RequestMapping(value = "/changePwd", method = RequestMethod.POST)
    public JsonResponse<String> changePwd(@RequestBody ChangePwdDto changePwdDto ) {
        userService.changePwd(changePwdDto);
        return JsonResponse.success(null);
    }


}

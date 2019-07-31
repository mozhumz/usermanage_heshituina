package com.mozhumz.usermanage.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyj.util.exception.BaseException;
import com.hyj.util.web.JsonResponse;
import com.mozhumz.usermanage.enums.ErrorCode;
import com.mozhumz.usermanage.model.dto.*;
import com.mozhumz.usermanage.model.entity.Role;
import com.mozhumz.usermanage.model.entity.User;
import com.mozhumz.usermanage.model.entity.UserRole;
import com.mozhumz.usermanage.model.qo.UserQo;
import com.mozhumz.usermanage.model.vo.UserVO;
import com.mozhumz.usermanage.service.IRoleService;
import com.mozhumz.usermanage.service.IUserRoleService;
import com.mozhumz.usermanage.service.IUserService;
import com.mozhumz.usermanage.utils.SessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author huyuanjia
 * @date 2019/4/2 16:55
 */
@RestController
@Api(value = "登录相关接口", description = "登录相关接口")
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    @Resource
    private HttpServletRequest request;
    @Resource
    private HttpServletResponse response;

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private IUserService userService;
    @Resource
    private IRoleService roleService;
    @Resource
    private IUserRoleService userRoleService;

    @ApiOperation(value = "获取登录用户")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public JsonResponse login() {
        SessionUser sessionUser=SessionUtil.getLoginUser();
        log.info(sessionUser.toString());
        return JsonResponse.success(sessionUser);
    }


    @ApiOperation(value = "退出")
    @RequestMapping(value = "/logOut", method = RequestMethod.GET)
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

    @ApiOperation(value = "重置密码")
    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    public JsonResponse<String> resetPwd(@RequestBody ResetPwdDto resetPwdDto ) {
        userService.resetPwd(resetPwdDto);
        return JsonResponse.success(null);
    }


    @ApiOperation(value = "获取用户列表")
    @RequestMapping(value = "/getUserList", method = RequestMethod.POST)
    public JsonResponse getUserList(@RequestBody UserQo userQo) {

        return userService.getUserList(userQo);
    }

    @ApiOperation(value = "获取用户列表-不分页")
    @RequestMapping(value = "/getUserList2", method = RequestMethod.POST)
    public JsonResponse getUserList2(@RequestBody UserQo userQo) {

        return userService.getUserList2(userQo);
    }

    @ApiOperation(value = "获取用户详情")
    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    public JsonResponse getUser(@RequestBody UserQo userQo) {
        return userService.getOne(userQo);
    }

    @ApiOperation(value = "获取所有角色")
    @RequestMapping(value = "/getRoleList", method = RequestMethod.POST)
    public JsonResponse getRoleList() {
        return JsonResponse.success(roleService.list());
    }

    @ApiOperation(value = "修改用户信息")
    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public JsonResponse saveUser(@RequestBody SaveUserDto saveUserDto) {
        return userService.saveUser(saveUserDto);
    }



}

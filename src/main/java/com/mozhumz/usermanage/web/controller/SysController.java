package com.mozhumz.usermanage.web.controller;

import com.hyj.util.web.JsonResponse;
import com.mozhumz.usermanage.constant.CommonConstant;
import com.mozhumz.usermanage.model.dto.SendEmailDto;
import com.mozhumz.usermanage.model.entity.SysEmail;
import com.mozhumz.usermanage.model.qo.EmailQo;
import com.mozhumz.usermanage.service.ISysEmailService;
import com.mozhumz.usermanage.utils.EmailUtil;
import com.mozhumz.usermanage.utils.SessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author huyuanjia
 * @date 2019/4/2 16:55
 */
@RestController
    @Api(value = "系统相关接口", description = "系统相关接口")
@RequestMapping("/api/sys")
public class SysController {
    @Resource
    private HttpServletRequest request;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private ISysEmailService sysEmailService;



    @ApiOperation(value = "添加系统邮箱")
    @RequestMapping(value = "/addEmail", method = RequestMethod.POST)
    public JsonResponse addEmail(@RequestBody SysEmail sysEmail) {
        return  sysEmailService.addEmail(sysEmail);
    }

    @ApiOperation(value = "修改系统邮箱")
    @RequestMapping(value = "/updateEmail", method = RequestMethod.POST)
    public JsonResponse updateEmail(@RequestBody SysEmail sysEmail) {
        return  sysEmailService.updateEmail(sysEmail);
    }

    @ApiOperation(value = "获取系统邮箱列表")
    @RequestMapping(value = "/getEmails", method = RequestMethod.POST)
    public JsonResponse getEmails(@RequestBody EmailQo emailQo) {
        return  sysEmailService.getList(emailQo);
    }

    @ApiOperation(value = "发送邮箱验证码-远程调用")
    @RequestMapping(value = "/sendEmailCode", method = RequestMethod.POST)
    public JsonResponse sendEmailCode(@RequestBody SendEmailDto sendEmailDto) {
        EmailUtil.sendEmail(sendEmailDto);
        return  JsonResponse.success(null);
    }

    @ApiOperation(value = "发送邮箱验证码-本地调用")
    @RequestMapping(value = "/sendCode", method = RequestMethod.POST)
    public JsonResponse sendCode(@RequestBody SendEmailDto sendEmailDto) {
        sendEmailDto.setKey(CommonConstant.userCode+ SessionUtil.getLoginUser().getUsername());
        sendEmailDto.setTitle(CommonConstant.emailCodeTitle);
        sendEmailDto.setContent(EmailUtil.getEmailCode(6));

        EmailUtil.sendEmail(sendEmailDto);
        return  JsonResponse.success(null);
    }


}

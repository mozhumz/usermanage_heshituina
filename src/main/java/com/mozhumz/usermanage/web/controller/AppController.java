package com.mozhumz.usermanage.web.controller;

import com.mozhumz.usermanage.model.qo.AppQo;
import com.mozhumz.usermanage.service.IAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.lshaci.framework.web.model.JsonResponse;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author huyuanjia
 * @date 2019/4/2 16:55
 */
@RestController
@Api(value = "测试相关接口", description = "测试相关接口")
@RequestMapping("/api/app")
public class AppController {
    @Resource
    private HttpServletRequest request;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private IAppService appService;



    @ApiOperation(value = "获取web应用列表")
    @RequestMapping(value = "/getAppList", method = RequestMethod.POST)
    public JsonResponse getAppList(@RequestBody AppQo appQo) {

        return JsonResponse.success(appService.getAppList(appQo));

    }


}

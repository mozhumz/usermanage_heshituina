package com.mozhumz.usermanage.web.controller;

import com.hyj.util.exception.BaseException;
import com.hyj.util.param.CheckParamsUtil;
import com.hyj.util.web.JsonResponse;
import com.mozhumz.usermanage.enums.ErrorCode;
import com.mozhumz.usermanage.model.entity.App;
import com.mozhumz.usermanage.model.qo.AppQo;
import com.mozhumz.usermanage.service.IAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
    private RedisTemplate redisTemplate;
    @Resource
    private IAppService appService;



    @ApiOperation(value = "获取web应用列表")
    @RequestMapping(value = "/getAppList", method = RequestMethod.POST)
    public JsonResponse getAppList(@RequestBody AppQo appQo) {

        return appService.getAppList(appQo);
    }

    @ApiOperation(value = "修改应用")
    @RequestMapping(value = "/updateApp", method = RequestMethod.POST)
    public JsonResponse updateApp(@RequestBody App app) {
        if(!CheckParamsUtil.check(app.getName(),app.getUrl(),app.getId()+"")){
            throw new BaseException(ErrorCode.PARAM_ERR.desc);
        }
        app.setUpdateDate(new Date());
        return JsonResponse.success(appService.updateById(app));
    }


}

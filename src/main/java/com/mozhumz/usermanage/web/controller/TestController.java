package com.mozhumz.usermanage.web.controller;

import com.hyj.util.web.JsonResponse;
import com.mozhumz.usermanage.service.ITestService;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.StringRedisTemplate;
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
@Api(value = "测试相关接口", description = "测试相关接口")
@RequestMapping("/api/test")
public class TestController {
    @Resource
    private HttpServletRequest request;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private ITestService testService;



    @ApiOperation(value = "测试")
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public JsonResponse  test() {
        stringRedisTemplate.opsForValue().set("testKey","val");
        request.getSession().setAttribute("haha","hyj");
        return JsonResponse .success(null);

    }

    @ApiOperation(value = "测试添加")
    @RequestMapping(value = "/addManageTest", method = RequestMethod.GET)
    @GlobalTransactional
    public JsonResponse  addManageTest() {
        return testService.add();

    }


}

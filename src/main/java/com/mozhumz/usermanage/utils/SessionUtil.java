package com.mozhumz.usermanage.utils;

import com.alibaba.fastjson.JSON;
import com.google.gson.*;
import com.hyj.util.common.CommonUtil;
import com.mozhumz.usermanage.constant.CommonConstant;
import com.mozhumz.usermanage.model.dto.SessionUser;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.lshaci.framework.web.exception.LoginException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

/**
 * @author huyuanjia
 * @date 2019/5/6 20:37
 */
@Component
@Slf4j
public class SessionUtil {
    public static RedisTemplate<String,String> redisTemplate;
    public static Gson gson = getGson();


    public static Gson getGson() {
        GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
        return builder.create();
    }

    @Resource
    public void setRedisTemplate(RedisTemplate<String,String> redisTemplate){
        SessionUtil.redisTemplate=redisTemplate;
    }

    /**
     * 获取session
     * @return
     */
    public static HttpSession getSession() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return requestAttributes.getRequest().getSession();
    }

    /**
     * 获取登录用户
     * @return
     */
    public static SessionUser getLoginUser(){
        String token= (String) getSession().getAttribute(CommonConstant.token);
        if(token==null){
            throw new LoginException();
        }
        String json= (String) redisTemplate.opsForValue().get(CommonConstant.globalSessionUser+token);
        SessionUser userDto= JSON.parseObject(json,SessionUser.class);
        return userDto;
    }

}

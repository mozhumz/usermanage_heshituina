package com.mozhumz.usermanage.web.filter;

import com.hyj.util.param.CheckParamsUtil;
import com.hyj.util.str.StrUtil;
import com.mozhumz.usermanage.constant.CommonConstant;
import com.mozhumz.usermanage.feign.ZuulResult;
import com.mozhumz.usermanage.feign.entity.dto.CheckTokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author huyuanjia
 * @date 2019/5/8 10:51
 * 登录拦截
 */
@Component
public class SSOFilter implements Filter {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private ZuulResult zuulResult;
    @Value("${urlPre.web}")
    private String webUrl;

    @Value("${urlPre.sso}")
    private String ssoUrl;

    @Value("${urlPre.webOut}")
    private String webOut;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        //远程接口放行
        for(String url:CommonConstant.remoteUrls){
            if(request.getRequestURL().toString().contains(url)){
                filterChain.doFilter(request,response);
                return;
            }
        }
        //判断是否有局部会话
        HttpSession session=request.getSession();
        String token= (String) session.getAttribute(CommonConstant.usermanageToken);
        if(CheckParamsUtil.check(token)){
            filterChain.doFilter(request,response);
            return;
        }

        //判断地址栏中是否有token
        String tokenStr=request.getParameter(CommonConstant.token);
        if(CheckParamsUtil.check(tokenStr)){
            //校验token
            CheckTokenDto checkTokenDto=new CheckTokenDto();
            checkTokenDto.setToken(tokenStr);
            checkTokenDto.setOutUrl(webOut);
            checkTokenDto.setSessionId(getSessionCode(request.getHeader("Cookie")));
            if(zuulResult.checkToken(checkTokenDto)){
                //设置session
                request.getSession().setAttribute(CommonConstant.usermanageToken,tokenStr);
                filterChain.doFilter(request,response);
                return;
            }
        }
        //重定向到认证中心
        response.sendRedirect(ssoUrl+"?webUrl="+webUrl);

    }

    public static String getSessionCode(String cookie){
        if(CheckParamsUtil.check(cookie)){
            String []arr1=cookie.split(";");
            for(String s:arr1){
                if(CheckParamsUtil.check(s)){
                    s= StrUtil.trim(s);
                    if(s.startsWith("SESSION="))
                        return s;
                }
            }
        }
        return null;
    }
}

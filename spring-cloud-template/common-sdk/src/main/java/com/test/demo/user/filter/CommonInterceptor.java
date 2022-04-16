package com.test.demo.user.filter;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.test.demo.user.enums.MessageEnum;
import com.test.demo.user.util.JWTUtil;
import com.test.demo.user.util.PathUtil;
import com.test.demo.user.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
public class CommonInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(CommonInterceptor.class);

    @Autowired
    private BasicsConfig basicsConfig;
    @Autowired
    private FilterUrlConfig filterUrlConfig;

//    @Autowired
//    private IRedisService redisService;

    /**
     * 在请求被处理之前调用
     * 
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        String url = request.getRequestURI();
//        System.out.println("进入拦截器，当前url:"+url);
//        redisService.set("test","66666666666666666");
//        System.out.println("redis测试--------"+redisService.get("test"));
        boolean checkurl = PathUtil.pathMatcher(url, filterUrlConfig.getUnauthorizationUrl());
        if (!checkurl) {
            return true;
        }
        String token = request.getHeader("Authorization");
        if (!StringUtils.isEmpty(token)) {
            boolean exists = JWTUtil.checkToken(token, basicsConfig.getSecurityKey());
            if (exists) {
                return true;
            }
        }
        // 跨域ajax请求，先发送一个OPTIONS请求，确认是可以请求的之后才发送get或post,第一次OPTIONS预请求，headers是不会带过来的，需要在过滤器中单独处理下；
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        output("", "", response);
        return false;
    }

    /**
     * 在请求被处理后，视图渲染之前调用
     * 
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在整个请求结束后调用
     * 
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {

    }

    /**
     * 认证不通过时，返回的信息
     * 
     * @param code
     * @param response
     * @param msg
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    private <T> void output(String code, String msg, HttpServletResponse response)
        throws IOException, UnsupportedEncodingException {
        response.setContentType("application/json;charset=UTF-8");
        try {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(JSON.toJSONString(ResponseUtil.errorSys401(MessageEnum.INVALID)));
        } catch (IOException e) {
            logger.error("" + e);
        }
    }

}

package com.champion.blog.interceptor;

import com.champion.blog.dto.Types;
import com.champion.blog.utils.IPKit;
import com.champion.blog.utils.MapCache;
import com.champion.blog.utils.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 */
@Component
public class BaseInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(BaseInterceptor.class);
    private static final String USER_AGENT = "user-agent";

    private MapCache cache = MapCache.single();


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String contextPath = request.getContextPath();
        // System.out.println(contextPath);
        String uri = request.getRequestURI();

        LOG.info("UserAgent: {}", request.getHeader(USER_AGENT));
        LOG.info("用户访问地址: {}, 来路地址: {}", uri, IPKit.getIpAddrByRequest(request));


        //设置get请求的token
        if (request.getMethod().equals("GET")) {
            String csrf_token = UUID.UU64();
            // 默认存储30分钟
            cache.hset(Types.CSRF_TOKEN.getType(), csrf_token, uri, 30 * 60);
            request.setAttribute("_csrf_token", csrf_token);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}

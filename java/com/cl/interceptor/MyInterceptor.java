package com.cl.interceptor;


import com.cl.model.Vo.UserVo;
import com.cl.utils.commonTools.IpTool;
import com.cl.utils.commonTools.LoginTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截后台若干访问
 * 在登录成功时放入session，以后就通过此拦截器根据url判断
 */
@Component
public class MyInterceptor implements HandlerInterceptor {
    private static final Logger interceptor_logger= LoggerFactory.getLogger("interceptor_logger");
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri=request.getRequestURI();
        interceptor_logger.info("用户访问地址: {}, 来路地址: {}", uri, IpTool.getIpAddrByRequest(request));
        UserVo user=LoginTool.getLoginUser(request);
        //不拦截登录login和到登录页面的toLogin，2种路径因为项目名可以配置去掉
        if(user==null)
        	if((uri.startsWith("/admin")||uri.startsWith("/myBlog/admin"))&&!uri.startsWith("/admin/login")&&!uri.startsWith("/admin/toLogin")&&!uri.startsWith("/myBlog/admin/login")&&!uri.startsWith("/myBlog/admin/toLogin")){
            response.sendRedirect(request.getContextPath()+"/admin/toLogin");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}

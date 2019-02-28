package com.cl.utils.commonTools;

import com.cl.constant.BlogConstant;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieTool {
    /**
     * 本来应该根据用户id设置Cookie，不过个人博客只有自己后台需要登录
     * 现在设置为根据用户ip设置cookie，用于浏览量的设置（2小时内访问算一次访问）
     *访问一篇文章设置一个cookie，cookie名为自定义常量+文章id，值为ip
     * @param response
     * @param uip
     */
    public static void setCookie(HttpServletResponse response, String uip, long articleId) {
        try {
            String ensIp = EncodeTool.enAes(uip, BlogConstant.AES_SALT);
            Cookie cookie = new Cookie(BlogConstant.USER_COOKIE+articleId, ensIp);
            cookie.setPath("/");
            cookie.setMaxAge(BlogConstant.VIEW_NUM_INCRE_AFTER);
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取用户请求中的用户id
     *
     * @param request
     * @return
     */
    public static String getCookieIp(HttpServletRequest request,long articleId) {
        Cookie cookie = getCookie(BlogConstant.USER_COOKIE+articleId, request);
        if (cookie != null && cookie.getValue() != null) {
            try {
                String uip = EncodeTool.deAes(cookie.getValue(), BlogConstant.AES_SALT);
                return uip;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static Cookie getCookie(String name, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies)
                if (cookie.getName().equals(name))
                    return cookie;
        }
        return null;
    }
}

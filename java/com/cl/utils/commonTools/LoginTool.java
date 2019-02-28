package com.cl.utils.commonTools;

import com.cl.constant.BlogConstant;
import com.cl.model.Vo.UserVo;

import javax.servlet.http.HttpServletRequest;

public class LoginTool {
    public static UserVo getLoginUser(HttpServletRequest request) {
        return (UserVo) request.getSession().getAttribute(BlogConstant.LOGIN_SESSION_KEY);
    }
}

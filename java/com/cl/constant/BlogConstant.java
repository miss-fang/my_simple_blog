package com.cl.constant;


import org.springframework.stereotype.Component;

@Component
public class BlogConstant {

	public static final String LOGIN_SESSION_KEY = "loginUser";
	public static final String USER_COOKIE = "userInCookie";
//	public static final String WEB_ROOT = "http://localhost:8080/";
	public static final String WEB_ROOT = "http://clmissfang.cn:8080/myBlog/";
	// 部署阿里云没有盘符
	//public static final String UPLOAD_ROOT = "D:/webPic/";
	public static final String UPLOAD_ROOT = "/usr/webPic/";
	/**
	 * 随机图片数，共54张图片用于博客
	 */
	public static final int RANDOM_PIC_NUM = 54;

	/**
	 * aes加密加盐，必须<b>16位</b>自定义随机字符串
	 */
	public static String AES_SALT = "`331211kfinrk_si";
	/**
	 * 2小时内浏览算一次，使用cookie
	 */
	public static Integer VIEW_NUM_INCRE_AFTER = 7200;
	/**
	 * 后台登录后session保存半小时
	 */
	public static Integer SESSION_TIME = 1800;
}

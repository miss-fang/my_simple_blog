package com.cl.configs;

import com.cl.constant.BlogConstant;
import com.cl.interceptor.MyInterceptor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.ErrorPageFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {
	@Autowired
	private MyInterceptor myInterceptor;

	@Override
	/**
	 * 指定静态资源访问路径 文件上传到D盘，可以通过虚拟路径访问而不需要重新build
	 * 访问http://localhost:8080/upload/222.jpg即可以访问D:/webPic/222.jpg
	 */
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 阿里云去除盘符
		registry.addResourceHandler("/upload/**").addResourceLocations("file:" + BlogConstant.UPLOAD_ROOT);
		super.addResourceHandlers(registry);
	}

	/**
	 * 添加自定义url拦截器
	 *
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(myInterceptor);
		super.addInterceptors(registry);
	}
	/**
	 * 
	 * @return
	 */
	@Bean    
	 public ErrorPageFilter errorPageFilter() {   
	      return new ErrorPageFilter();   
	 }    
	@Bean
	public FilterRegistrationBean disableSpringBootErrorFilter(ErrorPageFilter filter) {        
	      FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();           
	      filterRegistrationBean.setFilter(filter);      
	      filterRegistrationBean.setEnabled(false);      
	      return filterRegistrationBean;  
	}
}

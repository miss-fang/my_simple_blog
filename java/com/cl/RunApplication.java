package com.cl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Profile("default")
// 开启事务支持，然后使用@Transactional即可
// 使用jpa自动使用JpaTransactionManager事务管理器
@EnableTransactionManagement
public class RunApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(RunApplication.class, args);
	}

	/**
	 * 使用第三方tomcat需要继承SpringBootServletInitializer重写configure
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(RunApplication.class);
	}

}

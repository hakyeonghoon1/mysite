package com.douzone.mysite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.douzone.config.web.FileUploadConfig;
import com.douzone.config.web.MessageConfig;
import com.douzone.config.web.MvcConfig;
import com.douzone.config.web.SecurityConfig;
import com.douzone.mysite.site.TitleInterceptor;

@Configuration		// 꼭 붙어야함
@EnableAspectJAutoProxy
@ComponentScan({"com.douzone.mysite.controller","com.douzone.mysite.exception"})		//scan
@Import({MvcConfig.class, MessageConfig.class, FileUploadConfig.class, SecurityConfig.class})
public class WebConfig extends WebMvcConfigurerAdapter{

	// titleInterceptor
	@Bean
	public HandlerInterceptor titleInterceptor() {
		return new TitleInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(titleInterceptor()).addPathPatterns("/**");
	}
}

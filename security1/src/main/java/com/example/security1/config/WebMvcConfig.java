package com.example.security1.config;

import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		//머스테치 재설정하기
		MustacheViewResolver resolver = new MustacheViewResolver();
		
		resolver.setCharset("UTF-8");
		resolver.setContentType("text/html; charset=UTF-8");
		resolver.setPrefix("classpath:/templates/"); //classpath: -> 프로젝트
		resolver.setSuffix(".html"); // .html을 만들어도 머스테치가 인식
		//만들어낸 뷰리졸버 등록해주기
		
		registry.viewResolver(resolver);
	}

}

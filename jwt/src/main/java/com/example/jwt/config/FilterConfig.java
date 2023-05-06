package com.example.jwt.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.jwt.filter.MyFilter1;

@Configuration
public class FilterConfig {

	//굳이 시큐리티 필터 체인에 걸필요 없이 따로 만들면 됨
	//요청(Request)가 오면 작동함
	//시큐리티 필터 체인이 가장 먼저 작동함 

	@Bean
	public FilterRegistrationBean<MyFilter1> filter1() {
		FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<MyFilter1>(new MyFilter1());
		bean.addUrlPatterns("/*");
		bean.setOrder(0); // 낮은 번호가 필터중에서 가장 먼저 실행됨
		return bean;
	}
}

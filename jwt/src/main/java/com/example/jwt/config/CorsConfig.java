package com.example.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration // 스프링에서 관리 할 수 있게
public class CorsConfig {

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		// ajax, fetch 등 data를 요청할 때 응답을 자바스크립트가 받을 수 있을지 지정 - false면 못 받음
		config.setAllowCredentials(true); // 내서버가 응답을 할 때 json을 자바스크립트에서 처리할 수 있게 할지 설정
		config.addAllowedOrigin("*"); // 모든 ip에 응답을 허용
		config.addAllowedHeader("*"); // 모든 header에 응답을 허용
		config.addAllowedMethod("*"); // 모든 post, get, put, delete, patch 요청을 허용
		source.registerCorsConfiguration("/api/**", config); // 소스에 등록하기
		return new CorsFilter(source);
		//필어테 등록을 해줘야 의미가 있음
	}
	
}

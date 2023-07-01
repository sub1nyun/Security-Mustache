package com.sby.second.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
	
	// 스프링 프레임워크가 내장하고 있는 Filter
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		// 내 서버가 응답 시 -> json을 자바스크립트에서 처리할 수 있도록 설정
		// ex) ajax, fetch, axios -> 데이터를 자바스크립가 받을 수 있는지 설정 -> false라면 js에서 받을 수 없음
		config.setAllowCredentials(true); 
		// 모든 ip에 응답을 허용
		config.addAllowedOrigin("*");
		// 모든 header에 응답을 허용
		config.addAllowedHeader("*");
		// 모든 post, get, put, delete ,patch 요청을 허용하겠다
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/api/**", config);
		return new CorsFilter(source);
	}
	// 필터에 등록해야 의미가 있음

}

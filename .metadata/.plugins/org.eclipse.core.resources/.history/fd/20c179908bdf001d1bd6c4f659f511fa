package com.example.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 메모리에 띄움
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/user/**").authenticated() //user~ 로 들어오면 인증이 필요함
			// .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or
			// hasRole('ROLE_USER')")
			// .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and
			// hasRole('ROLE_USER')")
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") //인증뿐만 아니라 도메인도 확인
			.antMatchers("/manager/**").access("hasRole('ROLE_MANAGER')")
			.anyRequest().permitAll() //다른 주소는 권한 허용
			.and()
			.formLogin()
			.loginPage("/login");
		/*
			.loginProcessingUrl("loginProc")
			.defaultSuccessUrl("/")
			.and()
			.oauth2Login()
			.loginPage("/login")
			.userInfoEndpoint()
			.userService(null);
			*/
		return http.build();
		
	}
	
}

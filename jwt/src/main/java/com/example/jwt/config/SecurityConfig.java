package com.example.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

import com.example.jwt.filter.MyFilter1;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity // 시큐리티 활성화
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final CorsFilter corsFilter;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//http.addFilterBefore(new MyFilter1(), BasicAuthenticationFilter.class); // 이렇게만 하면 오류발생 -> 시큐리티 필터 전과 후로 걸어야함
		// ▲ 굳이 사용하지 않는 방법 -> 직접 필터를 하나 만듬 -> FilterConfig
		//시큐리티 필터 체인이 가장 먼저 작동함 -> 내가 만든 필터가 먼저 작동하게 하고싶다면 addFilterBefore()에 등록
		// SecurityContextPersistenceFilter-> 가장 먼저 실행되는 필터
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 미사용
		.and()
		.addFilter(corsFilter) // @CrossOrigin(인증x), 시큐리티 필터에 등록 인증(O)
		.formLogin().disable() // jwt 서버 -> id pw 폼으로 생각 안 함 (form 로그인 사용 안 함)
		.httpBasic().disable()
		.authorizeRequests()
		.antMatchers("/api/v1/user/**")
		.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
		.antMatchers("/api/v1/manager/**")
		.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
		.antMatchers("/api/v1/admin/**")
		.access("hasRole('ROLE_ADMIN')")
		.anyRequest().permitAll();
		return http.build();	
	}

}

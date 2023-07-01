package com.sby.second.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import com.sby.second.filter.MyFilter1;

import lombok.RequiredArgsConstructor;

@Configuration // 스프링 구성 요소로 등록
@EnableWebSecurity // 시큐리티 활성화
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final CorsFilter corsFilter;
	
	// 컨트롤러에서 사용하는 @CrossOrigin같은 경우는 시큐리티와 같이 인증이 필요한 요청은 거부 -> 인증이 필요하지 않는 부분만 사용하기
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//http.addFilter(new MyFilter1()); 오류 -> 시큐리티 필터만 등록 할 수 있는데 Filter 타입이기 때문에 등록이 불가
		// addFilterBefore or addFilterAfter 를 사용해서 시큐리티 필터 시작 전과 후를 정하여 걸어야함
		// ▼ BasicAuthenticationFilter가 동작하기전에 해당 MyFilter1이 동작한다는 의미
		// 굳이 시큐리티 필터에는 걸어서 사용하지 않음
		//http.addFilterBefore(new MyFilter1(), BasicAuthenticationFilter.class);
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 미사용
		.and()
		// 모든 요청이 해당 필터를 타기때문에 해당 서버가 CORS 정책에서 벗어 날 수 있음
		// @CrossOrigin(인증 X), 시큐리티 필터에 등록 인증(O)
		.addFilter(corsFilter) // CorsConfig에서 Bean으로 등록 했기때문에 바로 주입함
		.formLogin().disable() // jwt 서버 -> id pw 폼으로 생각 안 함 (form 로그인 사용 안 함)
		// 
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

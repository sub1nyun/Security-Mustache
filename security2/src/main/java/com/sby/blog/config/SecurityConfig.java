package com.sby.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 스프링의 구성 요소로 인식
@EnableWebSecurity // 스프링 시큐리티 보안 활성화 -> 스프링 필터체인에 등록
public class SecurityConfig {
	
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();	
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http)  throws Exception {
		http.csrf().disable(); // CSRF 보호 기능 비활성화
		http.authorizeRequests() // 시큐리티에서 HTTP 요청에 대한 인가 규칙을 설정을 시작하는 메서드
			.antMatchers("/user/**").authenticated() //user~ 로 들어오면 인증이 필요함, 인증만 되면 들어갈 수 있는 주소
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") //"/admin/"으로 시작하는 모든 URL에 대해 
			//"ROLE_ADMIN" 권한을 가진 사용자만 접근
			.antMatchers("/manager/**").access("hasRole('ROLE_MANAGER')")
			.anyRequest().permitAll() //다른 주소는 권한 허용
			// 권한이 없는 페이지를 요청했을때 로그인 페이지로 이동되게 하기
			.and()
			.formLogin()
			.loginPage("/loginForm") // 해당 url로 던져줌
			//.usernameParameter("username2") 파라메터 이름을 바꾸고 싶다면 사용
			.loginProcessingUrl("/loginProc") // /loginProc 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행
			.defaultSuccessUrl("/");
		return http.build();
		
	}

}

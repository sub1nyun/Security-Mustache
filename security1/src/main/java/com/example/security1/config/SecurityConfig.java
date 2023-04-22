package com.example.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 메모리에 띄움
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화, preAuthorize, postAutorize 어노테이션 활성화
public class SecurityConfig {
	
	@Bean // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해줌
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();	
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/user/**").authenticated() //user~ 로 들어오면 인증이 필요함, 인증만 되면 들어갈 수 있는 주소
			// .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or
			// hasRole('ROLE_USER')")
			// .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and
			// hasRole('ROLE_USER')")
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") //인증뿐만 아니라 도메인도 확인
			.antMatchers("/manager/**").access("hasRole('ROLE_MANAGER')")
			.anyRequest().permitAll() //다른 주소는 권한 허용
			.and()
			.formLogin()
			.loginPage("/loginForm")
			//.usernameParameter("username2") 파라메터 이름을 바꾸고 싶다면 사용
			.loginProcessingUrl("/loginProc") // /loginProc 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행
			.defaultSuccessUrl("/");
		/*
			
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

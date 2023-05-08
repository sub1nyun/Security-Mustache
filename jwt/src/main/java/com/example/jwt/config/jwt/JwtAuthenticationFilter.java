package com.example.jwt.config.jwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter가 존재함
// /login을 요청하면 username, password 전송하면 (post)
// UsernamePasswordAuthenticationFilter 동작 
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	// formLogin을 비활성화 했기때문에 작동을 안 함 -> 시큐리티컨피그에 직접 등록하기
	
	private final AuthenticationManager authenticationManager;

	//  /login 욫청을 하면 로그인 시도를 위해서 실행되는 함수
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("JwtAuthenticationFilter 동작");
		
		// 1. username과 password를 받아서
		// 2. 정상인지 로그인 시도를 해봄 authenticationManager 로그인 시도를 하면 
		//PrinccipalDetailsService가 호출 loadUserByUsername() 메서드 실행
		// 3. PrinciaplDetails를 세션에 담고 (권한 관리를 위해서)
		// 4. JWT토큰을 만들어서 응답해주기
		return super.attemptAuthentication(request, response);
	}
	
}

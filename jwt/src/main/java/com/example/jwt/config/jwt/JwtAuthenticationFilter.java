package com.example.jwt.config.jwt;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jwt.config.auth.PrincipalDetail;
import com.example.jwt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

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
		try {
			/*
			BufferedReader br = request.getReader();
			String input = null;
			while((input = br.readLine()) != null) {
				System.out.println(input);
			*/
			// JSON 데이터 파싱
			ObjectMapper om = new ObjectMapper();
			User user = om.readValue(request.getInputStream(), User.class);
			System.out.println(user);	
			
			//로그인 시도를 위한 토큰 강제로 만들어보기
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
			
			// PrincipalDetailsService의 loadUserByUsername() 함수가 실행됨 -> 정상이면
			// authentication이 리턴됨 
			// DB에 있는 username과 password가 일치한다는 것
			Authentication authentication = 
					authenticationManager.authenticate(authenticationToken);
			
			// authentication 객체가 session 영역에 저장됨 => 로그인이 되었다는 것 
			// Object를 리턴하기 때문에 다운캐스팅 
			PrincipalDetail principalDetail = (PrincipalDetail) authentication.getPrincipal();
			System.out.println("로그인 완료됨  : " + principalDetail.getUser().getUsername());
			// ▲ 로그인이 정상적으로 되었다는 것
			// 리턴 시 authentication 객체가 session 영역에 저장을 해야하고 그 방법이 return 해주면 됨
			// 리턴의 이유는 권환 관리를 security가 대신 해주기 때문에 편하기에 
			// 굳이 JWT 토큰을 사용하면서 세션을 만들 이유가 없음 -> 단지 권한 처리때문에 session에 넣어주는것
			
			// JWT 토큰을 만들기 
			return authentication;
		} catch (IOException e) {
			e.printStackTrace();
		}	
		// 2. 정상인지 로그인 시도를 해봄 authenticationManager 로그인 시도를 하면 
		//PrinccipalDetailsService가 호출 loadUserByUsername() 메서드 실행
		// 3. PrinciaplDetails를 세션에 담고 (권한 관리를 위해서)
		// 4. JWT토큰을 만들어서 응답해주기
		return null;
	}
	
	// attemptAuthentication실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행 되는 구조
	// JWT 토큰을 만들어서 request 요청한 사용자에게 JWT토큰을 응답해주면 됨 
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("인증이 완료되었다 이말이야");
		
		PrincipalDetail principalDetail = (PrincipalDetail) authResult.getPrincipal();
		
		// RSA 방식이 아님 => Hash 암호 방식
		String jwtToken = JWT.create() // 기본적으로 빌더 패턴
				.withSubject(principalDetail.getUsername()) // 토큰 이름
				// 토큰의 유효 시간 ▼
				.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
				.withClaim("id", principalDetail.getUser().getId()) // 넣고 싶은 key랑 value 아무거나 지정 가능
				.withClaim("username", principalDetail.getUser().getUsername())
				.sign(Algorithm.HMAC512(JwtProperties.SECRET)); // 서버만 아는 고유한 값 
		
			response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken);
	}
}

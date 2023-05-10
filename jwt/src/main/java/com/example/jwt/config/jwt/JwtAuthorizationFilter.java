package com.example.jwt.config.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jwt.config.auth.PrincipalDetail;
import com.example.jwt.model.User;
import com.example.jwt.repository.UserRepository;

// 로그인 요청 -> JWT 토큰을 받음(클라이언트) -> 클라이언트가 개인정보에 접근을 위해 다시 로그인을 요청하는것이 아닌
// JWT 토큰을 이용해서 해당 개인정보에 접근 -> 전자서명을 통해서 개인정보에 접근 하는 방식

// 인가
// 시큐리티가 filter를 가지고 있는데 그 중, BasicAuthenticationFilter 라는 것이 존재함
// 권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어 있는 구조
// 만약에 권한이나 인증이 필요한 주소가 아니라면 해당 필터를 타지 않음

public class JwtAuthorizationFilter extends BasicAuthenticationFilter{
	
	private UserRepository userRepository;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
		super(authenticationManager);
		System.out.println("인증이나 권한이 필요한 주소 요청이 들어옴");
		this.userRepository = userRepository;
	}
	
	// 인증이나 권한이 필요한 주소요청이 있을 때 해당 필터를 타게 됨
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// supser.~ 지워야함 -> 응답을 2번 해버림
		// 헤더가 존재하는지 확인
		String header = request.getHeader(JwtProperties.HEADER_STRING);
		
		if(header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
            return;
		}
		
		// JWT 토큰을 검증해서 정상적인 사용자인지 확인
		System.out.println("header : "+header);
		
		String token = request.getHeader(JwtProperties.HEADER_STRING)
				.replace(JwtProperties.TOKEN_PREFIX, "");
		
		// 토큰 검증 (이게 인증이기 때문에 AuthenticationManager도 필요 없음)
		// 내가 SecurityContext에 집적접근해서 세션을 만들때 자동으로 UserDetailsService에 있는 loadByUsername이 호출됨.
		String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
				.getClaim("username").asString();
		
		if(username != null) {	// 정상적으로 서명이 됐다는것
			User user = userRepository.findByUsername(username);
			
			// 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해 
			// 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장!
			PrincipalDetail principalDetails = new PrincipalDetail(user);
			// 로그인 진행아 아닌 강제로 만들기 
			// Jwt 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어준다
			Authentication authentication =
					new UsernamePasswordAuthenticationToken(
							principalDetails, //나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
							null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
							principalDetails.getAuthorities());
			
			// 강제로 시큐리티의 세션에 접근하여 값 저장
			// SecurityContextHolder.getContext() -> 시큐리티 세션 공간 
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		// 세션 등록 -> 로그인이 됐기때문에 다시 필터를 타게 해주면 됨
		chain.doFilter(request, response);
	}
	
}
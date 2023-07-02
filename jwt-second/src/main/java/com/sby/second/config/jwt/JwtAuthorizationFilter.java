package com.sby.second.config.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

// 해당 필터는 인증 요청이 있을때가 아님
// 시큐리티가 filter를 가지고 있는데 그 필터중에 BasicAuthenticationFilter 라는 것이 존재함
// 권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어있는 구조
// 만약 권한이나 인증이 필요한 주소가 아니라면 이 필터를 타지 않음

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		System.out.println("인증이나 권한이 필요한 주소 요청이 됨");
	}

}

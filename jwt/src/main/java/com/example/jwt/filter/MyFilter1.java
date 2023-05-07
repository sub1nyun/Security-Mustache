package com.example.jwt.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 실제 필터가 되기 위해서 인터페이스 받음
public class MyFilter1 implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		// 다시 필터를 탈 수 있도록 등록 -> 끝나지 않고 프로세스를 진행하기 위해서
		
		// 다운케스팅해서 넣어줌
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// 토큰 : cos 만들어줘야함 ->  id, pw가 정상적으로 들어와서 로그인이 완료되면 토큰을 만들어주고 그것을 응답
		// 요청할 때마다 header에 Authoriztation에 value 값으로 토큰을 가지고 오면 됨
		// 그때 토큰이 넘어오면 이 토큰이 내가 만든 토큰이 맞는지만 검증하면 됨 ( RSA, HS256 )
		
		// 토큰을 만들었다고 가정 : 코스(토큰 명)
		if(req.getMethod().equals("POST")) {
			String headerAuth = req.getHeader("Authorization");
			System.out.println(headerAuth);
			System.out.println("필터1");
			// 토큰이 '코스'라고  날아올때만 접근 컨트롤러로 타게
			if(headerAuth.equals("cos")) {
				chain.doFilter(req, res);
			}else {
				PrintWriter out = res.getWriter();
				out.println("인증 안 됨");
			}
		}
		
		 // 현재는 null로 뜸 
		
		
		
		
		
		
		
	}

}

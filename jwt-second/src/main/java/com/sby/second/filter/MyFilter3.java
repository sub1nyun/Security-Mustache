package com.sby.second.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Filter 인터페이스를 구현해주면 filter가 됨 
public class MyFilter3 implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		// 각 서블릿을 다운 캐스팅해서 필터 체인에 전달
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		// 토큰 : cos을 만들어 줘야하는데 언제 만드는가?
		// id,pw 정상적으로 들어와서 로그인이ㅣ 된다면 토큰을 만들고 해당 토큰을 응답해준다
		// 요청할 때마다 header에 Authorization에 value 값으로 토큰을 가지고 오게됨
		// 그때 토큰이 넘어온다면 내가 만든 토큰이 맞는지만 검증하면 됨 -> RSA, HS256 
		if(req.getMethod().equals("POST")) {
			String headerAuth =  req.getHeader("Authorization");
			System.out.println(headerAuth);
			
			System.out.println("필터3 빵야 빵야");
			
			if(headerAuth.equals("cos")) {
				chain.doFilter(req, res);
			}else {
				PrintWriter out = res.getWriter();
				out.println("인증 안됨");
			}
			
		}
		
		
	}

	
	
}

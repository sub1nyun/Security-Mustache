package com.sby.second.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

// Filter 인터페이스를 구현해주면 filter가 됨 
public class MyFilter1 implements Filter{

	// 시큐리티 필터가 서블릿의 톰켓보다 먼저 동작함 
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("필터1 빵야 빵야");
		chain.doFilter(request, response); // 필터체인에 다시 등록해줘야 함 -> 프로세스가 진행 되도록
		
	}

	
	
}

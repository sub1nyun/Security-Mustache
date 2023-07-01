package com.sby.second.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

// Filter 인터페이스를 구현해주면 filter가 됨 
public class MyFilter2 implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("필터2 빵야 빵야");
		chain.doFilter(request, response); // 필터체인에 다시 등록해줘야 함 -> 프로세스가 진행 되도록
		
	}

	
	
}

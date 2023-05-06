package com.example.jwt.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

// 실제 필터가 되기 위해서 인터페이스 받음
public class MyFilter1 implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("필터1");
		// 다시 필터를 탈 수 있도록 등록 -> 끝나지 않고 프로세스를 진행하기 위해서
		chain.doFilter(request, response);
		
	}

}

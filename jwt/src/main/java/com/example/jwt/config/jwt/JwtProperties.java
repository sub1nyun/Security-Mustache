package com.example.jwt.config.jwt;

public interface JwtProperties {
	
	String SECRET = "윤수빈"; // 서버만 알고 있는 비밀 값
	int EXPIRATION_TIME = 864000000; // 10일 (1/1000초)
	String TOKEN_PREFIX = "Bearer ";
	String HEADER_STRING = "Authorization";

}

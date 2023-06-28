package com.sby.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sby.blog.model.User;
import com.sby.blog.repository.UserRepository;

// Authentication 객체를 만들어서 세션에 넣어야 하기때문에 서비스 생성
// 시큐리티 설정에서 loginProcessingUrl("loginProc") -> 요청이 오면 
// 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 메서드가 실행됨
@Service
public class PrincipalDetailsService implements UserDetailsService {
	
	@Autowired 
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 유저가 있는지 확인해보기
		// 기본 CRUD만 들고있기에 만들어줘야함
		User userEntity = userRepository.findByUsername(username);
		if(userEntity != null) {
			// UserDetails 리턴 하기 때문에 시큐리티 session으로 -> Authentication -> UserDetails 타입이 들어와야함
			return new PrincipalDetails(userEntity);
			// 리턴 된 값이 Authentication의 내부에 들어가게됨 -> 시큐리티 세션 내부에 포함되게 됨
		}
		return null;
	}

}

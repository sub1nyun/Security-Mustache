package com.example.security1.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.security1.model.User;
import com.example.security1.repository.UserRepository;

// 시큐리티 설정에서 loginProcessingUrl("/loginProc");
// /loginProc 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행
// 규칙이라고 볼 수 있음
@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	// 시큐리티 session = Authentication = UserDetails 
	// 리턴 값이 = session(내부 Authentication(내부 UserDetails))
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("username: " + username);
		// loginForm에서 name값 username과 파라메터 이름이 동일하지 않으면 문제가 있음 
		// 해당 사용자가 있는지 확인해보기
		User userEntity = userRepository.findByUsername(username);
		// 기본적인 CRUD만 들고 있기때문에 메서드 직접 만들어주기 ▲
		if(userEntity != null) { //사용자를 찾음
			return new PrincipalDetails(userEntity);
		}// 찾지 봇함
		return null;
	}

}

package com.example.security1.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.security1.model.User;

// 시큐리티가 /loginProc 주소 요청이 오면 낚아채서 로그인을 진행시킴
// 로그인이 진행이 완료가 되면 시큐리티가 가지고 있는 session을 만들어줌 (Security ContextHolder)라는 키 값에 세션정보 저장
// 세션에 들어갈 수 있는 정보는 오브젝트가 정해져있음 -> Authentication 타입 객체
// Authentication 안에 User 정보가 있어야 함 -> User 오브젝트의 타입은 => UserDetails 타입 객체

// Security Session => Authentication => UserDetails(PrincipalDetails)

public class PrincipalDetails implements UserDetails {
	
	// 현재 유저 정보를 들고 있는 객체
	private User user; // 콤포지션 
	
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	// 해당 User의 권한을 리턴하는 곳!
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>(); // ArrayList 컬렉션의 자식
		collect.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				return user.getRole();
			}
		});
		return collect;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}


	@Override
	public boolean isAccountNonExpired() {
		// 계정 만료
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// 계정 잠김
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		//언제 비활성화 하는가 
		// ex) 1년동안 회원이 로그인을 하지 않으면 -> 휴먼 계정으로 전환한다면
		// 현재 시간 - 마지막 로그인 시간
		return true;
	}

}
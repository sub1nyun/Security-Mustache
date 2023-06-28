package com.sby.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sby.blog.model.User;

// 시큐리티가 /loginProc 주소 요청을 낚아채서 로그인을 진행시켜줌 
// 로그인 진행 완료가 되면 시큐리티 전용 session을 생성 -> 같은 세션공간이지만 자신만의 공간을 갖음 -> 키 값으로 구분
// Security ContextHolder(키 값)에 세션 정보를 저장함
// 들어갈 수 있는 오브젝트는 -> Authentication 타입의 객체
// Authentication 내부에는 -> User 정보가 있어야함
// User의 타입도 지정되어 있음 -> UserDetails 타입의 객체

// 시큐리티 세션 -> Authentication -> UserDetails
// PrincipalDetails가 UserDetails 구현하여 -> UserDetails 타입이 됨 -> Authentication 객체 내부에 접근이 가능

/*
 * UserDetails 인터페이스는 시큐리티에서 인증과 관련된 사용자 정보를 제공하기 위한 메서드를 가진 인터페이스
 * 스프링 시큐리티에서는 인증(Authentication) 객체에 인증된 사용자의 정보를 저장 -> 사용자의 정보는 PrincipalDetails 감싸짐
 */

// 결론 : 시큐리티를 통해서 로그인을 진행하면(인증 성공) -> 인증 정보는 보안 관련 세션인 SecurityContextHolder에 저장됨
// 인증된 사용자의 정보는 Authentication 객체에 담겨 있으며 -> 내부에는 인증된 사용자의 정보가 저장되어야함 
// 해당 정보는 UserDetails 인터페이스를 구현한 객체여야함 -> PrincipalDetails에서 구현했음


public class PrincipalDetails implements UserDetails {
	
	// UserDetails 인터페이스의 인증 관련된 메서드를 사용하기 위해서 User 데이터가 필요함
	private User user;
	
	// 로그인  하는 사용자의 정보를 PrincipalDetails 객체에 전달하기 위함
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	// 해당 User의 권한을 리턴하는 곳!
	// 계정의 권한이 여러개 있을 수 있어서 루프를 돌아야 하기때문에 컬렉션
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// GrantedAuthority 타입을 리턴해야함
		Collection<GrantedAuthority> collect = new ArrayList<GrantedAuthority>();
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

	// 계정 만료
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정 블럭
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	// 비밀 번호 기한
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	// 계정 활성화
	@Override
	public boolean isEnabled() {
		//언제 비활성화 하는가 
		// ex) 1년동안 회원이 로그인을 하지 않으면 -> 휴먼 계정으로 전환한다면
		// 현재 시간 - 마지막 로그인 시간
		return true;
	}

}

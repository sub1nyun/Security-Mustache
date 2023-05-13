package config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sby.blog.model.User;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
// 스프링 시큐리티의 고유한 세션저장소에 저장하게됨 -> UserDetails 타입의 PrinccipalDetail가 저장됨
public class PrincipalDetail implements UserDetails{

	// 시큐리티 세션에 저장할때 DB에 저장하는 User정보도 가지고 있어야함
	private User user; // model 패키지의 user 오브젝트 -> 콤포지션(상속이 아닌 객체를 품고있는것)
	
	
	public PrincipalDetail(User user) {
		this.user = user;
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
	public boolean isAccountNonExpired() { // 계정 만료 -> true 만료 안 됨
		return true;
	}

	@Override
	public boolean isAccountNonLocked() { // 계정 잠김 -> true 잠기지 않음
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { // 계정 비밀번호 만료 -> true 만료 안 됨
		return true;
	}

	@Override
	public boolean isEnabled() { // 계정 활성화 -> true 활성화
		return true;
	}
	
	@Override // 계정 권한 
	public Collection<? extends GrantedAuthority> getAuthorities() { 
		
		//ArrayList 부모에 -> Collection이 존재함
		Collection<GrantedAuthority> collectors = new ArrayList<GrantedAuthority>();
		// GrantedAuthority 인터페이스기 때문에 익명 클래스 생성
		collectors.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return "ROLE_"+user.getRole(); // ROLE_USER 반드시 ROLE_ 붙혀야 인식함
			}
		});
		
		// add 메서드 안에 들어올것이 하나 밖에 없음
		// 람다식 버전
		collectors.add(()->{
			return "ROLE_"+user.getRole();
		});
		
		return collectors;
	}
	
	
	
	
	
	
	
}

package com.example.jwt.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.config.auth.PrincipalDetail;
import com.example.jwt.model.User;
import com.example.jwt.repository.UserRepository;

import lombok.RequiredArgsConstructor;

//@CrossOrigin // 인증이 필요한 요청은 해결되지 않음
@RestController
@RequiredArgsConstructor
public class RestApiController {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	

	@GetMapping("home")
	public String home() {
		return "<h1>home</h1>";
	}
	
	@PostMapping("token")
	public String token() {
		return "<h1>token</h1>";
	}
	
	// JWT를 사용하면 UserDetailsService를 호출하지 않기 때문에 @AuthenticationPrincipal 사용 불가능
	// 왜나하면 @AuthenticationPrincipal은 UserDetailsService에서 리턴될 때 만들어지기 때문
	
	@GetMapping("/api/v1/user")
	public String user(Authentication authentication) {
		PrincipalDetail princiapl =(PrincipalDetail) authentication.getPrincipal();
		System.out.println("아이디 : " + princiapl.getUser().getId());
		System.out.println("닉네임 : " + princiapl.getUser().getUsername());
		System.out.println("비밀번호 : " + princiapl.getUser().getPassword());
		
		return "<h1>user</h1>";
	}
	
	// 	매니저 혹은 어드민이 접근 가능
		@GetMapping("/api/v1/manager/reports")
		public String reports() {
			return "<h1>reports</h1>";
		}
		
		// 어드민이 접근 가능
		@GetMapping("/api/v1/admin/users")
		public List<User> users() {
			return userRepository.findAll();
		}
	
	@PostMapping("join")
	public String join(@RequestBody User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles("ROLE_USER");
		userRepository.save(user);
		
		return "회원가입 완료";
	}
	
}

package com.sby.second.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sby.second.model.User;
import com.sby.second.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RestApiController {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("home")
	public String home() {
		return "<h1>home</h1>";
	}
	
	@PostMapping("token")
	public String token() {
		return "<h1>token</h1>";
	}
	
	@PostMapping("join")
	public String join(@RequestBody User user) {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword())); // 1234ppp -> ABC333333 해시화
		user.setRoles("ROLE_USER");
		userRepository.save(user);
		return "회원가입완료";
	}
	
}

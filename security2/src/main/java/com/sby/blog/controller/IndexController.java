package com.sby.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sby.blog.model.User;
import com.sby.blog.repository.UserRepository;

@Controller
public class IndexController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping({"", "/"})
	public String index() {
		// 뷰리졸버 설정해주기 -> 머스테치 의존성 등록했기 때문에 prefix, suffix 생략이 가능
		return "index";
	}
	
	@GetMapping("/user")
	public @ResponseBody String user() {
		return "user";
	}
	
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}
	
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}
	
	// /login 요청은 스프링 시큐리티가 낚아 채가는 중 
	// Config 파일 생성 후 작동하지 않음
	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}
	
	@PostMapping("/join")
	public String join(User user) {
		user.setRole("ROLE_USER");
		//userRepository.save(user); // 비밀번호가 평문으로 저장되는 문제가 발생함
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		userRepository.save(user);
		return "redirect:/loginForm"; //회원가입이 됐다면 -> loginForm 재호출 
	}
	
	// 1. @EnableGlobalMethodSecurity(securedEnabled = true 활성화 전에는 권한이 없어서 어디서든 접근이 가능했음
	@Secured("ROLE_ADMIN") // 권한이 없다면 info에 접근이 불가능해짐 -> 간단하게 권한 부여하기
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "개인정보";
	}
	
	// 해당 메서드가 작동하기 직전에 작동
	@PreAuthorize("hasRole('ROLE_MANAGER')")
	//@PostAuthorize // 메서드가 끝난 후 작동
	@GetMapping("/data")
	public @ResponseBody String data() {
		return "데이터정보";
	}
	
	
	/*
	@GetMapping("/joinProc")
	public @ResponseBody String joinProc() {
		return "회원가입 완료";
	}
	*/
}

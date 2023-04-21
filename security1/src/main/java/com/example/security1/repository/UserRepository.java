package com.example.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.security1.model.User;

// CRUD 함수를 JpaRepository가 내장하고 있음
// @Repository라는 어노테이션을 명시하지 않아도 IOC가 가능함 -> JpaRepository를 상속했기 때문에
public interface UserRepository extends JpaRepository<User, Integer>{
	
	// findBy 규칙 -> Username 문법
	// select * from user where username = 1? ---- 1? -> username 
	public User findByUsername(String username); // jpa Query methods 찾아보기 
	
	// select * from user where email = ? 
	
}

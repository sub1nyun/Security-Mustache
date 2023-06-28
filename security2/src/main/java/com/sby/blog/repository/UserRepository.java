package com.sby.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sby.blog.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	// findBy 규칙 -> Username 문법
	// select * from user where username = ?
	public User findByUsername(String username);

}

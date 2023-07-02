package com.sby.second.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sby.second.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);
}

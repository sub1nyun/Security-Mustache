package com.sby.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sby.blog.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}

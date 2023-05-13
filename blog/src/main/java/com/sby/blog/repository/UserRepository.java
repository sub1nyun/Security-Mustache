package com.sby.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sby.blog.model.User;

// ▼ User 테이블을 관리하는 인터페이스이며 PK는 Integer 타입
// 자동으로 Bean으로 등록이 되기 때문에 @Repository 생략 가능
public interface UserRepository extends JpaRepository<User, Integer>{

	// SELECT * FROM user WHERE username = ?
	Optional<User> findByUsername(String username);

}

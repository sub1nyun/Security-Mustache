package com.sby.blog;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.sby.blog.model.User;
import com.sby.blog.repository.UserRepository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BlogControllerTest {
	
	private UserRepository userRepository;

	
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();
	}
	
	// 람다식 적용
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// 없는 user를 찾으면 null 이 발생
		// Optional로 User객체를 감싸서 null인지 아닌지 판단해서 return
		/*
		User user = userRepository.findById(id).orElseThrow(()-> {
			return new IllegalArgumentException("해당 사용자 없음");
		});
		return user;
		*/
		User user =userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 사용자 없음");
			}
		});
		return user;
	}
	
	// 한페이지당 2건에 데이터를 리턴받아 볼 예정
	// pageable을 받는 findAll 메서드 사용
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<User> paginUser = userRepository.findAll(pageable);
		List<User> users = paginUser.getContent();
		return users;
	}
	
	@Transactional
	@PutMapping("dummy/user/{id}")
	// email, password 수정
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		//requestUser.setId(id); 
		//userRepository.save(requestUser);
		
		//save()를 통해서 update를 한다면
		User user = userRepository.findById(id).orElseThrow(()-> { // 영속화되는 시점
			return new IllegalArgumentException("수정 실패");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		// DB에서 찾아온 user기 때문에 null 값이 없음
		// 1. save() id 전달하지 않으면 insert
		// 2. save() id를 전달하면 해당 id에 대한 데이터를 update
		// 3. save() id를 전달하면 해당 id에 대한 데이터가 없다면 insert
		// userRepository.save(user);
		// ▲ 더티체킹을 이용해서 진행
		/*
		더티체킹(Dirty Checking)이란 상태 변경 검사이다.
		JPA에서는 트랜잭션이 끝나는 시점에 변화가 있는 모든 엔티티 객체를 데이터베이스 반영한다.
		그렇기 때문에 값을 변경한 뒤, save 하지 않더라도 DB에 반영되는 것이다.
		
		이러한 상태 변경 검사의 대상은 영속성 컨텍스트가 관리하는 엔티티에만 적용된다.(준영속, 비영속된 객체X)
		
			더티체킹(Dirty Checking) 원리:
		    · 영속성 컨텍스트란 서버와 DB사이에 존재한다.
		    · JPA는 엔티티를 영속성 컨텍스트에 보관할 때, 최초 상태를 복사해서 저장해둔다.(일종의 스냅샷)
		    · 트랜잭션이 끝나고 flush할 때 스냅샷과 현재 엔티티를 비교해 변경된 엔티티를 찾아낸다.
		    · JPA는 변경된 엔티티를 DB단에 반영하여 한번에 쿼리문을 날려준다.
		 */
		return user;
	}
	
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		}catch (EmptyResultDataAccessException e) {
			return "삭제 실패";
		}	
		return "삭제됨";
	}
	
	
	
	
}

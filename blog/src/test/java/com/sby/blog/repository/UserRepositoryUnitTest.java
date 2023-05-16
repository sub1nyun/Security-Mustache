package com.sby.blog.repository;

import javax.transaction.Transactional;

import org.apache.jasper.tagplugins.jstl.core.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Transactional
@AutoConfigureTestDatabase(replace = Replace.ANY)
@DataJpaTest
public class UserRepositoryUnitTest {

	@Autowired
	private UserRepository userRepository;
	
	
}

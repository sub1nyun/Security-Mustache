package com.example.jwt.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // mysql 이라면 autoin~~ 전략
	private long id;
	private String username;
	private String password;
	private String roles; // USER, ADMIN
	
	// 2개 이상이기에 만들었음 -> Role 이라는 모델(테이블)을 하나 더 만들어서 처리할 수도 있음
	public List<String> getRoleList() {
		if(this.roles.length() > 0 ) {
			return Arrays.asList(this.roles.split(","));
		}
		return new ArrayList<>(); // null이 발상해지 않게만
	}

}

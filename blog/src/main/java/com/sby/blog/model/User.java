package com.sby.blog.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴
@Entity // User 클래스가 MySQL에 테이블로 생성이 됨
//@DynamicInsert // insert시에 null인 필드를 제외시켜줌
public class User {

	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 넘버링전략 -> 시퀀스, MYSQL의 경우 auto_increment
	private int id; 
	
	@Column(nullable = false, length = 30, unique = true)  
	private String username;
	
	@Column(nullable = false, length = 100)  
	private String password;
	
	@Column(nullable = false, length = 50) 
	private String email;
	
	//@ColumnDefault("'user'")
	// DB는 RoleType이라는 타입이 없음 -> String 타입이라고 알려줘야함
	@Enumerated(EnumType.STRING)
	private RoleType role; // ADMIN과 USER만 입력이 가능하도록 강제
	
	@CreationTimestamp // 시간을 자동입력해줌
	private Timestamp createDate;
}

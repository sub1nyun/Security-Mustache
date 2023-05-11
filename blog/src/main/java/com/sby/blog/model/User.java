package com.sby.blog.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

@Entity // User 클래스가 MySQL에 테이블로 생성이 됨
public class User {

	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 넘버링전략 -> 시퀀스, MYSQL의 경우 auto_increment
	private int id; 
	
	@Column(nullable = false, length = 30)  
	private String username;
	
	@Column(nullable = false, length = 100)  
	private String password;
	
	@Column(nullable = false, length = 50) 
	private String email;
	
	@ColumnDefault("'user'")
	private String role; 
	
	@CreationTimestamp // 시간을 자동입력해줌
	private Timestamp createDate;
}

package com.sby.blog.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob // 대용량 데이터 
	private String content; // 섬머노트 라이브러리 사용 예정 -> 태그가 섞여서 들어가게됨
	
	@ColumnDefault("0") // int만 있기에 ' ' 처리해 줄 필요 없음
	private int count; // 조회수
	
	// 연관관계 ▼ Board가 Many || User가 One
	@ManyToOne // Many = Board, User = one -> 한명의 유저는 여러개의 개시글을 작성이 가능하다는 의미
	@JoinColumn(name="userId") // 실제 DB 컬럼에 만들어질 이름
	private User userId; // DB는 오브젝트럴 저장할 수 없음 -> FK, 자바는 오브젝트 저장 가능
	// ORM -> OBJECT를 저장 가능
	
	@CreationTimestamp
	private Timestamp createDate;
	
	

}

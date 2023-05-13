package com.sby.blog.model;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	
	// 연관관계 ▼ Board가 Many || User가 One || @ManyToOne -> EAGER -> 기본전략 -> Board테이블을 조회하면 User는 정보는 가져옴 -> 한 건 밖에 없어서
	@ManyToOne(fetch = FetchType.EAGER) // Many = Board, User = one -> 한명의 유저는 여러개의 개시글을 작성이 가능하다는 의미
	@JoinColumn(name="userId") // 실제 DB 컬럼에 만들어질 이름
	private User userId; // DB는 오브젝트럴 저장할 수 없음 -> FK, 자바는 오브젝트 저장 가능
	// ORM -> OBJECT를 저장 가능
	
	// mappedBy -> 연관관계의 주인이 아니라는 의미 -> FK가 아니다 -> DB에 컬럼을 생서하지 말라는 의미 (Reply 클래스에 있는 필드 명 사용해야함)
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER) // 하나의 게시글은 여러개의 답글을 둘 수 있음
	private List<Reply> reply;
	
	@CreationTimestamp
	private Timestamp createDate;
	
	// @ManyToOne -> fetch = FetchType.EAGER 전략을 취한다
	// ex) Board테이블을 조회 시 User 테이블의 정보를 (join을 통해)가져옴 -> One(한 건이기 때문에)
	
	// @OneToMany -> Board테이블을 조회 시 Many(건 수가 많음) -> 필요하면 가져오고 필요가 없다면 가지고 오지 않음
	// UI 구성을 게시글을 조회 시 댓글을 모두 봐야 하기때문에 -> EAGER 전략으로 변경

}

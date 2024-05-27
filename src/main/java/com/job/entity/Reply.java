package com.job.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Table(name = "REPLY_TB")  //  table 이름과 클래스 이름이 다를때 사용 (oracle은 user table 못만듬)
@Getter
@Entity
@Builder
@AllArgsConstructor
@SequenceGenerator(name="REPLY_SEQ_GENERATOR", 
sequenceName   = "REPLY_SEQ", 
initialValue   = 1,     // 초기값
allocationSize = 1 )   // 증가치
public class Reply {   
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "REPLY_SEQ_GENERATOR")
    @Column(name = "reply_idx", nullable = false, unique = true, updatable = false)
	private Long replyIdx;
	
	@ManyToOne                      	// 관계 : 다대일 설정 (Community <-> Reply)  
	@JoinColumn(name="community_idx")  // 외래키 칼럼 (부모키 Community idx칼럼)
	private Community community;
	
	@ManyToOne                      	// 관계 : 다대일 설정 (Community <-> Reply)  
	@JoinColumn(name="user_idx")  // 외래키 칼럼 (부모키 Community idx칼럼)
	private User user;
	
	@Column(name = "reply_name", nullable = false)
	private String replyName;
	
	@Column(name = "reply_content", nullable = false)
	private String replyContent;
	
	@Column(name = "created_date", nullable = false)
	private String createdDate;

	   protected Reply() {
	        // 보호된 기본 생성자
	    }
	
}

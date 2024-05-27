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

@Table(name = "COMMUNITY_TB")  //  table 이름과 클래스 이름이 다를때 사용 (oracle은 user table 못만듬)
@Getter
@Entity
@Builder
@AllArgsConstructor
@SequenceGenerator(name="COMMUNITY_SEQ_GENERATOR", 
sequenceName   = "COMMUNITY_SEQ", 
initialValue   = 1,     // 초기값
allocationSize = 1 )   // 증가치	
public class Community {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "COMMUNITY_SEQ_GENERATOR")
    @Column(name = "community_idx", nullable = false, unique = true, updatable = false)
	private Long communityIdx;
	
	@Column(name = "community_title", nullable = false)
	private String communityTitle;
	
	@ManyToOne                      	// 관계 : 다대일 설정 (Community <-> Reply)  
	@JoinColumn(name="user_idx")  // 외래키 칼럼 (부모키 Community idx칼럼)
	private User user;
	
	@Column(name = "community_name", nullable = false)
	private String communityName;
	
	@Column(name = "community_content", nullable = false)
	private String communityContent;
	
	@Column(name = "created_date", nullable = false)
	private String createdDate;
	
	@Column(name = "view_count")
	private Long viewCount;
	
	@Column(name = "like_count")
	private Long likeCount;
	
	@Column(name = "reply_count")
	private Long replyCount;
	
	   protected Community() {
	        // 보호된 기본 생성자
	    }
	
}

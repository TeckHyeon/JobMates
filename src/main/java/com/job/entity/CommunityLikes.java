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
import lombok.ToString;

@Table(name = "COMMUNITY_LIKES_TB")  //  table 이름과 클래스 이름이 다를때 사용 (oracle은 user table 못만듬)
@Getter
@Entity
@Builder
@AllArgsConstructor
@ToString
@SequenceGenerator(name="COMMUNITY_LIKES_SEQ_GENERATOR", 
sequenceName   = "COMMUNITY_LIKES_SEQ", 
initialValue   = 1,     // 초기값
allocationSize = 1 )   // 증가치	
public class CommunityLikes {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "COMMUNITY_LIKES_SEQ_GENERATOR")
    @Column(name = "likes_idx", nullable = false, unique = true, updatable = false)
	private Long likesIdx;

	@ManyToOne                      	
	@JoinColumn(name="COMMUNITY_IDX")  
	private Community community;
	
	@ManyToOne                      	
	@JoinColumn(name="user_idx")  
	private User user;
	
    protected CommunityLikes() {
        // 보호된 기본 생성자
    }
}

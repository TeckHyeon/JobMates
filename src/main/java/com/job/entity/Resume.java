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
import lombok.Getter;

@Table(name = "RESUME_TB")  //  table 이름과 클래스 이름이 다를때 사용 (oracle은 user table 못만듬)
@Getter
@Entity
@SequenceGenerator(name="RESUME_SEQ_GENERATOR", 
sequenceName   = "RESUME_SEQ", 
initialValue   = 1,     // 초기값
allocationSize = 1 )   // 증가치
public class Resume {   
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "RESUME_SEQ_GENERATOR")
    @Column(name = "resume_idx", nullable = false, unique = true, updatable = false)
	private Long resumeIdx;
	
	@ManyToOne                      // 관계 : 다대일 설정 (USER <-> RESUME)  
	@JoinColumn(name="user_idx")  // 외래키 칼럼 (부모키 USER idx칼럼)
	private User user;
	
	@Column(name = "resume_title", nullable = false)
	private String resumeTitle;
	
	@Column(name = "portfolio", nullable = false)
	private String portfolio;
	
	@Column(name = "publish", nullable = false)
	private Long publish;
	
	@Column(name = "resume_comment", nullable = false)
	private String resumeComment;
	
	@Column(name = "created_date", nullable = false)
	private String createdDate;
}
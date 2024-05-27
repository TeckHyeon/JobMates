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
import lombok.ToString;

@Table(name = "POSTING_TB")  //  table 이름과 클래스 이름이 다를때 사용 (oracle은 user table 못만듬)
@Getter
@Entity
@ToString
@SequenceGenerator(name="POSTING_SEQ_GENERATOR", 
sequenceName   = "POSTING_SEQ", 
initialValue   = 1,     // 초기값
allocationSize = 1 )   // 증가치
public class Posting {

    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "POSTING_SEQ_GENERATOR")
    @Column(name = "posting_idx", nullable = false, updatable = false)
	private Long postingIdx;
    
	@ManyToOne                      	// 관계 : 다대일 설정 (Community <-> Reply)  
	@JoinColumn(name="user_idx", nullable = false)  // 외래키 칼럼 (부모키 Community idx칼럼)
	private User user;
	
	@Column(name = "posting_title", nullable = false)
	private String postingTitle;
	
	@Column(name = "posting_comment", nullable = false)
	private String postingComment;
	
	@Column(name = "experience", nullable = false)
	private String experience;
	
	@Column(name = "emp_type", nullable = false)
	private String empType;
	
	@Column(name = "salary", nullable = false)
	private String salary;
	
	@Column(name = "start_time", nullable = false)
	private String startTime;
	
	@Column(name = "end_time", nullable = false)
	private String endTime;
	
	@Column(name = "posting_deadline")
	private String postingDeadline;
	
	@Column(name = "job_type", nullable = false)
	private String jobType;
	
	@Column(name = "created_date", nullable = false)
	private String createdDate;
	
	@Column(name = "posting_address", nullable = false)
	private String postingAddress;
    
    // Lombok @NoArgsConstructor(access = AccessLevel.PROTECTED) 대신 직접 구현
    protected Posting() {
        // 보호된 기본 생성자
    }

}

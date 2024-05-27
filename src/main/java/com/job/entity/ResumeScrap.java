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

@Table(name = "SKILL_TB")  //  table 이름과 클래스 이름이 다를때 사용 (oracle은 user table 못만듬)
@Getter
@Entity
@SequenceGenerator(name="RSCRAP_SEQ_GENERATOR", 
sequenceName   = "RSCRAP_SEQ", 
initialValue   = 1,     // 초기값
allocationSize = 1 )   // 증가치
public class ResumeScrap {   
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "RSCRAP_SEQ_GENERATOR")
    @Column(name = "rscrap_idx", nullable = false, unique = true, updatable = false)
	private Long rscrapIdx;

	@ManyToOne                      // 관계 : 다대일 설정 (Company <-> ResumeScrap)  
	@JoinColumn(name="company_idx")  // 외래키 칼럼 (부모키 Company idx칼럼)
	private Company company;
	
	@ManyToOne                      // 관계 : 다대일 설정 (Resume <-> ResumeScrap)  
	@JoinColumn(name="resume_idx")  // 외래키 칼럼 (부모키 Resume idx칼럼)
	private Resume resume;

}
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

@Table(name = "POSTING_SKILL_TB")  //  table 이름과 클래스 이름이 다를때 사용 (oracle은 user table 못만듬)
@Getter
@Entity
@SequenceGenerator(name="POSTINGSKILL_SEQ_GENERATOR", 
sequenceName   = "POSTINGSKILL_SEQ", 
initialValue   = 1,     // 초기값
allocationSize = 1 )   // 증가치
public class PostingSkill {   
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "POSTINGSKILL_SEQ_GENERATOR")
    @Column(name = "posting_skill_idx", nullable = false, unique = true, updatable = false)
	private Long postingSkillIdx;
	
	@ManyToOne                      	// 관계 : 다대일 설정 (Community <-> Reply)  
	@JoinColumn(name="skill_idx")  // 외래키 칼럼 (부모키 Community idx칼럼)
	private Skill skill;
	
	@ManyToOne                      	// 관계 : 다대일 설정 (Community <-> Reply)  
	@JoinColumn(name="posting_idx")  // 외래키 칼럼 (부모키 Community idx칼럼)
	private Posting posting;
}

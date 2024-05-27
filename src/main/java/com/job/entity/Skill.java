package com.job.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Table(name = "SKILL_TB")  //  table 이름과 클래스 이름이 다를때 사용 (oracle은 user table 못만듬)
@Getter
@Entity
@Builder
@AllArgsConstructor
@SequenceGenerator(name="SKILL_SEQ_GENERATOR", 
sequenceName   = "SKILL_SEQ", 
initialValue   = 1,     // 초기값
allocationSize = 1 )   // 증가치
public class Skill {   
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "SKILL_SEQ_GENERATOR")
    @Column(name = "skill_idx", nullable = false, unique = true, updatable = false)
	private Long skillIdx;
	@Column(name = "skill_name", nullable = false, unique = true, updatable = false)
	private String skillName;
	
	protected Skill() {
        // 보호된 기본 생성자
    }
	
}

package com.job.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;

@Table(name = "FAQ_TB")  //  table 이름과 클래스 이름이 다를때 사용 (oracle은 user table 못만듬)
@Getter
@Entity
@SequenceGenerator(name="FAQ_SEQ_GENERATOR", 
sequenceName   = "FAQ_SEQ", 
initialValue   = 1,     // 초기값
allocationSize = 1 )   // 증가치
public class Faq {   
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "FAQ_SEQ_GENERATOR")
    @Column(name = "faq_idx", nullable = false, unique = true, updatable = false)
	private Long faqIdx;
	
	@Column(name = "faq_type", nullable = false)
	private Long faqType;
	
	@Column(name = "question", nullable = false)
	private String question;
	
	@Column(name = "answer", nullable = false)
	private String answer;
}

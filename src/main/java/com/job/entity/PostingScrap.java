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

@Table(name = "POSTING_SCRAP_TB")  //  table 이름과 클래스 이름이 다를때 사용 (oracle은 user table 못만듬)
@Getter
@Entity
@Builder
@AllArgsConstructor
@ToString
@SequenceGenerator(name="PSCRAP_SEQ_GENERATOR", 
sequenceName   = "PSCRAP_SEQ", 
initialValue   = 1,     // 초기값
allocationSize = 1 )   // 증가치
public class PostingScrap {   
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "PSCRAP_SEQ_GENERATOR")
    @Column(name = "pscrap_idx", nullable = false, unique = true, updatable = false)
	private Long pscrapIdx;

	@ManyToOne                      // 관계 : 다대일 설정 (Company <-> ResumeScrap)  
	@JoinColumn(name="person_idx")  // 외래키 칼럼 (부모키 Company idx칼럼)
	private Person person;

	@ManyToOne                      // 관계 : 다대일 설정 (Resume <-> ResumeScrap)  
	@JoinColumn(name="posting_idx")  // 외래키 칼럼 (부모키 Resume idx칼럼)
	private Posting posting;
	
    // Lombok @NoArgsConstructor(access = AccessLevel.PROTECTED) 대신 직접 구현
    protected PostingScrap() {
        // 보호된 기본 생성자
    }
}
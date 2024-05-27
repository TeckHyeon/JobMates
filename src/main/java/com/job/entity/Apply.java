package com.job.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Table(name = "APPLY_TB") // table 이름과 클래스 이름이 다를때 사용 (oracle은 user table 못만듬)
@Getter
@Entity
@Builder
@AllArgsConstructor
@ToString
@SequenceGenerator(name = "APPLY_SEQ_GENERATOR", sequenceName = "APPLY_SEQ", initialValue = 1, // 초기값
		allocationSize = 1) // 증가치

public class Apply {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPLY_SEQ_GENERATOR")
	@Column(name = "apply_idx", nullable = false, unique = true, updatable = false)
	private Long applyIdx;

	@ManyToOne // 관계 : 다대일 설정 (USER <-> Company)
	@JoinColumn(name = "posting_idx", nullable = false) // 외래키 칼럼 (부모키 USER idx칼럼)
	private Posting posting;

	@ManyToOne // 관계 : 다대일 설정 (USER <-> Company)
	@JoinColumn(name = "resume_idx", nullable = false) // 외래키 칼럼 (부모키 USER idx칼럼)
	private Resume resume;

	@Column(name = "created_date", nullable = false)
	private String createdDate;

	@Column(name = "apply_status", nullable = false)
	private Long applyStatus;

	@ManyToOne
	@JoinColumn(name = "person_idx", nullable = false)
	private Person person;
	
	@ManyToOne
	@JoinColumn(name = "company_idx", nullable = false)
	private Company company;

	protected Apply() {
        // 보호된 기본 생성자
    }
	
	@PrePersist
	public void prePersist() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.createdDate = LocalDateTime.now().format(formatter);
	}

	public void changeApplyStatus(Long applyStatus) {
		 this.applyStatus = applyStatus;
		
	}
}

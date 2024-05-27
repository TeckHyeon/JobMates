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

@Table(name = "COMPANY_TB")  //  table 이름과 클래스 이름이 다를때 사용 (oracle은 user table 못만듬)
@Getter
@Entity
@SequenceGenerator(name="COMPANY_SEQ_GENERATOR", 
sequenceName   = "COMPANY_SEQ", 
initialValue   = 1,     // 초기값
allocationSize = 1 )   // 증가치
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "COMPANY_SEQ_GENERATOR")
    @Column(name = "company_idx", nullable = false, unique = true, updatable = false)
	private Long companyIdx;
	
	@ManyToOne                      // 관계 : 다대일 설정 (USER <-> Company)  
	@JoinColumn(name="user_idx")  // 외래키 칼럼 (부모키 USER idx칼럼)
	private User user;
	
	@Column(name = "company_name", nullable = false)
	private String companyName;
	
	@Column(name = "company_rep_name", nullable = false)
	private String companyRepName;
	
	@Column(name = "company_phone", nullable = false)
	private String companyPhone;
	
	@Column(name = "company_address", nullable = false)
	private String companyAddress;
	
	@Column(name = "company_mgr_name")
	private String companyMgrName;
	
	@Column(name = "company_mgr_phone")
	private String companyMgrPhone;
	
	@Column(name = "company_emp")
	private Long companyEmp;
	
	@Column(name = "company_size")
	private String companySize;
	
	@Column(name = "company_sector")
	private String companySector;
	
	@Column(name = "company_year")
	private String companyYear;
	
}

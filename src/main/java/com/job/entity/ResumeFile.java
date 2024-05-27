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

@Table(name = "RESUME_FILE_TB")  //  table 이름과 클래스 이름이 다를때 사용 (oracle은 user table 못만듬)
@Getter
@Entity
@SequenceGenerator(name="ResumeFile_SEQ_GENERATOR", 
sequenceName   = "ResumeFile_SEQ", 
initialValue   = 1,     // 초기값
allocationSize = 1 )   // 증가치
public class ResumeFile {   
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "ResumeFile_SEQ_GENERATOR")
    @Column(name = "resume_file_idx", nullable = false, unique = true, updatable = false)
	private Long resumeFileIdx;
	
	@ManyToOne                      // 관계 : 다대일 설정 (USER <-> Company)  
	@JoinColumn(name="resume_idx")  // 외래키 칼럼 (부모키 USER idx칼럼)
	private Resume resume;
	
	@Column(name = "original_name", nullable = false)
	private String originalName;
	
	@Column(name = "file_path", nullable = false)
	private String filePath;
	
	@Column(name = "file_size", nullable = false)
	private Long fileSize;
	
	@Column(name = "upload_date", nullable = false)
	private String uploadDate;
}

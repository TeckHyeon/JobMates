package com.job.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.entity.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
	
    Optional<Resume> findByResumeIdx(Long resume_idx);

	List<Resume> findByUserUserIdx(Long userIdx);

	Optional<Resume> findResumeByResumeIdx(Long resumeIdx); 
    
}
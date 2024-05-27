package com.job.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.entity.ResumeFile;

public interface ResumeFileRepository extends JpaRepository<ResumeFile, Long> {
	
    Optional<ResumeFile> findByResumeFileIdx(Long resume_file_idx);

	Optional<ResumeFile> findByResumeResumeIdx(Long resumeIdx); 
    
}
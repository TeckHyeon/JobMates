package com.job.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.entity.Apply;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
	
    Optional<Apply> findByApplyIdx(Long apply_idx);

	boolean existsByPersonPersonIdxAndPostingPostingIdx(Long personIdx, Long postingIdx);

	Optional<Apply> findApplyByResumeResumeIdxAndPostingPostingIdx(Long resumeIdx, Long postingIdx); 
    
}
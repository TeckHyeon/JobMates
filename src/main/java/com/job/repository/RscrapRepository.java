package com.job.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.entity.ResumeScrap;

public interface RscrapRepository extends JpaRepository<ResumeScrap, Long> {
	
    Optional<ResumeScrap> findByRscrapIdx(Long rscrap_idx); 
    
}
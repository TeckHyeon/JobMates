package com.job.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.entity.CompanyFile;

public interface CompanyFileRepository extends JpaRepository<CompanyFile, Long> {
	
    Optional<CompanyFile> findByCompanyFileIdx(Long company_file_idx); 
    
}
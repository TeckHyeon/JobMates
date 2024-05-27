package com.job.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	
    Optional<Company> findByCompanyIdx(Long company_idx);

	Optional<Company> findCompanyByUserUserIdx(Long userIdx); 
    
}
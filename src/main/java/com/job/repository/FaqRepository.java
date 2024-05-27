package com.job.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.entity.Faq;

public interface FaqRepository extends JpaRepository<Faq, Long> {
	
    Optional<Faq> findByFaqIdx(Long faq_idx); 
    
}
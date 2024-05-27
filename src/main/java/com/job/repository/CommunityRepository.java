package com.job.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.job.dto.CommunityDto;
import com.job.entity.Community;

public interface CommunityRepository extends JpaRepository<Community, Long> {
	
    Optional<Community> findByCommunityIdx(Long community_idx);
    
	Page<Community> findByCommunityTitleContainingOrCommunityContentContainingAllIgnoreCase(String keyword,
			String keyword2, Pageable pageable); 
    
}
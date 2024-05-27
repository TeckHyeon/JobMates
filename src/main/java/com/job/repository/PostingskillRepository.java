package com.job.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.entity.PostingSkill;

public interface PostingskillRepository extends JpaRepository<PostingSkill, Long> {
	
    Optional<PostingSkill> findByPostingSkillIdx(Long posting_skill_idx);

	List<PostingSkill> findPostinSkillByPostingPostingIdx(Long postingIdx); 
    
}
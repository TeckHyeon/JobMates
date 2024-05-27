package com.job.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.entity.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {
	
    Optional<Skill> findBySkillIdx(Long skill_idx);

	List<Skill> findSkillNameBySkillIdx(Long skillIdx); 
    
}
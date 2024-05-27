
package com.job.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.entity.PersonSkill;

public interface PersonSkillRepository extends JpaRepository<PersonSkill, Long> {

	List<PersonSkill> findSkillIdxByPersonPersonIdx(Long personIdx);
}

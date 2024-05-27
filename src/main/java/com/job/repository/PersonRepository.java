
package com.job.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
	
	Optional<Person> findByUserUserIdx(Long userIdx);
	
	Optional<Person> findByPersonIdx(Long personIdx);

}

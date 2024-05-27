package com.job.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.job.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    
}
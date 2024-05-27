package com.job.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.job.entity.Posting;

public interface PostingRepository extends JpaRepository<Posting, Long> {

	Optional<Posting> findByPostingIdx(Long posting_idx);

	Optional<Posting> findPostingByPostingIdx(Long postingIdx);

	List<Posting> findByJobTypeContaining(String keyword);
}

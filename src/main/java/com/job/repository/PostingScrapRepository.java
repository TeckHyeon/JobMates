package com.job.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.entity.PostingScrap;

public interface PostingScrapRepository extends JpaRepository<PostingScrap, Long> {
    
    Optional<PostingScrap> findByPostingPostingIdx(Long posting_idx);

	long countByPersonPersonIdxAndPostingPostingIdx(Long personIdx, Long postingIdx);

	void deleteByPostingPostingIdx(Long postingIdx);



	void deleteByPostingPostingIdxAndPersonPersonIdx(Long postingIdx, Long personIdx);

}

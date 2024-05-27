package com.job.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
	
	List<Reply> findReplysByCommunityCommunityIdx(Long communityIdx);

	Long countByCommunityCommunityIdx(Long communityIdx);

	List<Reply> findReplysByCommunityCommunityIdxOrderByCreatedDateDesc(Long communityIdx); 
    
}
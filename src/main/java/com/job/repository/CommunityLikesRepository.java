package com.job.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.job.dto.CommunityLikesDto;
import com.job.entity.CommunityLikes;

public interface CommunityLikesRepository extends JpaRepository<CommunityLikes, Long> {
	
	int countByCommunityCommunityIdxAndUserUserIdx(Long communityIdx, Long userIdx);

	Long countByCommunityCommunityIdx(Long communityIdx);

	void save(CommunityLikesDto like);
	
	void deleteByCommunityCommunityIdxAndUserUserIdx(Long communityIdx, Long userIdx);

}
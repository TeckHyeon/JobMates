package com.job.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.dto.CommunityLikesDto;
import com.job.entity.CommunityLikes;
import com.job.entity.CommunityView;

public interface CommunityViewRepository extends JpaRepository<CommunityView, Long> {

	Long countByCommunityCommunityIdx(Long communityIdx);

	CommunityView findByUserUserIdx(Long userIdx);

	Optional<CommunityView> findByUserUserIdxAndCommunityCommunityIdx(Long userIdx, Long communityIdx);

}
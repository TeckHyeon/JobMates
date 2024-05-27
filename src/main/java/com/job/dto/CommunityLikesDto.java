package com.job.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityLikesDto {
	private Long likesIdx;
	private Long communityIdx;
	private Long userIdx;

}

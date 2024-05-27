package com.job.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityViewDto {
	private Long viewIdx;
	private Long communityIdx;
	private Long userIdx;

}

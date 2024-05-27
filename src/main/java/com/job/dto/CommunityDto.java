package com.job.dto;

import java.time.LocalDateTime;

import com.job.entity.Community;
import com.job.util.TimeAgo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CommunityDto {
	private Long communityIdx;
	private String communityTitle;
	private Long userIdx;
	private String communityName;
	private String communityContent;
	private String createdDate;
	private Long viewCount;
	private Long likeCount;
	private Long replyCount;

	public static CommunityDto createCommunityDtoList(Community community) {
		LocalDateTime createdDateTime = TimeAgo.stringToLocalDateTime(community.getCreatedDate());
        String timeAgo = TimeAgo.calculateTimeAgo(createdDateTime);
		return CommunityDto.builder().communityIdx(community.getCommunityIdx())
				.communityTitle(community.getCommunityTitle()).userIdx(community.getUser().getUserIdx())
				.communityName(community.getCommunityName()).communityContent(community.getCommunityContent())
				.createdDate(timeAgo).viewCount(community.getViewCount()).likeCount(community.getLikeCount()).replyCount(community.getReplyCount()).build();
	}

	public CommunityDto createCommunityDto(Community community) {
		// TODO Auto-generated method stub
		return new CommunityDto(community.getCommunityIdx(), community.getCommunityTitle(),
				community.getUser().getUserIdx(), community.getCommunityName(), community.getCommunityContent(),
				community.getCreatedDate(), community.getViewCount(), community.getLikeCount(),
				community.getReplyCount());
	}
}

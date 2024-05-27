package com.job.dto;

import java.time.LocalDateTime;

import com.job.entity.Reply;
import com.job.util.TimeAgo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDto {
	private Long replyIdx;
	private Long communityIdx;
	private Long userIdx;
	private String replyName;
	private String replyContent;
	private String createdDate;

	public static ReplyDto createReplyDto(Reply reply) {
		LocalDateTime createdDate = TimeAgo.stringToLocalDateTime(reply.getCreatedDate());
		return new ReplyDto(reply.getReplyIdx(), reply.getCommunity().getCommunityIdx(), reply.getUser().getUserIdx(),
				reply.getReplyName(), reply.getReplyContent(), TimeAgo.calculateTimeAgo(createdDate));
	}
}

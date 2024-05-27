package com.job.dto;

import com.job.entity.PostingSkill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostingSkillDto {
	private Long postingSkillIdx;
	private Long skillIdx;
	private Long postingIdx;

	public static PostingSkillDto createSkillDto(PostingSkill postingSkill) {
		// TODO Auto-generated method stub
		return new PostingSkillDto(postingSkill.getPostingSkillIdx(), postingSkill.getSkill().getSkillIdx(),
				postingSkill.getPosting().getPostingIdx());
	}
}

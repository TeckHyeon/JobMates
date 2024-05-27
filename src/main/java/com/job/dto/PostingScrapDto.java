package com.job.dto;

import com.job.entity.PostingScrap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostingScrapDto {   
	private Long pscrapIdx;
	private Long personIdx;
	private Long postingIdx;
	public static PostingScrapDto 
	       createPostingScrapDto(PostingScrap  postingScrap) {
		return new PostingScrapDto (
				postingScrap.getPscrapIdx(),	
				postingScrap.getPerson().getPersonIdx(),	
				postingScrap.getPosting().getPostingIdx()
			);
	}
}

package com.job.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeRecommendDto {
	private Long postingIdx;
	private String postingTitle;
	private String postingDeadline;
	private Long personIdx;
	private String personName;
	private Long resumeIdx;
	private String resumeTitle;
	private Long userIdx;
	
}

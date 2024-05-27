package com.job.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RScrapListDto {
	private String resumeTitle;
	private Long resumeIdx;
	private Long userIdx;
	private Long personIdx;
	private String personName;	
}

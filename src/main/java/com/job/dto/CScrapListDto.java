package com.job.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CScrapListDto {
	private Long personIdx;
	private Long postingIdx;
	private String companyName;
	private String postingTitle;
	private String jobType;
	private String postingDeadline;
}

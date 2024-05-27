package com.job.dto;

import com.job.entity.Apply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplyDto {
	private Long applyIdx;
	private Long postingIdx;
	private Long resumeIdx;
	private String createdDate;
	private Long applyStatus;
	private Long personIdx;
	private Long companyIdx;

	public static ApplyDto createResumeFileDto(Apply apply) {
		return new ApplyDto(apply.getApplyIdx(), apply.getPosting().getPostingIdx(), apply.getResume().getResumeIdx(),
				apply.getCreatedDate(), apply.getApplyStatus(), apply.getPerson().getPersonIdx(),
				apply.getCompany().getCompanyIdx());
	}
}

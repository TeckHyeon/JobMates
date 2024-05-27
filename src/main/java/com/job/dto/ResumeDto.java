package com.job.dto;

import java.util.Date;
import com.job.entity.Resume;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeDto {
	private Long resumeIdx;
	private Long userIdx;
	private String resumeTitle;
	private String portfolio;
	private Long publish;
	private String resumeComment;
	private String createdDate;

	public static ResumeDto createResumeDto(Resume resume) {
		return new ResumeDto(resume.getResumeIdx(), resume.getUser().getUserIdx(), resume.getResumeTitle(),
				resume.getPortfolio(), resume.getPublish(), resume.getResumeComment(), resume.getCreatedDate());
	}

	public void setCreatedDate(Date date) {
		// TODO Auto-generated method stub

	}
}

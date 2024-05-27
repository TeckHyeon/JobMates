package com.job.dto;

import com.job.entity.ResumeFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeFileDto {
	private Long resumeFileIdx;
	private Long resumeIdx;
	private String originalName;
	private String filePath;
	private Long fileSize;
	private String uploadDate;

	public static ResumeFileDto createResumeFileDto(ResumeFile resumeFile) {
		return new ResumeFileDto(resumeFile.getResumeFileIdx(), resumeFile.getResume().getResumeIdx(),
				resumeFile.getOriginalName(), resumeFile.getFilePath(), resumeFile.getFileSize(),
				resumeFile.getUploadDate());

	}
}

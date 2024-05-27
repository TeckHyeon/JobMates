package com.job.dto;

import com.job.entity.CompanyFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyFileDto {   
	private Long companyFileIdx;
	private Long companyIdx;
	private String originalName;
	private String filePath;
	private Long fileSize;
	private String uploadDate;
	
	public static CompanyFileDto createCompanyFileDto(CompanyFile file) {
		// TODO Auto-generated method stub
		return new CompanyFileDto(
				file.getCompanyFileIdx(),
				file.getCompany().getCompanyIdx(),
				file.getOriginalName(),
				file.getFilePath(),
				file.getFileSize(),
				file.getUploadDate()
		    );
	}
}

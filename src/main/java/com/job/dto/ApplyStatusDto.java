package com.job.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplyStatusDto {
	private ApplyDto applyDto;
    private PostingDto postingDto;
    private ResumeDto resumeDto;
    private PersonDto personDto;
    private CompanyDto companyDto;    
    
}

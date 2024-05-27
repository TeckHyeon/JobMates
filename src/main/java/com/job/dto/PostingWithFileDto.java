package com.job.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostingWithFileDto {
	private PostingDto postingDto;
    private CompanyFileDto companyFileDto;
    private CompanyDto companyDto;

    
    
}

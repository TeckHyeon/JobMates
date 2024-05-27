package com.job.dto;

import com.job.entity.Company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDto {

	private Long companyIdx;
	private Long userIdx;
	private String companyName;
	private String companyRepName;
	private String companyPhone;
	private String companyAddress;
	private String companyMgrName;
	private String companyMgrPhone;
	private Long companyEmp;
	private String companySize;
	private String companySector;
	private String companyYear;

	public static CompanyDto createCompanyDto(Company com) {
		// TODO Auto-generated method stub
		return new CompanyDto(com.getCompanyIdx(), com.getUser().getUserIdx(), // userIdx를 올바른 위치로 이동
				com.getCompanyName(), com.getCompanyRepName(), com.getCompanyPhone(), com.getCompanyAddress(),
				com.getCompanyMgrName(), com.getCompanyMgrPhone(), com.getCompanyEmp(), com.getCompanySize(),
				com.getCompanySector(), com.getCompanyYear());
	}


}

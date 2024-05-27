package com.job.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.job.dto.CompanyDto;
import com.job.dto.CompanyFileDto;

@Mapper
public interface CompanyMapper {

	void insertCompany(CompanyDto companyDto);

	void insertCompanyFile(CompanyFileDto companyFile);





}

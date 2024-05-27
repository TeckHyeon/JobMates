package com.job.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.job.dto.CompanyDto;
import com.job.dto.CompanyFileDto;
import com.job.dto.PersonDto;
import com.job.dto.SkillDto;
import com.job.dto.UserDto;

@Mapper
public interface MypageMapper {

	PersonDto getPerson(PersonDto personDto);


	PersonDto getPersonByUserIdx(Long userIdx);

	// userIdx로 personIdx갖고 오기
	Long getPersonIdxByUserIdx(Long userIdx);

	// 유저 skill 갖고오기
	List<SkillDto> getPersonSkill(Long personIdx);

	// 회사 정보 갖고오기
	CompanyDto getCompanyByUserIdx(Long userIdx);
	
	// 회사 사진 갖고오기
	CompanyFileDto getCompanyFile(Long companyIdx);

	// 이메일 들고 오기
	String getEmailByUserIdx(Long userIdx);
	
	// 개인 회원정보 업데이트
	void updatePerson(PersonDto person);

	// 이메일 수정
	void updateEmail(UserDto user);

	// 기업 회원정보 업데이트
	void updateCompany(CompanyDto company);


	// 기업 사진 업데이트
	void updateCompanyFile(CompanyFileDto companyFileDto);


	// 회원탈퇴
	void accountDelete(Long userIdx);




}

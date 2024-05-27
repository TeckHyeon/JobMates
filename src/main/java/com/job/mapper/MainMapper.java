package com.job.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.job.dto.FaqDto;

@Mapper
public interface MainMapper {


	FaqDto getfaq(FaqDto faqDto);

	List<FaqDto> getFaqlist();
	
	
}

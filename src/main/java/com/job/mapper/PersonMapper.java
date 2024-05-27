package com.job.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.job.dto.PersonDto;
import com.job.entity.Person;

@Mapper
public interface PersonMapper {

	void insertPerson(PersonDto personDto);



}

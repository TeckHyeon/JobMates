package com.job.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.job.dto.CompanyDto;
import com.job.dto.PersonDto;
import com.job.dto.UserDto;
import com.job.mapper.PersonMapper;
import com.job.mapper.UserMapper;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PersonMapper personMapper;

   
    @Transactional
    public void registerMember(PersonDto personDto, UserDto userDto, CompanyDto companyDto) {
        personMapper.insertPerson(personDto);
        userMapper.insertUser(userDto);

        
    }





	
    
   
}
    

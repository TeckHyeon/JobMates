package com.job.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.job.dto.CScrapListDto;
import com.job.dto.UserDto;
@Mapper
public interface UserMapper {

	void insertUser(UserDto userDto);

	UserDto login(@Param("userId") String userId,@Param("userPw") String userPw);

	HashMap<String, Object> getUser(UserDto userDto);

	List<CScrapListDto> getUserBookmarks(Long personIdx);
	





}

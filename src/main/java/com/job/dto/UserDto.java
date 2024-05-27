package com.job.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {   
	private Long userIdx;
	private String userId;
	private String userPw;
	private Long userType;
	private String createdDate;
	private String userEmail;
}

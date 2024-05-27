package com.job.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class FaqDto {   
	private Long faqIdx;
	private Long faqType;
	private String question;
	private String answer;
}

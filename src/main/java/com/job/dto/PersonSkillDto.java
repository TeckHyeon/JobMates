package com.job.dto;

import com.job.entity.PersonSkill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonSkillDto {   
	private Long person_skillIdx;
	private Long skillIdx;
	private Long personIdx;
	
	public static PersonSkillDto createSkillDto(PersonSkill personSkill) {
		// TODO Auto-generated method stub
		return new PersonSkillDto(personSkill.getPersonSkillIdx(), personSkill.getSkill().getSkillIdx(),
				personSkill.getPerson().getPersonIdx());
	}
}

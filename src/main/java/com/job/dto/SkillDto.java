package com.job.dto;

import com.job.entity.Skill;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SkillDto {   
	private Long skillIdx;
	private String skillName;
	
	public static SkillDto createSkillDto(Skill skill) {
        SkillDto dto = new SkillDto();
        dto.setSkillIdx(skill.getSkillIdx());
        dto.setSkillName(skill.getSkillName());
        return dto;
    }
}

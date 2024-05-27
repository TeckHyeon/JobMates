package com.job.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.job.dto.PersonDto;
import com.job.dto.PersonSkillDto;
import com.job.dto.PostingDto;
import com.job.dto.PostingSkillDto;
import com.job.dto.ResumeScrapDto;
import com.job.dto.SkillDto;
import com.job.mapper.PostingMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostingService {
	
	@Autowired
	private PostingMapper postingMapper;
	
	@Transactional
    public void postingWrite(PostingDto postingDto, List<Long> skillIdxList) {
        // 시퀀스를 사용하여 postingIdx를 조회
		postingMapper.postingWrite(postingDto); // 공고 정보를 POSTING_TB에 저장
        Long postingIdx = postingMapper.selectNextPostingSeq();
  
        for (Long skillIdx : skillIdxList) {
            PostingSkillDto postingSkillDto = new PostingSkillDto();
            postingSkillDto.setPostingIdx(postingIdx);
            postingSkillDto.setSkillIdx(skillIdx);
            postingMapper.postingSkillWrite(postingSkillDto); // 기술 스택을 POSTING_SKILL_TB에 저장
        }
    }

	// 이력서 체크
	public boolean checkResumeScrap(Long resumeIdx, Long companyIdx) {
	    return postingMapper.checkScrap(resumeIdx, companyIdx) > 0;
	}

	public void addScrap(ResumeScrapDto resumeScrapDto) {
		postingMapper.insertScrap(resumeScrapDto);
		
	}

	public void deleteScrap(ResumeScrapDto resumeScrapDto) {
		postingMapper.deleteScrap(resumeScrapDto);
		
	}




}

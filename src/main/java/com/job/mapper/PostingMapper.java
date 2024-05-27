package com.job.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.job.dto.CompanyDto;
import com.job.dto.CompanyFileDto;
import com.job.dto.PersonDto;
import com.job.dto.PersonSkillDto;
import com.job.dto.PostingDto;
import com.job.dto.PostingRecommendDto;
import com.job.dto.PostingSkillDto;
import com.job.dto.RScrapListDto;
import com.job.dto.ResumeDto;
import com.job.dto.ResumeFileDto;
import com.job.dto.SkillDto;
import com.job.dto.UserDto;
import com.job.dto.ResumeRecommendDto;
import com.job.dto.ResumeScrapDto;

@Mapper
public interface PostingMapper {

	//기업
	
	// 내 공고 리스트
	List<PostingDto> getPostListByUserIdx(Long userIdx);
	
	// 자세히보기 공고 갖고 오기
	PostingDto getPostingByPostingIdx(Long postingIdx);

	// 자세히 보기 페이지 posting_idx로 해당 스킬 갖고오기
	List<SkillDto> getSkillByPostingIdx(Long postingIdx);

	// 자세히보기 회사정보 갖고 오기
	CompanyDto getCompanyByUserIdx(Long userIdx);
	
	// 공고 작성
	void postingWrite(PostingDto postingDto);
	
	// 공고에 스킬 작성
	void postingSkillWrite(PostingSkillDto postingSkill);
	
	// 공고 수정
	void postingUpdate(PostingDto postingDto);
	
	// 공고 스킬 수정
	void postingSkillUpdate(PostingSkillDto postingSkill);


	// 공고 삭제
	void postingDelete(Long postingIdx);
	// 공고 스킬 삭제
	void postingSkillDelete(Long postingIdx);
	// 공고 스크랩한거 삭제
	void postingScrapDelete(Long postingIdx);



	
	// ------------------------
	// 개인
	
	// 내 이력서 리스트
	List<ResumeDto> getResumeListByUserIdx(Long userIdx);
	
	// 이력서 리스트에서 스킬 정보 불러오기
	List<Long> getPersonSkillByPersonIdx(Long personIdx);

	//이력서 작성자 정보 userIdx로 불러오기
	PersonDto getPersonByUserIdx(Long userIdx);

	
	//이력서 작성 
	void rResumeWrite(ResumeDto resumeDto);
	// 이력서 작성(스킬)
	void resumeSkillWrite(PersonSkillDto personSkill);
	

	
	// 이력서 작성시 스킬 들고오기
	List<SkillDto> getAllSkill();
	
	//이력서 삭제
	void resumeDelete(Long resumeIdx);

	//이력서 자세히보기
	void resumeView(Long resumeIdx);

	//이력서 이력서 정보 resumeIdx로 불러오기
	ResumeDto getResumeByResumeIdx(Long resumeIdx);
	
	//이력서 수정
	void resumeUpdate(ResumeDto resumeDto);


	// 이력서 자세히보기 skill_idx로 개인 스킬 들고 오기
	List<SkillDto> getSkillBySkillIdx(Long personIdx);

	// skill_idx로 개인 스킬 들고 오기랑 똑같은건데 join문 안쓴거
	List<SkillDto> getUserSkill(Long personIdx);

	// 스킬 수정용 삭제
	void deletePersonSkill(Long personIdx);
	
	// 스킬 수정용 입력 


	
	
	// 공개여부 상태변경

	void updateResumePublishStatus(ResumeDto resumeDto);
	
	// 이력서 사진 업로드
	void resumeFileWrite(ResumeFileDto resumeFileDto);
	// 이력서 사진 삭제
	void resumeFileDelete(Long resumeIdx);
	// 이력서 사진 갖고 오기
	ResumeFileDto getResumeFile(Long resumeIdx);

	// 이력서 사진 수정
	void updateResumeFile(ResumeFileDto resumeFileDto);

	Long selectNextPostingSeq();

	// 이력서 작성에서 user_idx로 person_idx찾으려고 만든거
	//나중에 삭제해야함
	List<Long> getPeronIdxByUserIdx(Long userIdx);

	void insertSkill(PersonSkillDto personSkillDto);

	//==================추천공고=================================
	
	// userIdx로 personIdx갖고오기
	Long getPersonIdxByUserIdx(Long userIdx);
	// person의 user_idx로 posting_idx를 갖고오기.
	List<PostingRecommendDto> getPostingIdxByPersonUserIdx(Long userIdx);

	
	//==================추천인재=================================
	// userIdx로 내 posting갖고 오기.
	List<Long> getPostingIdxByUserIdx(Long userIdx);

	// posting_skill에 맞는 이력서 및 유저 정보 갖고오기
    List<ResumeRecommendDto> resumeRecommend(Map<String, Object> params);

    // 추천 인재 자세히보기 페이지용 유저 정보 호출
	PersonDto getPersonByPersonIdx(Long personIdx);

	// 유저 아이디로 companyIdx 갖고오기
	Long getCompanyIdxByUserIdx(Long userIdx);

	// 스크랩 체크
	int checkScrap(Long resumeIdx, Long companyIdx);
	// 스크랩 하기
	void insertScrap(ResumeScrapDto resumeScrapDto);
	// 스크랩 삭제
	void deleteScrap(ResumeScrapDto resumeScrapDto);


	//=================관심인재(이력서)=============
	
	// companyIdx로 스크랩한 이력서들 갖고오기. 
	List<RScrapListDto> getRScrapList(Long companyIdx);



	




	



}

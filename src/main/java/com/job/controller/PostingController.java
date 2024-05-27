package com.job.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.job.dto.CompanyDto;
import com.job.dto.PersonDto;
import com.job.dto.PersonSkillDto;
import com.job.dto.PostingDto;
import com.job.dto.PostingRecommendDto;
import com.job.dto.PostingSkillDto;
import com.job.dto.RScrapListDto;
import com.job.dto.ResumeDto;
import com.job.dto.ResumeFileDto;
import com.job.dto.ResumeRecommendDto;
import com.job.dto.ResumeScrapDto;
import com.job.dto.SkillDto;
import com.job.dto.UserDto;
import com.job.mapper.MypageMapper;
import com.job.mapper.PostingMapper;
import com.job.service.PostingService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PostingController {

	@Autowired
	private PostingMapper postingMapper;

	@Autowired
	private PostingService postingService;

	@Autowired
	private MypageMapper mypageMapper;
	
	// ------------기업---------------
	// 공고 관리 페이지
	@GetMapping("/postings")
	public ModelAndView postings(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		UserDto user = (UserDto) session.getAttribute("login");
		Long userIdx = user.getUserIdx();
		if (userIdx == null) {
			// 세션에 user_idx가 없는 경우, 예를 들어 로그인하지 않았을 경우의 처리
			// 로그인 페이지로 리다이렉트하거나 에러 메시지 처리
			return new ModelAndView("redirect:/login");
		}
		CompanyDto company = postingMapper.getCompanyByUserIdx(userIdx);
		
		
		List<PostingDto> posting = postingMapper.getPostListByUserIdx(userIdx);
		Long userType = user.getUserType();
		mv.addObject("company", company);
		mv.addObject("userType", userType);
		mv.addObject("posting", posting);
		mv.setViewName("posting/postings");

		return mv;
	}

	// 공고 자세히보기
	@GetMapping("/postingView")
	public ModelAndView postingView(HttpSession session, @RequestParam("postingIdx") Long postingIdx) {
		ModelAndView mv = new ModelAndView();
		UserDto user = (UserDto) session.getAttribute("login");
		// 스킬 갖고오기
		List<SkillDto> skill = postingMapper.getSkillByPostingIdx(postingIdx);

		// company_tb 갖고오기
		Long userIdx = user.getUserIdx();
		CompanyDto company = postingMapper.getCompanyByUserIdx(userIdx);
		if (company == null) {

			mv.setViewName("redirect:/");
			return mv;
		}


		// posting_tb 갖고오기
		PostingDto posting = postingMapper.getPostingByPostingIdx(postingIdx);
		if (posting == null) {
			mv.setViewName("redirect:/");
			return mv;
		}
		Long userType = user.getUserType();
		mv.addObject("company", company);
		mv.addObject("userType", userType);
		mv.addObject("posting", posting);
		mv.addObject("skill", skill);
		mv.setViewName("posting/postingView");

		return mv;
	}

	// 공고 작성페이지로 이동
	@GetMapping("/postingWriteForm")
	public ModelAndView postingWriteForm(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		UserDto user = (UserDto) session.getAttribute("login");
		// company_tb 갖고오기
		Long userIdx = user.getUserIdx();
		CompanyDto company = postingMapper.getCompanyByUserIdx(userIdx);
		if (company == null) {

			mv.setViewName("redirect:/");
			return mv;
		}
		

		// 스킬 들고 오기
		List<SkillDto> skill = postingMapper.getAllSkill();
		Long userType = user.getUserType();
		mv.addObject("userType", userType);
		mv.addObject("userIdx", userIdx);
		mv.addObject("skill", skill);
		mv.addObject("company", company);
		mv.setViewName("posting/postingWrite");

		return mv;
	}

	// 공고작성
	@PostMapping("/postingWrite")
	public ModelAndView postingWrite(HttpSession session, PostingDto postingDto,
			@RequestParam("skillIdx") List<Long> skillIdxList) {
		ModelAndView mv = new ModelAndView();

		
		UserDto user = (UserDto) session.getAttribute("login");
		
		Long userIdx = user.getUserIdx();
		if (userIdx == null) {
			// 세션에 사용자 정보가 없을 경우 처리
			mv.setViewName("redirect:/login");
			return mv;
		}
		
		postingService.postingWrite(postingDto, skillIdxList);

		mv.setViewName("redirect:/postings");
		return mv;
	}

	// 공고 수정폼 이동
	@GetMapping("/postingEditForm")
	public ModelAndView postingEditForm(HttpSession session, @RequestParam("postingIdx") Long postingIdx,
			PostingSkillDto postingSkill) {
		ModelAndView mv = new ModelAndView();
		UserDto user = (UserDto) session.getAttribute("login");
		Long userIdx = user.getUserIdx();

		// company 정보 들고 오기
		CompanyDto company = postingMapper.getCompanyByUserIdx(userIdx);
		if (company == null) {

			mv.setViewName("redirect:/");
			return mv;
		}

		// posting 들고오기
		PostingDto posting = postingMapper.getPostingByPostingIdx(postingIdx);
		if (posting == null) {
			mv.setViewName("redirect:/");
			return mv;
		}

		// postingIdx에 맞는 skill 갖고오게
		List<SkillDto> userSkills = postingMapper.getSkillByPostingIdx(postingIdx);

		// 모든 스킬 갖고오기
		List<SkillDto> allSkills = postingMapper.getAllSkill();
		Long userType = user.getUserType();
		mv.addObject("userType", userType);
		mv.addObject("company", company);
		mv.addObject("posting", posting);
		mv.addObject("userSkills", userSkills);
		mv.addObject("allSkills", allSkills);
		mv.setViewName("posting/postingEdit");
		return mv;
	}

	// 공고 수정
	@PostMapping("/postingEdit")
	@Transactional
	public ModelAndView postingEdit(HttpSession session, @RequestParam("postingIdx") Long postingIdx,
			PostingDto postingDto, @RequestParam("skillIdx") List<Long> skillIdxList, PostingSkillDto postingSkill) {
		ModelAndView mv = new ModelAndView();

		postingMapper.postingUpdate(postingDto);

		postingMapper.postingSkillDelete(postingIdx);	

		// 선택된 기술 스택들을 반복하여 저장
		// 배열을 for문으로 구현하고, postingIdx를 입력,skillIdx는 화면에서 받아옴.
		// 그 postingSkill(PostingSkillDto)를 토대로 매퍼를 통해 수정(새로운 스킬 입력)
		for (Long skillIdx : skillIdxList) {
			postingSkill.setPostingIdx(postingIdx);
			postingSkill.setSkillIdx(skillIdx); // 추가된 라인
			postingMapper.postingSkillUpdate(postingSkill);
		}

		mv.setViewName("redirect:/postings");
		return mv;
	}

	// 공고 삭제
	@RequestMapping("/postingDelete")
	@Transactional
	public ModelAndView postingDelete(PostingDto postingDto) {
		ModelAndView mv = new ModelAndView();

		Long postingIdx = postingDto.getPostingIdx();
		
		postingMapper.postingScrapDelete(postingIdx);
		postingMapper.postingSkillDelete(postingIdx);
		postingMapper.postingDelete(postingIdx);
		mv.setViewName("redirect:/postings");
		return mv;
	}

	// ------------------------------------------------------기업---------------------------------------------------------
	// -------------------------------------------------------------------------------------------------------------------
	// ------------------------------------------------------개인---------------------------------------------------------

	// 이력서 관리 페이지
	@GetMapping("/resumes")
	public ModelAndView resumes(HttpSession session) {

		UserDto user = (UserDto) session.getAttribute("login");
		Long userIdx = user.getUserIdx();
		if (userIdx == null) {
			// 세션에 user_idx가 없는 경우, 예를 들어 로그인하지 않았을 경우의 처리
			// 로그인 페이지로 리다이렉트하거나 에러 메시지 처리
			return new ModelAndView("redirect:/login");
		}
		Long personIdx = postingMapper.getPersonIdxByUserIdx(userIdx);
		List<Long> personSkill = postingMapper.getPersonSkillByPersonIdx(personIdx);
		
		
		// userIdx를 사용해서 해당 사용자의 이력서 리스트를 조회
		List<ResumeDto> resumeList = postingMapper.getResumeListByUserIdx(userIdx);

		ModelAndView mv = new ModelAndView();
		Long userType = user.getUserType();
		
		mv.addObject("personSkill", personSkill);
		mv.addObject("userType", userType);
		mv.addObject("resumes", resumeList);
		mv.setViewName("posting/resumes");

		return mv;
	}

	// 이력서 자세히보기
	@GetMapping("/resumeView")
	public ModelAndView resumeView(HttpSession session, @RequestParam("resumeIdx") Long resumeIdx) {

		ModelAndView mv = new ModelAndView();

		// person_tb 정보 갖고오기
		UserDto user = (UserDto) session.getAttribute("login");
		Long userIdx = user.getUserIdx();
		PersonDto person = postingMapper.getPersonByUserIdx(userIdx);
		if (person == null) {
			// 사용자 정보가 없을 경우 처리
			mv.setViewName("redirect:/");
			return mv;
		}

		// personIdx가 같은 정보를 찾기 위해 세션에서 갖고옴
		Long personIdx = person.getPersonIdx();

		// skillIdx를 토대로 skill 갖고오기
		List<SkillDto> skill = postingMapper.getSkillBySkillIdx(personIdx);

		// Resume_tb 정보 갖고 오기
		ResumeDto resume = postingMapper.getResumeByResumeIdx(resumeIdx);
		if (resume == null) {
			// 사용자 정보가 없을 경우 처리
			mv.setViewName("redirect:/");
			return mv;
		}

		ResumeFileDto resumeFile = postingMapper.getResumeFile(resumeIdx);
		Long userType = user.getUserType();
		
		mv.addObject("userType", userType);
		mv.addObject("person", person);
		mv.addObject("resume", resume);
		mv.addObject("skill", skill);
		mv.addObject("resumeFile", resumeFile);
		mv.setViewName("posting/resumeView");
		return mv;
	}

	// 이력서 공개여부 변경
	@PostMapping("/resumes/togglePublish")
	public ResponseEntity<?> togglePublishStatus(@RequestBody ResumeDto resumeDto) {
		Long resumeIdx = resumeDto.getResumeIdx();
		Long currentStatus = resumeDto.getPublish();
		log.info("resumeDto = {}", resumeDto);

		// 이력서 상태 변경 로직 (예: currentStatus가 1이면 2로, 2면 1로)
		Long publish = (long) ((currentStatus == 1L) ? 2L : 1L);
		resumeDto.setPublish(publish);

		// 이력서 상태 업데이트를 위한 서비스 또는 매퍼 호출
		postingMapper.updateResumePublishStatus(resumeDto);

		try {

			// 새 상태와 메시지를 포함한 응답 반환
			Map<String, Object> response = new HashMap<>();
			response.put("newStatus", publish);
			response.put("message", "상태가 성공적으로 변경되었습니다.");

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			// 오류 처리
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("상태 변경 중 오류가 발생했습니다.");
		}
	}

	// 이력서 작성페이지로 이동
	// 이력서에 담길 정보들을 갖고 이동
	@GetMapping("/resumeWriteForm")
	public ModelAndView resumeWriteForm(HttpSession session) {

		ModelAndView mv = new ModelAndView();

		// 세션에서 user_idx 가져오기
		UserDto user = (UserDto) session.getAttribute("login");
		Long userIdx = user.getUserIdx();
		if (userIdx == null) {
			// 세션에 사용자 정보가 없을 경우 처리
			mv.setViewName("posting/error");
			return mv;
		}

		// userIdx를 사용하여 해당 사용자의 정보 조회
		PersonDto person = postingMapper.getPersonByUserIdx(userIdx);
		if (person == null) {
			// 사용자 정보가 없을 경우 처리
			mv.setViewName("redirect:/");
			return mv;
		}
		
		Long personIdx = person.getPersonIdx();

		
		// skillIdx를 토대로 skill 갖고오기
		List<SkillDto> skill = postingMapper.getSkillBySkillIdx(personIdx);

		Long userType = user.getUserType();
		mv.addObject("userType", userType);
		mv.addObject("person", person);
		mv.addObject("skill", skill);
		mv.setViewName("posting/resumeWrite");

		return mv;
	}

	// 이력서 작성
	@PostMapping("/resumeWrite")
	@Transactional
	public ModelAndView resumwWrite(HttpSession session, ResumeDto resumeDto,
			ResumeFileDto resumeFileDto, @RequestParam("file") MultipartFile file) {
		ModelAndView mv = new ModelAndView();

		UserDto user = (UserDto) session.getAttribute("login");
		Long userIdx = user.getUserIdx();
		if (userIdx == null) {
			// 세션에 사용자 정보가 없을 경우 처리
			mv.setViewName("redirect:/login");
			return mv;
		}
		PersonDto persondto = mypageMapper.getPersonByUserIdx(userIdx);
		
		Long personIdx = persondto.getPersonIdx();
		// 생성 날짜를 현재 날짜로 설정
		resumeDto.setCreatedDate(new Date());

		// resumeDto에 현재 세션의 userIdx를 넣음
		resumeDto.setUserIdx(userIdx);

		// personSkillDto에 현재 세션의 personIdx를 넣음

		postingMapper.rResumeWrite(resumeDto);


		// 파일이 업로드되지 않았을 경우 처리
		if (file.isEmpty()) {
			mv.addObject("error", "파일을 선택하세요.");
			mv.setViewName("posting/resumeWrite");
			return mv;
		}

		// 이력서 파일 이름
		String fileName = file.getOriginalFilename();
		// 암호환 파일 이름 중복방지(그냥 시간 앞에 붙임)
		String fileNameScret = System.currentTimeMillis() + "_" + fileName;

		String filePath = "C:/dev/" + fileNameScret;
		Long fileSize = file.getSize();
		// 파일 해당 위치에 저장
		File dest = new File("C:/dev/images/"+fileNameScret);
		// 만약 해당 위치에 폴더가 없으면 생성
		if (!dest.exists()) {
			dest.mkdirs();
        }
		try {
			// 파일을 지정된 경로로 저장
			file.transferTo(dest);

			// 데이터베이스에 저장할 이미지 경로 설정
			resumeFileDto.setFilePath("/images/" + fileNameScret);
			resumeFileDto.setOriginalName(fileName);
			resumeFileDto.setFileSize(fileSize);
			// 이후 데이터베이스에 저장(경로로 저장)

			postingMapper.resumeFileWrite(resumeFileDto);

		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			mv.addObject("error", "파일 업로드 실패.");
			mv.setViewName("posting/resumeWrite");
			return mv;
		}

		mv.setViewName("redirect:/resumes");
		return mv;
	}
	
	

	// 이력서 수정폼 이동
	@GetMapping("/resumeEditForm")
	public ModelAndView resumeEditForm(HttpSession session, @RequestParam("resumeIdx") Long resumeIdx,
			PersonSkillDto personSkill) {
		ModelAndView mv = new ModelAndView();
		UserDto user = (UserDto) session.getAttribute("login");
		Long userIdx = user.getUserIdx();

		// 이력서에 사용된 person_tb 갖고오기
		PersonDto person = postingMapper.getPersonByUserIdx(userIdx);
		if (person == null) {
			// 사용자 정보가 없을 경우 처리
			mv.setViewName("redirect:/");
			return mv;
		}

		// personIdx가 같은 정보를 찾기 위해 세션에서 갖고옴
		Long personIdx = person.getPersonIdx();
		
		// skillIdx를 토대로 skill 갖고오기
		List<SkillDto> skill = postingMapper.getSkillBySkillIdx(personIdx);
		
		
		// 이력서에 사용된 resume_tb 갖고 오기
		ResumeDto resume = postingMapper.getResumeByResumeIdx(resumeIdx);
		if (resume == null) {
			// 사용자 정보가 없을 경우 처리
			mv.setViewName("redirect:/");
			return mv;
		}
		ResumeFileDto resumeFile = postingMapper.getResumeFile(resumeIdx);
		
		Long userType = user.getUserType();
		
		
		mv.addObject("userType", userType);
		mv.addObject("person", person);
		mv.addObject("resume", resume);
		mv.addObject("skill", skill);
		mv.addObject("resumeIdx", resumeIdx);
		mv.addObject("resumeFile", resumeFile);
		mv.setViewName("posting/resumeEdit");
		return mv;

	}

	// 이력서 수정
	@PostMapping("/resumeEdit")
	@Transactional
	public ModelAndView resumeEdit(HttpSession session, 
			@RequestParam("resumeIdx") Long resumeIdx, ResumeDto resumeDto, PersonDto person,
			ResumeFileDto resumeFileDto, 
			@RequestParam("file") MultipartFile file) {
		
		ModelAndView mv = new ModelAndView();

		UserDto user = (UserDto) session.getAttribute("login");
		postingMapper.resumeUpdate(resumeDto);
		

		// 세션에서 user_idx 갖고오기
		Long userIdx = user.getUserIdx();
		
		
		// 파일 수정부분

		if (!file.isEmpty()) {
			// 새 파일이 업로드되었을 경우
			String fileName = file.getOriginalFilename();
			String fileNameScret = System.currentTimeMillis() + "_" + fileName;
			String filePath = "C:/dev/" + fileNameScret;
			Long fileSize = file.getSize();

			File dest = new File("C:/dev/images/"+fileNameScret);
			if (!dest.exists()) {
				dest.mkdirs();
	        }

			try {
				// 파일을 지정된 경로로 저장
				file.transferTo(dest);
				resumeFileDto.setResumeIdx(resumeIdx);
				resumeFileDto.setOriginalName(fileName);
				resumeFileDto.setFileSize(fileSize);
				resumeFileDto.setFilePath("/images/"+fileNameScret);


				// 여기서 새 파일 정보로 업데이트
				postingMapper.updateResumeFile(resumeFileDto);

			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		} else {
			// 새 파일이 업로드되지 않았을 경우 예전 파일 정보 유지
		}

		mv.setViewName("redirect:/resumes");

		return mv;
	}

	// 이력서 삭제
	@RequestMapping("/resumeDelete")
	@Transactional
	public ModelAndView deleteResume(ResumeDto resumeDto) {

		ModelAndView mv = new ModelAndView();

		Long resumeIdx = resumeDto.getResumeIdx();

		postingMapper.resumeFileDelete(resumeIdx);
		postingMapper.resumeDelete(resumeIdx);

		mv.setViewName("redirect:/resumes");
		return mv;
	}



	// ==================================================================
	// == 개인유저가 보는 추천 공고(스킬 기반)
	
		//추천 공고 페이지 이동
		@GetMapping("/postingRecommend")
		public ModelAndView postingRecommend(HttpSession session,PersonDto personDto,PersonSkillDto personSkill) {
			ModelAndView mv = new ModelAndView();

			// 세션에서 로그인 정보 불러오기.
			UserDto user = (UserDto) session.getAttribute("login");
			Long userIdx = user.getUserIdx();
			if (userIdx == null) {
				// 세션에 사용자 정보가 없을 경우 처리
				mv.setViewName("posting/error");
				return mv;
			}
			Long userType = user.getUserType();

			// userIdx로 로그인한 사람 personIdx 갖고 오기
			Long personIdx = postingMapper.getPersonIdxByUserIdx(userIdx);
			personDto.setPersonIdx(personIdx);
			
			// 불러온 userIdx로 person의 skill을 갖고오고
			// person의 skill과 posting의 skill이 1개 이상 일치하는 공고 posting_idx를 갖고 오고
			// 해당 postingIdx로 company_name 들고오기
			List<PostingRecommendDto> postingList = postingMapper.getPostingIdxByPersonUserIdx(userIdx);
			
			// 위에서 찾은 회사 user_idx(세션에 있는 user_idx가 아님)로 file_path 찾아오기

			
			mv.addObject("person", personDto);
			mv.addObject("userType", userType);
			mv.addObject("posting",postingList);
			mv.setViewName("posting/postingRecommend");
			return mv;
		} 

	// ==================================================================
	// == 기업 유저가 보는 추천 이력서(스킬 기반)
		
		@GetMapping("/resumeRecommend")
		public ModelAndView resumeRecommend(HttpSession session, ResumeDto resumeDto, UserDto userDto) {
		    ModelAndView mv = new ModelAndView();
		    
		    UserDto user = (UserDto) session.getAttribute("login");
		    Long userType = user.getUserType();
		    Long userIdx = user.getUserIdx();
		    Long companyIdx = postingMapper.getCompanyIdxByUserIdx(userIdx);

		    
		    // user_idx로 posting_idx 갖고 옴
		    List<Long> postingIdxList = postingMapper.getPostingIdxByUserIdx(userIdx);
		    // posting_idx에 값을 설정하기 위해서 hashmap 씀 
		    Map<Long, List<ResumeRecommendDto>> resumeRecMap = new HashMap<>();
		    
		    // 배열에 postingIdx 넣기
		    for (Long postingIdx : postingIdxList) {
		        Map<String, Object> params = new HashMap<>();
		        params.put("postingIdx", postingIdx);
		        params.put("userIdx", userIdx);
		        List<ResumeRecommendDto> resumeRecs = postingMapper.resumeRecommend(params);
		        resumeRecMap.put(postingIdx, resumeRecs);
		    }
		    
			mv.addObject("companyIdx", companyIdx);
		    mv.addObject("resumeRecMap", resumeRecMap);
		    mv.addObject("userType", userType);
		    mv.setViewName("posting/resumeRecommend");
		    return mv;
		}

		
		// 추천 이력서 보기(기업이 보는 이력서)
		@GetMapping("/resumeRecommendView")
		public ModelAndView resumeRecommendView(HttpSession session,PersonDto person,@RequestParam("personIdx") Long personIdx,@RequestParam("resumeIdx") Long resumeIdx) {
			ModelAndView mv = new ModelAndView();
			
			UserDto user = (UserDto) session.getAttribute("login");
		    Long userType = user.getUserType();
		    Long userIdx = user.getUserIdx();
		    Long companyIdx = postingMapper.getCompanyIdxByUserIdx(userIdx);
		    
		    person = postingMapper.getPersonByPersonIdx(personIdx);
		    
		    // 개인유저 스킬 갖고오기 
		    List<SkillDto> skill = postingMapper.getSkillBySkillIdx(personIdx);

			// Resume_tb 정보 갖고 오기
			ResumeDto resume = postingMapper.getResumeByResumeIdx(resumeIdx);
			if (resume == null) {
				// 사용자 정보가 없을 경우 처리
				mv.setViewName("redirect:/");
				return mv;
			}

			ResumeFileDto resumeFile = postingMapper.getResumeFile(resumeIdx);
			
			mv.addObject("companyIdx", companyIdx);
		    mv.addObject("userType", userType);
		    mv.addObject("person", person);
		    mv.addObject("skill", skill);
		    mv.addObject("resume", resume);
		    mv.addObject("resumeFile", resumeFile);
			mv.setViewName("posting/resumeRecommendView");

			return mv;
		}
	
		// ================ 이력서 스크랩 =====================
		
		// 스크랩 상태 확인
		@GetMapping("/checkResumeScrap")
		public ResponseEntity<Boolean> checkScrap(@RequestParam("resumeIdx") Long resumeIdx, @RequestParam("companyIdx") Long companyIdx) {
		    boolean isScraped = postingService.checkResumeScrap(resumeIdx, companyIdx);
		    return ResponseEntity.ok(isScraped);
		}
		
	    // 스크랩 추가
	    @PostMapping("/resumeScrapAdd")
	    @ResponseBody
	    public Map<String, Object> addScrap(@RequestBody ResumeScrapDto resumeScrapDto) {
	        Map<String, Object> response = new HashMap<>();
	        try {
	            postingService.addScrap(resumeScrapDto);
	            response.put("success", true);
	        } catch (Exception e) {
	            response.put("success", false);
	            response.put("message", e.getMessage());
	        }
	        return response;
	    }

	    // 스크랩 삭제
	    @DeleteMapping("/resumeScrapDelete")
	    @ResponseBody
	    public Map<String, Object> deleteScrap(@RequestBody ResumeScrapDto resumeScrapDto) {
	        Map<String, Object> response = new HashMap<>();
	        try {
	            postingService.deleteScrap(resumeScrapDto);
	            response.put("success", true);
	        } catch (Exception e) {
	            response.put("success", false);
	            response.put("message", e.getMessage());
	        }
	        return response;
	    }
	    
	    
	    // =============스크랩한 인재 페이지============================
	    // 스크랩한 공고 리스트
	    @GetMapping("/RScrapList")
	    public ModelAndView RScrapList(HttpSession session) {
	    	ModelAndView mv = new ModelAndView();
	    	
			UserDto user = (UserDto) session.getAttribute("login");
		    Long userType = user.getUserType();
		    Long userIdx = user.getUserIdx();
		    Long companyIdx = postingMapper.getCompanyIdxByUserIdx(userIdx);
		    
		    // 스크랩한 리스트 갖고오기
		    List<RScrapListDto> RScrapList = postingMapper.getRScrapList(companyIdx);
		    
		    
		    mv.addObject("companyIdx",companyIdx);
		    mv.addObject("RScrapList",RScrapList);
		    mv.addObject("userType",userType);
	    	mv.setViewName("posting/RScrapList");
	    	return mv; 	
	    }
	    
	    @GetMapping("/RScrapView")
		public ModelAndView RScrapView(HttpSession session,PersonDto person,@RequestParam("personIdx") Long personIdx,@RequestParam("resumeIdx") Long resumeIdx) {
			ModelAndView mv = new ModelAndView();
			
			UserDto user = (UserDto) session.getAttribute("login");
		    Long userType = user.getUserType();
		    Long userIdx = user.getUserIdx();
		    Long companyIdx = postingMapper.getCompanyIdxByUserIdx(userIdx);
		    
		    person = postingMapper.getPersonByPersonIdx(personIdx);
		    
		    // 개인유저 스킬 갖고오기 
		    List<SkillDto> skill = postingMapper.getSkillBySkillIdx(personIdx);

			// Resume_tb 정보 갖고 오기
			ResumeDto resume = postingMapper.getResumeByResumeIdx(resumeIdx);
			if (resume == null) {
				// 사용자 정보가 없을 경우 처리
				mv.setViewName("redirect:/");
				return mv;
			}

			ResumeFileDto resumeFile = postingMapper.getResumeFile(resumeIdx);
			
			mv.addObject("companyIdx", companyIdx);
		    mv.addObject("userType", userType);
		    mv.addObject("person", person);
		    mv.addObject("skill", skill);
		    mv.addObject("resume", resume);
		    mv.addObject("resumeFile", resumeFile);
			mv.setViewName("posting/RScrapView");

			return mv;
		}
	    
	    
}
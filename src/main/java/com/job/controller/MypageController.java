package com.job.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.job.dto.CompanyDto;
import com.job.dto.CompanyFileDto;
import com.job.dto.PersonDto;
import com.job.dto.PersonSkillDto;
import com.job.dto.SkillDto;
import com.job.dto.UserDto;
import com.job.mapper.MypageMapper;
import com.job.mapper.PostingMapper;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MypageController {
	@Autowired
	private MypageMapper mypageMapper;
	
	@Autowired
	private PostingMapper postingMapper;
	
	// 마이페이지
	@RequestMapping("/mypage")
	public ModelAndView mypage(HttpSession session) {
		UserDto user = (UserDto) session.getAttribute("login");
		Long userType = user.getUserType();

		ModelAndView mv = new ModelAndView();
		Boolean isLoggedInObj = (Boolean) session.getAttribute("isLoggedIn");
		boolean isLoggedIn = isLoggedInObj != null && isLoggedInObj.booleanValue();
		
		if (isLoggedIn) {
			if (user != null) {
				if (userType == 2) {
					Long userIdx = user.getUserIdx();
					String userId = user.getUserId();
					String userEmail = mypageMapper.getEmailByUserIdx(userIdx);

					PersonDto person = mypageMapper.getPersonByUserIdx(userIdx);
					
					Long personIdx = person.getPersonIdx();

					List<SkillDto> skill = mypageMapper.getPersonSkill(personIdx);


					mv.addObject("userEmail", userEmail);
					mv.addObject("skill", skill);
					mv.addObject("userId", userId);
					mv.addObject("person", person);
					mv.addObject("userType", userType);
					mv.setViewName("section/mypage");

					return mv;
				}

				if (userType == 1) {
					
					 Long userIdx = user.getUserIdx(); 
					 String userId = user.getUserId();
					 String userEmail = mypageMapper.getEmailByUserIdx(userIdx);
					 
					CompanyDto company = mypageMapper.getCompanyByUserIdx(userIdx);
					 
					Long companyIdx = company.getCompanyIdx();
					
					CompanyFileDto companyFile = mypageMapper.getCompanyFile(companyIdx);

					 
					 mv.addObject("userEmail", userEmail);
					 mv.addObject("companyFile", companyFile);
					 mv.addObject("userId", userId);
					 mv.addObject("company",company);					 
					 mv.addObject("userType", userType);
					 mv.setViewName("section/mypageCompany"); 
					 
					 return mv;
					
				}

			}
		} else {
			mv.setViewName("redirect:/personlogin");
			return mv;
		}

		return mv;
	}
	
	
	
	// 개인회원 수정페이지 이동
	@RequestMapping("/mypageUpdateForm")
	public ModelAndView mypageUpdateForm(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		
		UserDto user = (UserDto) session.getAttribute("login");
		Long userType = user.getUserType();
		Long userIdx = user.getUserIdx();
		String userEmail = mypageMapper.getEmailByUserIdx(userIdx);


		
		String userId = user.getUserId();
		
		PersonDto person = mypageMapper.getPersonByUserIdx(userIdx);
		
		Long personIdx = person.getPersonIdx();

		// skillIdx를 토대로 skill 갖고오기
		// 시간 없어서 그냥 원래 있던 postingMapper 연결함
		List<SkillDto> userSkills = postingMapper.getUserSkill(personIdx);
		
		
		// 모든 skill_tb에 있는 모든 skill
		// 시간 없어서 그냥 원래 있던 postingMapper 연결함
		List<SkillDto> allSkills = postingMapper.getAllSkill();



		mv.addObject("allSkills", allSkills);
		mv.addObject("userSkills", userSkills);
		mv.addObject("userIdx", userIdx);
		mv.addObject("userId", userId);
		mv.addObject("person", person);
		mv.addObject("userType", userType);
		mv.addObject("userEmail",userEmail);
		mv.setViewName("section/mypageUpdateForm");

		return mv;

	}
	
	
	// 개인회원 정보 수정
	@PostMapping("/mypageUpdate")
	public ModelAndView mypageUpdate(UserDto user, PersonDto person, @RequestParam("skillIdx") List<Long> skillIdxList) {
	    ModelAndView mv = new ModelAndView();
	    
	    
		Long personIdx = person.getPersonIdx();

	    mypageMapper.updateEmail(user);
	    mypageMapper.updatePerson(person);
	    

        postingMapper.deletePersonSkill(personIdx);
	
		for (Long skillIdx : skillIdxList) {
			PersonSkillDto personSkillDto = new PersonSkillDto();
			personSkillDto.setPersonIdx(personIdx); // 사용자 ID 설정
			personSkillDto.setSkillIdx(skillIdx); // 스킬 인덱스 설정
			postingMapper.insertSkill(personSkillDto); // 스킬 정보 삽입
		}
    
	    
	    mv.setViewName("redirect:/mypage");
	    return mv;
	}
	
	
	
	// 기업회원 수정페이지 이동
	@RequestMapping("/mypageCompanyUpdateForm")
	public ModelAndView mypageCompanyUpdateForm(HttpSession session) {
		ModelAndView mv = new ModelAndView();

		 UserDto user = (UserDto) session.getAttribute("login");
		
		 Long userType = user.getUserType();
		 Long userIdx = user.getUserIdx(); 
		 String userId = user.getUserId();
		 String userEmail = mypageMapper.getEmailByUserIdx(userIdx);
		 
				 
		 CompanyDto company = mypageMapper.getCompanyByUserIdx(userIdx);
		 Long companyIdx = company.getCompanyIdx();
		 
		 
		CompanyFileDto companyFile = mypageMapper.getCompanyFile(companyIdx);

		 
		 mv.addObject("companyFile", companyFile);
		 mv.addObject("userEmail", userEmail);
		 mv.addObject("userId", userId);
		 mv.addObject("userIdx", userIdx);
		 mv.addObject("company",company);
		 mv.addObject("companyIdx", companyIdx);
		 mv.addObject("userType", userType);
		 mv.setViewName("section/mypageCompanyUpdateForm");
		
		return mv;
	} 
	
	// 기업회원 정보 수정
	@PostMapping("/mypageCompanyUpdate")
	@Transactional
	public ModelAndView mypageCompanyUpdate(UserDto user, CompanyDto company,CompanyFileDto companyFileDto, 
			@RequestParam("file") MultipartFile file,@RequestParam("companyIdx") Long companyIdx) {
		ModelAndView mv = new ModelAndView();
		

		 mypageMapper.updateEmail(user);
		 mypageMapper.updateCompany(company);
		 
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
					companyFileDto.setCompanyIdx(companyIdx);
					companyFileDto.setOriginalName(fileName);
					companyFileDto.setFileSize(fileSize);
					companyFileDto.setFilePath("/images/"+fileNameScret);


					// 여기서 새 파일 정보로 업데이트
					mypageMapper.updateCompanyFile(companyFileDto);

				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			} else {
				// 새 파일이 업로드되지 않았을 경우 예전 파일 정보 유지
			}
		 


	    mv.setViewName("redirect:/mypage");
		return mv;

	}
	
	//회원탈퇴 폼으로 이동
	@GetMapping("/accountDeleteForm")
	public ModelAndView accountDeleteForm(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		UserDto user = (UserDto) session.getAttribute("login");
		Long userType = user.getUserType();
		Long userIdx = user.getUserIdx();
		String userId = user.getUserId();
		
		mv.addObject("userId", userId);
		mv.addObject("userIdx", userIdx);
		mv.addObject("userType", userType);
		mv.setViewName("section/accountDeleteForm");
		return mv;
	} 
	
	@RequestMapping("/accountDelete")
	public ModelAndView accountDelete(HttpSession session,@RequestParam("userIdx") Long userIdx) {
		ModelAndView mv = new ModelAndView();
		
		mypageMapper.accountDelete(userIdx);
		session.invalidate();

		mv.setViewName("redirect:/");
		return mv;
	}
	

}


















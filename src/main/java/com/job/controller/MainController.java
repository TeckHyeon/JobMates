package com.job.controller;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.job.dto.ApplyDto;
import com.job.dto.ApplyStatusDto;
import com.job.dto.CommunityDto;
import com.job.dto.CommunityLikesDto;
import com.job.dto.CommunityViewDto;
import com.job.dto.CompanyDto;
import com.job.dto.FaqDto;
import com.job.dto.PersonDto;
import com.job.dto.PostingDto;
import com.job.dto.PostingScrapDto;
import com.job.dto.PostingWithFileDto;
import com.job.dto.ReplyDto;
import com.job.dto.ResumeDto;
import com.job.dto.ResumeFileDto;
import com.job.dto.SkillDto;
import com.job.dto.UserDto;
import com.job.mapper.MainMapper;
import com.job.service.MainService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {

	@Autowired
	private MainService mainService;

	@Autowired
	private MainMapper mainMapper;

	@GetMapping("/")
	public ModelAndView main(HttpSession session) {
		ModelAndView mv = new ModelAndView("section/main");
		UserDto user = (UserDto) session.getAttribute("login");

		Boolean isLoggedInObj = (Boolean) session.getAttribute("isLoggedIn");
		boolean isLoggedIn = isLoggedInObj != null && isLoggedInObj.booleanValue();
		
		if (isLoggedIn) {
			if (user != null) {
				Long userType = user.getUserType();
				log.info("user = {}", user);
				if (userType == 1) {
					Long userIdx = user.getUserIdx();
					List<PostingWithFileDto> lists = mainService.findPostingsByUserIdx(userIdx);
					log.info("lists = {}", lists);
					mv.addObject("userType", userType);
					mv.addObject("posts", lists);
					return mv;
				} else {
					Long userIdx = user.getUserIdx();
					PersonDto person = mainService.findPersonByUserIdx(userIdx);
					log.info("person = {}", person);
					List<PostingWithFileDto> lists = mainService.findAllPosting();
					List<SkillDto> skillList = mainService.findAllSkills();
					log.info("lists = {}", lists);
					mv.addObject("person", person);
					mv.addObject("userType", userType);
					mv.addObject("skills", skillList);
					mv.addObject("posts", lists);
					return mv;
				}
			}
		} else {
			List<PostingWithFileDto> lists = mainService.findAllPosting();
			log.info("lists = {}", lists);
			List<SkillDto> skillList = mainService.findAllSkills();
			mv.addObject("skills", skillList);
			mv.addObject("posts", lists);
			return mv;
		}
		return mv;
	}

	@GetMapping("/searchJobType")
	public ModelAndView searchJobType(@RequestParam("keyword") String keyword) {
		ModelAndView mv = new ModelAndView("fragment/searchBox");
		List<PostingDto> lists = mainService.findByKeyword(keyword);

		// 빈 리스트 생성
		List<PostingDto> results = lists != null ? lists : Collections.emptyList();

		// 결과 리스트 추가
		mv.addObject("results", results);

		if (keyword != null) {
			mv.addObject("isSearched", true);
		}
		return mv;
	}

	@GetMapping("/searchResult")
	public ModelAndView searchResult(@RequestParam("region") String region,
			@RequestParam("experience") String experience, @RequestParam("selectedSkills") List<Long> selectedSkills,
			@RequestParam("selectedJobs") List<String> selectedJobs, @RequestParam("keyword") String keyword, HttpSession session) {
		ModelAndView mv = new ModelAndView("fragment/postResult");
		log.info("selectedSkills = {}", selectedSkills);
		List<PostingWithFileDto> lists = mainService.findPostingBySearchResult(region, experience, selectedSkills,
				selectedJobs, keyword);
		mv.addObject("posts", lists);
		log.info("lists = {}", lists);

		List<SkillDto> skillList = mainService.findAllSkills();
		log.info("skills = {}", skillList);
		mv.addObject("skills", skillList);

		Boolean isLoggedInObj = (Boolean) session.getAttribute("isLoggedIn");
		boolean isLoggedIn = isLoggedInObj != null && isLoggedInObj.booleanValue();
		UserDto user = (UserDto) session.getAttribute("login");
		if (isLoggedIn) {
			if (user != null) {
				Long userType = user.getUserType();
				log.info("user = {}", user);
				if (userType == 2) {
					Long userIdx = user.getUserIdx();
					PersonDto person = mainService.findPersonByUserIdx(userIdx);
					mv.addObject("person", person);
				}
			}
		}
		return mv;
	}

	@PostMapping("/scrapAdd")
	public ResponseEntity<?> addScrap(@RequestBody PostingScrapDto postingScrapDto) {
		try {
			mainService.insertPostingScrap(postingScrapDto);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("스크랩 추가에 실패했습니다.");
		}
	}

	@DeleteMapping("/scrapDelete")
	public ResponseEntity<?> deleteScrap(@RequestBody PostingScrapDto postingScrapDto) {
		try {
			mainService.deleteScrap(postingScrapDto);
			log.info("postingScrapDto = {}", postingScrapDto);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.info("postingScrapDto = {}", postingScrapDto);
			log.error("스크랩 삭제 처리 중 오류 발생", e); // 로그 출력 예
			return ResponseEntity.badRequest().body("스크랩 삭제에 실패했습니다.");
		}
	}

	@GetMapping("/checkScrap")
	public ResponseEntity<?> checkScrap(@RequestParam("postingIdx") Long postingIdx,
			@RequestParam("personIdx") Long personIdx) {
		Long scarapCount = mainService.countPostingScrap(personIdx, postingIdx);
		try {

			if (scarapCount != 0) {
				boolean isScraped = true;
				return ResponseEntity.ok(isScraped);
			} else {
				boolean isScraped = false;
				return ResponseEntity.ok(isScraped);
			}

		} catch (Exception e) {
			return ResponseEntity.badRequest().body("스크랩 상태 확인에 실패했습니다.");
		}
	}

	@GetMapping("/mainPosting/{postingIdx}")
	public ModelAndView mainPosting(@PathVariable("postingIdx") Long postingIdx, HttpSession session) {
		ModelAndView mv = new ModelAndView("section/mainPosting");
		UserDto user = (UserDto) session.getAttribute("login");

		Boolean isLoggedInObj = (Boolean) session.getAttribute("isLoggedIn");
		boolean isLoggedIn = isLoggedInObj != null && isLoggedInObj.booleanValue();

		if (isLoggedIn) {
			if (user != null) {
				Long userType = user.getUserType();
				Long userIdx = user.getUserIdx();
				PersonDto person = mainService.findPersonByUserIdx(userIdx);
				PostingDto posting = mainService.findPostingByPostingIdx(postingIdx);
				Long postingUserIdx = posting.getUserIdx();
				CompanyDto company = mainService.findCompanyByUserIdx(postingUserIdx);
				List<SkillDto> skills = mainService.findSkillListByPostingIdx(postingIdx);
				mv.addObject("userType", userType);
				mv.addObject("company", company);
				mv.addObject("person", person);
				mv.addObject("posting", posting);
				mv.addObject("skills", skills);
				log.info("company = {}", company);
				log.info("posting = {}", posting);
				log.info("skills = {}", skills);
			}
		} else {
			mv.setViewName("redirect:/personlogin");
		}
		return mv;
	}

	@GetMapping("/applyForm/{postingIdx}")
	public ModelAndView applyForm(@PathVariable("postingIdx") Long postingIdx, HttpSession session) {
		ModelAndView mv = new ModelAndView("fragment/applyForm");

		// 세션에서 유저 정보 불러옴
		UserDto user = (UserDto) session.getAttribute("login");
		Long userIdx = user.getUserIdx();

		// 가져온 유저 정보로 PERSON 지정
		PersonDto person = mainService.findPersonByUserIdx(userIdx);

		// 지정한 PERSON으로 이력서 리스트 가져옴
		List<ResumeDto> resumes = mainService.findResumeByUserIdx(userIdx);

		// 지정한 VIEW에서 넘겨받은 postingIdx로 POSTING 정보 가져옴
		PostingDto posting = mainService.findPostingByPostingIdx(postingIdx);
		Long postingUserIdx = posting.getUserIdx();

		// POSTING에서 가져온 userIdx로 COMPANY 지정
		CompanyDto company = mainService.findCompanyByUserIdx(postingUserIdx);

		mv.addObject("company", company);
		mv.addObject("posting", posting);
		mv.addObject("person", person);
		mv.addObject("resumes", resumes);
		return mv;
	}

	@GetMapping("/resumeSelect")
	public ModelAndView selectResume(@RequestParam("resumeIdx") Long resumeIdx) {
		ModelAndView mv = new ModelAndView("fragment/selectedResume");
		ResumeDto resume = mainService.findResumeByResumeIdx(resumeIdx);
		mv.addObject("resume", resume);
		return mv;
	}

	@PostMapping("/applyPosting")
	public ResponseEntity<?> postApply(@RequestBody ApplyDto applyDto) {
		try {
			mainService.insertApply(applyDto);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("스크랩 추가에 실패했습니다.");
		}
	}

	@GetMapping("/applyPage")
	public ModelAndView applyPage(HttpSession session) {
		ModelAndView mv = new ModelAndView("section/apply");
		UserDto user = (UserDto) session.getAttribute("login");
		Long userIdx = user.getUserIdx();
		Long userType = user.getUserType();

		if (userType == 2) {
			PersonDto person = mainService.findPersonByUserIdx(userIdx);
			Long personIdx = person.getPersonIdx();
			List<ApplyStatusDto> applyList = mainService.findApplyList(personIdx);
			mv.addObject("apply", applyList);
		} else {
			if (userType == 1) {
				CompanyDto companyDto = mainService.findCompanyByUserIdx(userIdx);
				Long companyIdx = companyDto.getCompanyIdx();
				List<ApplyStatusDto> applyList = mainService.findApplyListByCompanyIdx(companyIdx);
				mv.addObject("apply", applyList);
			}
		}

		mv.addObject("userType", userType);
		return mv;
	}

	@GetMapping("/companyApply")
	public ModelAndView companyApply(HttpSession session) {
		ModelAndView mv = new ModelAndView("fragment/companyApply");
		UserDto user = (UserDto) session.getAttribute("login");
		Long userIdx = user.getUserIdx();
		Long userType = user.getUserType();
		CompanyDto companyDto = mainService.findCompanyByUserIdx(userIdx);
		Long companyIdx = companyDto.getCompanyIdx();
		List<ApplyStatusDto> applyList = mainService.findApplyListByCompanyIdx(companyIdx);
		mv.addObject("apply", applyList);
		mv.addObject("userType", userType);
		return mv;
	}

	@GetMapping("/personApply")
	public ModelAndView personApply(HttpSession session) {
		ModelAndView mv = new ModelAndView("fragment/personApply");
		UserDto user = (UserDto) session.getAttribute("login");
		Long userIdx = user.getUserIdx();
		Long userType = user.getUserType();
		PersonDto person = mainService.findPersonByUserIdx(userIdx);
		Long personIdx = person.getPersonIdx();
		List<ApplyStatusDto> applyList = mainService.findApplyList(personIdx);
		mv.addObject("apply", applyList);
		mv.addObject("userType", userType);
		return mv;
	}

	@DeleteMapping("/applycancel/{applyIdx}")
	public ResponseEntity<?> cancelApply(@PathVariable("applyIdx") Long applyIdx) {
		try {
			log.info("applyIdxs = {}", applyIdx);
			mainService.deleteAllByApplyIdxs(applyIdx);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("지원 취소에 실패했습니다.");
		}
	}

	@GetMapping("/applyCheck/{personIdx}/{postingIdx}")
	public ResponseEntity<Map<String, String>> checkApply(@PathVariable("personIdx") Long personIdx,
			@PathVariable("postingIdx") Long postingIdx) {
		try {
			log.info("Checking application for personIdx = {} and postingIdx = {}", personIdx, postingIdx);

			boolean exists = mainService.existsByPersonIdxAndPostingIdx(personIdx, postingIdx);
			Map<String, String> response = new HashMap<>();

			if (exists) {
				response.put("message", "이미 해당 공고에 지원하셨습니다.");
				return ResponseEntity.ok().body(response);
			} else {
				response.put("message", "지원 가능합니다.");
				return ResponseEntity.ok().body(response);
			}
		} catch (Exception e) {
			log.error("Error checking application status", e);
			Map<String, String> response = new HashMap<>();
			response.put("message", "지원 상태를 확인할 수 없습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PatchMapping("/applyProcess/{applyIdx}/{applyStatus}")
	public ResponseEntity<?> processApply(@PathVariable("applyIdx") Long applyIdx,
			@PathVariable("applyStatus") Long applyStatus) {
		try {
			log.info("applyIdxs = {}", applyIdx);
			mainService.processApply(applyIdx, applyStatus);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("지원 처리에 실패했습니다.");
		}
	}

	@GetMapping("/applyResumeView/{resumeIdx}/{personIdx}/{postingIdx}")
	public ModelAndView resumeView(HttpSession session, @PathVariable("resumeIdx") Long resumeIdx,
			@PathVariable("personIdx") Long personIdx, @PathVariable("postingIdx") Long postingIdx) {

		ModelAndView mv = new ModelAndView();

		// person_tb 정보 갖고오기
		UserDto user = (UserDto) session.getAttribute("login");
		PersonDto person = mainService.findPersonByPersonIdx(personIdx);
		ResumeDto resume = mainService.findResumeByResumeIdx(resumeIdx);
		ResumeFileDto resumeFile = mainService.findResumeFileByResumeIdx(resumeIdx);
		PostingDto posting = mainService.findPostingByPostingIdx(postingIdx);

		ApplyDto apply = mainService.findApplyByResumeIdxAndPostingIdx(resume.getResumeIdx(), posting.getPostingIdx());
		// skillIdx를 토대로 skill 갖고오기
		List<SkillDto> skill = mainService.findSkillListByPersonIdx(personIdx);

		Long userType = user.getUserType();
		mv.addObject("apply", apply);
		mv.addObject("userType", userType);
		mv.addObject("person", person);
		mv.addObject("resume", resume);
		mv.addObject("skill", skill);
		mv.addObject("resumeFile", resumeFile);
		mv.setViewName("fragment/applyResumeView");
		return mv;
	}

	@GetMapping("/community")
	public ModelAndView community(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "5") int size, HttpSession session) {
		ModelAndView mv = new ModelAndView("section/community");
		UserDto user = (UserDto) session.getAttribute("login");
		Boolean isLoggedInObj = (Boolean) session.getAttribute("isLoggedIn");
		boolean isLoggedIn = isLoggedInObj != null && isLoggedInObj.booleanValue();
		if (isLoggedIn) {
			if (user != null) {
				Long userType = user.getUserType();
				mv.addObject("userType", userType);
			}
		}
		Pageable pageable = PageRequest.of(page, size);
		Page<CommunityDto> communityPage = mainService.findAllCommunity(pageable);
		log.info("communityPage = {}", communityPage);
		log.info("currentPage = {}", communityPage.getNumber());
		log.info("pageCount = {}", communityPage.getTotalPages());
		mv.addObject("user", user);
		mv.addObject("community", communityPage);
		mv.addObject("currentPage", communityPage.getNumber());
		mv.addObject("pageCount", communityPage.getTotalPages());
		mv.addObject("size", size); // size 추가
		return mv;
	}

	@GetMapping("/communitySort")
	public ModelAndView getCommunityData(@RequestParam(value = "sort", defaultValue = "recent") String sort,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "5") int size, HttpServletRequest request,
			HttpSession session) {
		ModelAndView mv;
		UserDto user = (UserDto) session.getAttribute("login");
		Sort sortOrder;
		switch (sort) {
		case "popular":
			sortOrder = Sort.by(Sort.Direction.DESC, "viewCount");
			break;
		case "likes":
			sortOrder = Sort.by(Sort.Direction.DESC, "likeCount");
			break;
		case "comments":
			sortOrder = Sort.by(Sort.Direction.DESC, "replyCount");
			break;
		case "recent":
		default:
			sortOrder = Sort.by(Sort.Direction.DESC, "createdDate");
			break;
		}

		Pageable pageable = PageRequest.of(page, size, sortOrder);
		Page<CommunityDto> communityPage = mainService.findSortedCommunities(pageable);

		// AJAX 요청인지 확인
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			mv = new ModelAndView("fragment/communityMain");
			Boolean isLoggedInObj = (Boolean) session.getAttribute("isLoggedIn");
			boolean isLoggedIn = isLoggedInObj != null && isLoggedInObj.booleanValue();
			if (isLoggedIn) {
				if (user != null) {
					Long userType = user.getUserType();
					mv.addObject("userType", userType);
				}
			}
		} else {
			mv = new ModelAndView("section/community"); // 전체 페이지를 로드
		}

		mv.addObject("community", communityPage);
		mv.addObject("currentPage", communityPage.getNumber());
		mv.addObject("pageCount", communityPage.getTotalPages());
		mv.addObject("size", size);
		mv.addObject("sort", sort);
		return mv;
	}

	@PostMapping("/likeAdd")
	public ResponseEntity<?> addLike(@RequestBody CommunityLikesDto like) {
		try {
			mainService.insertLike(like);
			log.info("like = {}", like);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Like 추가에 실패했습니다.");
		}
	}

	@DeleteMapping("/likeDelete")
	public ResponseEntity<?> deleteLike(@RequestBody CommunityLikesDto like) {
		try {
			mainService.deleteLike(like);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.info("Like = {}", like);
			log.error("Like 삭제 처리 중 오류 발생", e); // 로그 출력 예
			return ResponseEntity.badRequest().body("Like 삭제에 실패했습니다.");
		}
	}

	@GetMapping("/checkLike/{communityIdx}/{userIdx}")
	public ResponseEntity<?> checkLike(@PathVariable("communityIdx") Long communityIdx,
			@PathVariable("userIdx") Long userIdx) {
		int checkLike = mainService.checkLike(communityIdx, userIdx);
		try {
			if (checkLike != 0) {
				boolean isLiked = true;
				return ResponseEntity.ok(isLiked);
			} else {
				boolean isLiked = false;
				return ResponseEntity.ok(isLiked);
			}

		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Like 상태 확인에 실패했습니다.");
		}
	}

	@PostMapping("/loadLikes")
	@ResponseBody
	public Long loadLikes(@RequestParam("communityIdx") Long communityIdx) {

		// postId를 기반으로 좋아요 수를 업데이트하고, 업데이트된 좋아요 수를 반환하는 로직 구현
		Long loadlikes = mainService.countLike(communityIdx);

		// 업데이트된 좋아요 수를 int로 직접 반환
		return loadlikes;
	}

	@GetMapping("/communityDetail/{communityIdx}")
	public ModelAndView getCommunityData(@PathVariable("communityIdx") Long communityIdx, HttpSession session,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		Boolean isLoggedInObj = (Boolean) session.getAttribute("isLoggedIn");
		boolean isLoggedIn = isLoggedInObj != null && isLoggedInObj.booleanValue();
		UserDto user = (UserDto) session.getAttribute("login");

		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			mv.setViewName("fragment/communityDetail");
			if (isLoggedIn) {
				if (user != null) {
					Long userType = user.getUserType();
					mv.addObject("userType", userType);
					mv.addObject("user", user);
				}
			}
			CommunityDto community = mainService.findCommunityBycommunityIdx(communityIdx);
			List<ReplyDto> reply = mainService.findReplysByCommunityIdx(communityIdx);
			mv.addObject("community", community);
			mv.addObject("reply", reply);
			mv.addObject("isLoggedIn", isLoggedIn);
		} else {
			mv = new ModelAndView("community"); // 전체 페이지를 로드
		}

		return mv;
	}

	@PostMapping("/replyInsert")
	public ResponseEntity<?> insertComment(@RequestBody ReplyDto reply, HttpSession session) {
		try {
			UserDto user = (UserDto) session.getAttribute("login");
			if (user != null) {
				Long userType = user.getUserType();
				if (userType == 1) {
					Long userIdx = user.getUserIdx();
					CompanyDto company = mainService.findCompanyByUserIdx(userIdx);
					reply.setUserIdx(userIdx);
					reply.setReplyName(company.getCompanyName());
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					String currentDate = formatter.format(new Date());
					reply.setCreatedDate(currentDate);
					mainService.insertReply(reply);
				} else {
					Long userIdx = user.getUserIdx();
					PersonDto person = mainService.findPersonByUserIdx(userIdx);
					reply.setUserIdx(userIdx);
					reply.setReplyName(person.getPersonName());
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					String currentDate = formatter.format(new Date());
					reply.setCreatedDate(currentDate);
					log.info("reply = {}", reply);
					mainService.insertReply(reply); // Insert reply for non-company user as well
				}
			}

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("댓글 추가에 실패했습니다.", e);
			return ResponseEntity.badRequest().body("댓글 추가에 실패했습니다.");
		}
	}

	@PostMapping("/viewAdd")
	public ResponseEntity<?> addView(@RequestBody CommunityViewDto view) {
		try {
			mainService.insertView(view);
			log.info("like = {}", view);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("View 추가에 실패했습니다.");
		}
	}

	@PostMapping("/loadView")
	@ResponseBody
	public Long loadView(@RequestParam("communityIdx") Long communityIdx) {

		Long loadView = mainService.countView(communityIdx);

		return loadView;
	}

	@PostMapping("/updateReply")
	@ResponseBody
	public Long updateReply(@RequestParam("communityIdx") Long communityIdx) {

		Long updateReply = mainService.countReply(communityIdx);

		return updateReply;
	}

	@GetMapping("/communityWrite")
	public ModelAndView communityWriteForm(HttpSession session) {
		ModelAndView mv = new ModelAndView("fragment/communityWrite");
		UserDto user = (UserDto) session.getAttribute("login");

		Boolean isLoggedInObj = (Boolean) session.getAttribute("isLoggedIn");
		boolean isLoggedIn = isLoggedInObj != null && isLoggedInObj.booleanValue();

		if (isLoggedIn) {
			if (user != null) {
				Long userType = user.getUserType();
				log.info("user = {}", user);
				if (userType == 1) {
					Long userIdx = user.getUserIdx();
					CompanyDto company = mainService.findCompanyByUserIdx(userIdx);
					mv.addObject("userType", userType);
					mv.addObject("name", company.getCompanyName());
					return mv;
				} else {
					Long userIdx = user.getUserIdx();
					PersonDto person = mainService.findPersonByUserIdx(userIdx);
					mv.addObject("name", person.getPersonName());
					mv.addObject("userType", userType);
					return mv;
				}
			}
		} else {
			mv.setViewName("redirect:/personlogin");
			return mv;
		}
		return mv;
	}

	@PostMapping("/communityWrite")
	public ResponseEntity<?> communityWriteForm(HttpSession session, CommunityDto community) {
		try {
			UserDto user = (UserDto) session.getAttribute("login");
			if (user != null) {
				Long userType = user.getUserType();
				if (userType == 1) {
					Long userIdx = user.getUserIdx();
					CompanyDto company = mainService.findCompanyByUserIdx(userIdx);
					community.setUserIdx(userIdx);
					community.setCommunityName(company.getCompanyName());
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					String currentDate = formatter.format(new Date());
					community.setCreatedDate(currentDate);
					community.setViewCount((long) 0);
					community.setLikeCount((long) 0);
					community.setReplyCount((long) 0);
					log.info("community = {}", community);
					mainService.insertCommunity(community, userIdx);
				} else {
					Long userIdx = user.getUserIdx();
					PersonDto person = mainService.findPersonByUserIdx(userIdx);
					community.setUserIdx(userIdx);
					community.setCommunityName(person.getPersonName());
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					String currentDate = formatter.format(new Date());
					community.setCreatedDate(currentDate);
					community.setViewCount((long) 0);
					community.setLikeCount((long) 0);
					community.setReplyCount((long) 0);
					log.info("community = {}", community);
					mainService.insertCommunity(community, userIdx);
				}
			}
			return ResponseEntity.ok().body("{\"message\": \"게시글이 성공적으로 추가되었습니다.\"}");
		} catch (Exception e) {
			log.error("게시글 추가에 실패했습니다.", e);
			return ResponseEntity.badRequest().body("{\"message\": \"게시글 추가에 실패했습니다.\"}");
		}
	}

	@PatchMapping("/updateCommunity")
	public ResponseEntity<?> communityUpdate(HttpSession session, @RequestBody CommunityDto community) {
		try {
			mainService.updateCommunity(community);

			return ResponseEntity.ok().body("{\"message\": \"게시글이 성공적으로 수정되었습니다.\"}");
		} catch (Exception e) {
			log.error("게시글 추가에 실패했습니다.", e);
			return ResponseEntity.badRequest().body("{\"message\": \"게시글 수정에 실패했습니다.\"}");
		}
	}

	@DeleteMapping("/deleteCommunity")
	public ResponseEntity<?> communityDelete(HttpSession session, @RequestBody CommunityDto communityDto) {
		try {
			mainService.deleteCommunity(communityDto);

			return ResponseEntity.ok().body("{\"message\": \"게시글이 성공적으로 삭제되었습니다.\"}");
		} catch (Exception e) {
			log.error("댓글 삭제에 실패했습니다.", e);
			return ResponseEntity.badRequest().body("{\"message\": \"게시글 삭제에 실패했습니다.\"}");
		}
	}

	@PatchMapping("/updateReply")
	public ResponseEntity<?> replyUpdate(HttpSession session, @RequestBody ReplyDto replyDto) {
		try {
			mainService.updateReply(replyDto);

			return ResponseEntity.ok().body("{\"message\": \"댓글이 성공적으로 수정되었습니다.\"}");
		} catch (Exception e) {
			log.error("댓글 수정에 실패했습니다.", e);
			return ResponseEntity.badRequest().body("{\"message\": \"댓글 수정에 실패했습니다.\"}");
		}
	}

	@DeleteMapping("/deleteReply")
	public ResponseEntity<?> replyDelete(HttpSession session, @RequestBody ReplyDto replyDto) {
		try {
			mainService.deleteReply(replyDto);

			return ResponseEntity.ok().body("{\"message\": \"댓글이 성공적으로 삭제되었습니다.\"}");
		} catch (Exception e) {
			log.error("댓글 삭제에 실패했습니다.", e);
			return ResponseEntity.badRequest().body("{\"message\": \"댓글 삭제에 실패했습니다.\"}");
		}
	}

	@GetMapping("/searchCommunity")
	public ModelAndView searchCommunity(@RequestParam("keyword") String keyword, HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "5") int size) {
		ModelAndView mv;
		Pageable pageable = PageRequest.of(page, size);
		Page<CommunityDto> communityPage = mainService.findCommunityByKeywordAndPage(keyword, pageable);

		// AJAX 요청인지 확인
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			mv = new ModelAndView("fragment/communityMain");
		} else {
			mv = new ModelAndView("section/community"); // 전체 페이지를 로드
		}

		mv.addObject("community", communityPage);
		mv.addObject("currentPage", communityPage.getNumber());
		mv.addObject("pageCount", communityPage.getTotalPages());
		mv.addObject("size", size);
		return mv;
	}

	@RequestMapping("/faq")
	public ModelAndView faq(FaqDto faqDto, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("section/faq");
		UserDto user = (UserDto) session.getAttribute("login");

		Boolean isLoggedInObj = (Boolean) session.getAttribute("isLoggedIn");
		boolean isLoggedIn = isLoggedInObj != null && isLoggedInObj.booleanValue();
		
		if (isLoggedIn) {
			if (user != null) {
				Long userType = user.getUserType();
				List<FaqDto> faqlist = mainMapper.getFaqlist();
				mv.addObject("faqlist", faqlist);
				mv.addObject("userType", userType);
			}
		} else {
			List<FaqDto> faqlist = mainMapper.getFaqlist();
			mv.addObject("faqlist", faqlist);
		}				
		
		return mv;

	}

}
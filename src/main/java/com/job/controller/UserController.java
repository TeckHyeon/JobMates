package com.job.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.job.dto.CScrapListDto;
import com.job.dto.CompanyDto;
import com.job.dto.CompanyFileDto;
import com.job.dto.PersonDto;
import com.job.dto.UserDto;
import com.job.mapper.CompanyMapper;
import com.job.mapper.PersonMapper;
import com.job.mapper.PostingMapper;
import com.job.mapper.UserMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


@Slf4j 
@Controller
public class UserController {
	@Autowired
	private PersonMapper personMapper;

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private PostingMapper postingMapper;
	
	// 로그인 페이지
	@GetMapping("/personlogin")
	public  String personlogin() {
		return "member/personlogin"; 
	}
	@GetMapping("/companylogin")
	public  String  companylogin() {
		return "member/companylogin"; 
	}
	// 회원가입 유형선택 페이지
	@GetMapping("/join")
	public String join() {
		return "member/join";
	}
    // 개인 회원가입 페이지
	@GetMapping("/personjoin")
	public  String  personjoin() {
		return "member/personjoin"; 
}
	// 기업 회원가입 페이지
	@GetMapping("/companyjoin")
	public  String  companyjoin() {
		return "member/companyjoin"; 
	}
/*	// 관심기업 페이지
	@GetMapping("/postingScrap")
	public  String  postingScrap() {
		return "member/postingScrap"; 
	}
	*/
	// 개인 회원가입을 한다
	
	@PostMapping("/personjoin")
	@Transactional
	public ModelAndView write(PersonDto personDto,UserDto userDto) {
		//저장
		userMapper.insertUser(userDto);
		personMapper.insertPerson(personDto);
		
		System.out.println("================================"+userDto);
		System.out.println("================================"+personDto);
		
		//데이터를 가지고 이동한다
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("redirect:/");
		return mv;
	}
	//개인로그인을 한다
	@RequestMapping(value="/personlogin", method = {RequestMethod.POST})
	public ModelAndView login(HttpServletRequest request, UserDto userDto, HttpServletResponse response)
			throws IOException {
		ModelAndView mv = new ModelAndView();

		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");

		userDto = userMapper.login(userId, userPw);

		if (userDto != null) {// 아이디와 암호 일치시 수행
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(60 * 60); // 60분동안 로그인 유지
			session.setAttribute("login", userDto);
			session.setAttribute("isLoggedIn", true);
			mv.setViewName("redirect:/");

		} else {// 로그인 실패시
			PrintWriter out = response.getWriter();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8;");
			out.println("<script> alert('아이디또는 비밀번호를 잘못 입력했습니다.');");
			out.println("history.go(-1); </script>");
			out.close();
			mv.setViewName("redirect:/personlogin");
		}
		return mv;
	}
	

		@GetMapping("/IdDupCheck")
		@ResponseBody
		public String IdDupCheck(UserDto userDto) {
			
			HashMap<String, Object>  map =  userMapper.getUser(userDto);
			System.out.println(map);
			// db id 중복체크 결과(서버data) 를 html 에 돌려준다
			
			if ( map == null  ) {
				return "<h4 style='color:green'>사용가능한 아이디입니다</h4>";
			} else {
				if( userDto.getUserId().equals(map.get("userId"))) {
					return "<h4 style='color:red'>사용할 수 없는 아이디입니다</h4>";			
				} 
			
			}
			return "<h4 style='color:red'>사용할 수 없는 아이디입니다</h4>";}

		//  로그아웃을 한다
		@RequestMapping("/logout")
		public   String   logout(  HttpSession  session    ) {
			session.invalidate();
			return  "redirect:/personlogin";
		}
		//기업로그인을 한다
		@PostMapping("/companylogin")
		public String companylogin(HttpServletRequest request, UserDto userDto,
		                           HttpServletResponse response) throws IOException {

				String userId = request.getParameter("userId");
				String userPw = request.getParameter("userPw");
				System.out.println(userId);
				System.out.println(userPw);
				userDto = userMapper.login(userId, userPw);

				if (userDto != null) {// 아이디와 암호 일치시 수행
					HttpSession session = request.getSession();
					session.setMaxInactiveInterval(60 * 60); // 60분동안 로그인 유지
					session.setAttribute("login", userDto);
					session.setAttribute("isLoggedIn", true);
					  return "redirect:/";

				} else {// 로그인 실패시
					PrintWriter out = response.getWriter();
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/html; charset=UTF-8;");
					out.println("<script> alert('아이디또는 비밀번호를 잘못 입력했습니다.');");
					out.println("history.go(-1); </script>");
					out.close();
					
					return "/companylogin";
				}
			}
			// 기업 회원가입을 한다
			
				@PostMapping("/companyjoin")
				@Transactional
				public ModelAndView write(CompanyDto companyDto,UserDto userDto,CompanyFileDto companyFile) {
					//저장
					userMapper.insertUser(userDto);
					companyMapper.insertCompany(companyDto);
					companyMapper.insertCompanyFile(companyFile);

					
					//데이터를 가지고 이동한다
					ModelAndView mv = new ModelAndView();
					
					mv.setViewName("redirect:/");
					return mv;
				}
				
				@GetMapping("/postingScrap")
				public ModelAndView view(HttpServletRequest request) {
				    ModelAndView mv = new ModelAndView();
				    HttpSession session = request.getSession();
				    UserDto user = (UserDto) session.getAttribute("login");
				    PersonDto person = postingMapper.getPersonByUserIdx(user.getUserIdx());
				    Long userType = user.getUserType();
				    System.out.println(person);
				    System.out.println(person);
				    System.out.println(person);
				    
				    // userId 대신 personIdx를 사용
				    Long personIdx = person.getPersonIdx();
				    System.out.println("----------"+personIdx);
				    System.out.println(personIdx);
				    System.out.println(personIdx);
				    
				    List<CScrapListDto> bookmarks = userMapper.getUserBookmarks(personIdx);
				    System.out.println("2222222"+bookmarks);
				    System.out.println(bookmarks);
				    System.out.println(bookmarks);
				    log.info("personIdx = {}", personIdx);
				    log.info("bookmarks = {}", bookmarks);
				    
				    mv.addObject("personIdx", personIdx);
				    mv.addObject("bookmarks", bookmarks);
				    mv.addObject("userType", userType);
				    mv.setViewName("member/postingScrap");

				    return mv;
				}

			
				
}


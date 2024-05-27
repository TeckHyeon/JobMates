package com.job.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import com.job.dto.ApplyDto;
import com.job.dto.ApplyStatusDto;
import com.job.dto.CommunityDto;
import com.job.dto.CommunityLikesDto;
import com.job.dto.CommunityViewDto;
import com.job.dto.CompanyDto;
import com.job.dto.CompanyFileDto;
import com.job.dto.PersonDto;
import com.job.dto.PostingDto;
import com.job.dto.PostingScrapDto;
import com.job.dto.PostingWithFileDto;
import com.job.dto.ReplyDto;
import com.job.dto.ResumeDto;
import com.job.dto.ResumeFileDto;
import com.job.dto.SkillDto;
import com.job.dto.UserDto;
import com.job.entity.Apply;
import com.job.entity.Community;
import com.job.entity.CommunityLikes;
import com.job.entity.CommunityView;
import com.job.entity.Company;
import com.job.entity.CompanyFile;
import com.job.entity.Person;
import com.job.entity.PersonSkill;
import com.job.entity.Posting;
import com.job.entity.PostingScrap;
import com.job.entity.PostingSkill;
import com.job.entity.Reply;
import com.job.entity.Resume;
import com.job.entity.ResumeFile;
import com.job.entity.Skill;
import com.job.entity.User;
import com.job.repository.ApplyRepository;
import com.job.repository.CommunityLikesRepository;
import com.job.repository.CommunityRepository;
import com.job.repository.CommunityViewRepository;
import com.job.repository.CompanyRepository;
import com.job.repository.PersonRepository;
import com.job.repository.PersonSkillRepository;
import com.job.repository.PostingRepository;
import com.job.repository.PostingScrapRepository;
import com.job.repository.PostingskillRepository;
import com.job.repository.ReplyRepository;
import com.job.repository.ResumeFileRepository;
import com.job.repository.ResumeRepository;
import com.job.repository.SkillRepository;
import com.job.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MainService {

	@Autowired
	private CommunityViewRepository communityViewRepository;
	@Autowired
	private ReplyRepository replyRepository;

	@Autowired
	private CommunityLikesRepository communityLikesRepository;

	@Autowired
	private CommunityRepository communityRepository;

	@Autowired
	private ResumeFileRepository resumeFileRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PersonSkillRepository personSkillRepository;

	@Autowired
	private ResumeRepository resumeRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private PostingRepository postingRepository;

	@Autowired
	private PostingScrapRepository postingScrapRepository;

	@Autowired
	private SkillRepository skillRepository;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private PostingskillRepository postingSkillRepository;

	@Autowired
	private ApplyRepository applyRepository;

	@PersistenceContext
	private EntityManager entityManager;

	public List<PostingDto> findByKeyword(String keyword) {
		List<Posting> postings = postingRepository.findByJobTypeContaining(keyword);
		return postings.stream().map(posting -> PostingDto.createPostingDto(posting)). // Posting 엔티티를 PostingDto로 변환
				distinct().collect(Collectors.toList());
	}

	public List<SkillDto> findAllSkills() {
		List<Skill> skillList = skillRepository.findAll(Sort.by(Sort.Direction.ASC, "skillIdx"));
		System.out.println("스킬 = " + skillList);
		return skillList.stream().map(skill -> SkillDto.createSkillDto(skill)).collect(Collectors.toList());
	}

	public List<PostingWithFileDto> findPostingBySearchResult(String region, String experience,
			List<Long> selectedSkills, List<String> selectedJobs, String keyword) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

		// Posting의 처리
		Root<Posting> postingRoot = cq.from(Posting.class);
		Join<Posting, User> postingUserJoin = postingRoot.join("user", JoinType.INNER);

		// Company와 CompanyFile의 처리
		Root<Company> companyRoot = cq.from(Company.class);
		Join<Company, User> companyUserJoin = companyRoot.join("user", JoinType.INNER);

		Root<CompanyFile> fileRoot = cq.from(CompanyFile.class);
		Join<CompanyFile, Company> fileCompanyJoin = fileRoot.join("company", JoinType.RIGHT);

		List<Predicate> predicates = new ArrayList<>();
		// 지역 조건 추가
		if (region != null && !region.equals("전국")) {
			predicates.add(cb.like(postingRoot.get("postingAddress"), "%" + region + "%"));
		}

		// 경험 조건 추가
		if (experience != null && !experience.equals("무관")) {
			predicates.add(cb.like(postingRoot.get("experience"), "%" + experience + "%"));
		}

		// selectedSkills 조건 처리
		if (selectedSkills != null && !selectedSkills.isEmpty()) {
			predicates.add(postingRoot.get("id").in(entityManager
					.createQuery("SELECT ps.posting.id FROM PostingSkill ps WHERE ps.skill.id IN :selectedSkills")
					.setParameter("selectedSkills", selectedSkills).getResultList()));
		}

		// selectedJobs 조건 처리
		if (selectedJobs != null && !selectedJobs.isEmpty()) {
			predicates.add(postingRoot.get("jobType").in(selectedJobs));
		}

		if (keyword != null && !keyword.trim().isEmpty()) {
			Predicate postingTitlePredicate = cb.like(postingRoot.get("postingTitle"), "%" + keyword + "%");
			Predicate companyNamePredicate = cb.like(companyRoot.get("companyName"), "%" + keyword + "%");
			predicates.add(cb.or(postingTitlePredicate, companyNamePredicate));
		}

		predicates.add(cb.equal(companyUserJoin.get("userIdx"), postingUserJoin.get("userIdx")));
		predicates.add(cb.equal(companyRoot.get("companyIdx"), fileCompanyJoin.get("companyIdx")));

		cq.multiselect(postingRoot, fileRoot, companyRoot).where(cb.and(predicates.toArray(new Predicate[0])));

		List<Object[]> results = entityManager.createQuery(cq).getResultList();
		List<PostingWithFileDto> postingWithFileDtos = new ArrayList<>();
		for (Object[] result : results) {
			PostingWithFileDto postingWithFileDto = buildPostingWithFileDto(result);
			postingWithFileDtos.add(postingWithFileDto);
		}
		return postingWithFileDtos;
	}

	public List<PostingWithFileDto> findAllPosting() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

		Root<Company> companyRoot = cq.from(Company.class);
		Join<Company, User> companyUserJoin = companyRoot.join("user", JoinType.INNER);

		Root<Posting> postingRoot = cq.from(Posting.class);
		Join<Posting, User> postingUserJoin = postingRoot.join("user", JoinType.INNER);

		// CompanyFile의 처리를 위한 Root 추가 (가정에 기반한 예시)
		Root<CompanyFile> fileRoot = cq.from(CompanyFile.class);
		Join<CompanyFile, Company> fileCompanyJoin = fileRoot.join("company", JoinType.RIGHT);

		cq.multiselect(postingRoot, fileRoot, companyRoot).where(
				cb.equal(companyUserJoin.get("userIdx"), postingUserJoin.get("userIdx")),
				cb.equal(companyRoot.get("companyIdx"), fileCompanyJoin.get("companyIdx"))); // Company와 CompanyFile을 연결

		List<Object[]> results = entityManager.createQuery(cq).getResultList();

		List<PostingWithFileDto> postingWithFileDtos = new ArrayList<>();
		for (Object[] result : results) {
			PostingWithFileDto postingWithFileDto = buildPostingWithFileDto(result);
			postingWithFileDtos.add(postingWithFileDto);
		}
		return postingWithFileDtos;
	}

	public List<PostingWithFileDto> findPostingsByUserIdx(Long userIdx) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

		Root<Posting> postingRoot = cq.from(Posting.class);
		Join<Posting, User> postingUserJoin = postingRoot.join("user", JoinType.INNER);
		Join<Posting, Company> postingCompanyJoin = postingRoot.join("user", JoinType.INNER);

		Root<Company> companyRoot = cq.from(Company.class);
		Join<Company, User> companyUserJoin = companyRoot.join("user", JoinType.INNER);

		// CompanyFile의 처리를 위한 Root 추가 (가정에 기반한 예시)
		Root<CompanyFile> fileRoot = cq.from(CompanyFile.class);
		Join<CompanyFile, Company> fileCompanyJoin = fileRoot.join("company", JoinType.RIGHT);

		cq.multiselect(postingRoot, fileRoot, companyRoot).where(
				cb.equal(companyRoot.get("companyIdx"), fileCompanyJoin.get("companyIdx")),
				cb.equal(companyUserJoin.get("userIdx"), userIdx), cb.equal(postingUserJoin.get("userIdx"), userIdx)); 

		List<Object[]> results = entityManager.createQuery(cq).getResultList();

		List<PostingWithFileDto> postingWithFileDtos = new ArrayList<>();
		for (Object[] result : results) {
			PostingWithFileDto postingWithFileDto = buildPostingWithFileDto(result);
			postingWithFileDtos.add(postingWithFileDto);
		}
		return postingWithFileDtos;
	}

	private PostingWithFileDto buildPostingWithFileDto(Object[] result) {
		Posting posting = (Posting) result[0];
		CompanyFile file = null;
		if (result[1] != null) {
			file = (CompanyFile) result[1]; // result[1]이 null이 아닐 때만 캐스팅
		}
		Company com = (Company) result[2];

		PostingDto postingDto = PostingDto.createPostingDto(posting);
		CompanyFileDto companyFileDto = null;
		if (file != null) {
			companyFileDto = CompanyFileDto.createCompanyFileDto(file); // file이 null이 아닐 때만 DTO 변환
		}
		CompanyDto companyDto = CompanyDto.createCompanyDto(com);

		PostingWithFileDto postingWithFileDto = new PostingWithFileDto();
		postingWithFileDto.setPostingDto(postingDto);
		postingWithFileDto.setCompanyFileDto(companyFileDto); // file이 null일 경우 companyFileDto도 null
		postingWithFileDto.setCompanyDto(companyDto);

		return postingWithFileDto;
	}

	public PersonDto findPersonByUserIdx(Long userIdx) {
		Optional<Person> personOpt = personRepository.findByUserUserIdx(userIdx);
		if (!personOpt.isPresent()) {
			return null; // 또는 예외 처리
		}
		Person person = personOpt.get();
		PersonDto personDto = PersonDto.createPersonDto(person);
		return personDto;
	}

	public Long countPostingScrap(Long personIdx, Long postingIdx) {
		long postingScrapCount = postingScrapRepository.countByPersonPersonIdxAndPostingPostingIdx(personIdx,
				postingIdx);
		return postingScrapCount;
	}

	public void insertPostingScrap(PostingScrapDto postingScrapDto) {
		// personIdx와 postingIdx로 Person과 Posting 인스턴스 찾기
		Person person = personRepository.findById(postingScrapDto.getPersonIdx())
				.orElseThrow(() -> new NoSuchElementException("해당 Person을 찾을 수 없습니다."));
		Posting posting = postingRepository.findById(postingScrapDto.getPostingIdx())
				.orElseThrow(() -> new NoSuchElementException("해당 Posting을 찾을 수 없습니다."));

		// PostingScrap 인스턴스 생성 및 설정
		PostingScrap postingScrap = PostingScrap.builder().person(person).posting(posting).build();

		postingScrapRepository.save(postingScrap);
	}

	@Transactional
	public void deleteScrap(PostingScrapDto postingScrapDto) {
		Person person = personRepository.findById(postingScrapDto.getPersonIdx())
				.orElseThrow(() -> new NoSuchElementException("해당 Person을 찾을 수 없습니다."));
		Posting posting = postingRepository.findById(postingScrapDto.getPostingIdx())
				.orElseThrow(() -> new NoSuchElementException("해당 Posting을 찾을 수 없습니다."));

		// PostingScrap 인스턴스 생성 및 설정
		PostingScrap postingScrap = PostingScrap.builder().person(person).posting(posting).build();

		postingScrapRepository.deleteByPostingPostingIdxAndPersonPersonIdx(postingScrap.getPosting().getPostingIdx(),
				postingScrap.getPerson().getPersonIdx());
	}

	public PostingDto findPostingByPostingIdx(Long postingIdx) {
		Optional<Posting> PostingOpt = postingRepository.findPostingByPostingIdx(postingIdx);
		if (!PostingOpt.isPresent()) {
			return null; // 또는 예외 처리
		}
		Posting posting = PostingOpt.get();
		PostingDto postingDto = PostingDto.createPostingDto(posting);
		return postingDto;
	}

	public CompanyDto findCompanyByUserIdx(Long userIdx) {
		Optional<Company> CompanyOpt = companyRepository.findCompanyByUserUserIdx(userIdx);
		if (!CompanyOpt.isPresent()) {
			return null; // 또는 예외 처리
		}
		CompanyDto companyDto = CompanyDto.createCompanyDto(CompanyOpt.get());
		return companyDto;
	}

	public List<SkillDto> findSkillListByPostingIdx(Long postingIdx) {
		List<PostingSkill> postingSkills = postingSkillRepository.findPostinSkillByPostingPostingIdx(postingIdx);
		List<SkillDto> skills = new ArrayList<>();
		for (PostingSkill postingSkill : postingSkills) {
			Skill skill = skillRepository.findById(postingSkill.getSkill().getSkillIdx()).orElse(null);
			if (skill != null) {

				SkillDto skillDto = SkillDto.createSkillDto(skill);
				skills.add(skillDto);
			}
		}
		return skills;
	}

	public List<ResumeDto> findResumeByUserIdx(Long userIdx) {
		List<Resume> resumes = resumeRepository.findByUserUserIdx(userIdx);
		return resumes.stream().map(resume -> ResumeDto.createResumeDto(resume)).collect(Collectors.toList());
	}

	public ResumeDto findResumeByResumeIdx(Long resumeIdx) {
		Optional<Resume> ResumeOpt = resumeRepository.findResumeByResumeIdx(resumeIdx);
		if (!ResumeOpt.isPresent()) {
			return null; // 또는 예외 처리
		}
		ResumeDto resumeDto = ResumeDto.createResumeDto(ResumeOpt.get());
		return resumeDto;
	}

	public void insertApply(ApplyDto applyDto) {
		// personIdx와 postingIdx로 Person과 Posting 인스턴스 찾기
		Resume resume = resumeRepository.findById(applyDto.getResumeIdx())
				.orElseThrow(() -> new NoSuchElementException("해당 Resume을 찾을 수 없습니다."));
		Posting posting = postingRepository.findById(applyDto.getPostingIdx())
				.orElseThrow(() -> new NoSuchElementException("해당 Posting을 찾을 수 없습니다."));
		Person person = personRepository.findById(applyDto.getPersonIdx())
				.orElseThrow(() -> new NoSuchElementException("해당 Person을 찾을 수 없습니다."));
		Company company = companyRepository.findById(applyDto.getCompanyIdx())
				.orElseThrow(() -> new NoSuchElementException("해당 Company를 찾을 수 없습니다."));

		Apply apply = Apply.builder().resume(resume).person(person).posting(posting).company(company)
				.applyStatus((long) 1).build();

		applyRepository.save(apply);
	}

	public List<ApplyStatusDto> findApplyList(Long personIdx) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

		Root<Apply> applyRoot = cq.from(Apply.class);
		Join<Apply, Posting> postingJoin = applyRoot.join("posting", JoinType.INNER);
		Join<Apply, Resume> resumeJoin = applyRoot.join("resume", JoinType.INNER);
		Join<Apply, Person> personJoin = applyRoot.join("person", JoinType.INNER);
		Join<Apply, Company> companyJoin = applyRoot.join("company", JoinType.INNER);

		cq.multiselect(postingJoin.get("postingIdx"), postingJoin.get("postingTitle"), resumeJoin.get("resumeIdx"),
				resumeJoin.get("resumeTitle"), applyRoot.get("applyIdx"), applyRoot.get("applyStatus"),
				applyRoot.get("createdDate"), personJoin, companyJoin.get("companyName"))
				.where(cb.equal(personJoin.get("personIdx"), personIdx));

		List<Object[]> results = entityManager.createQuery(cq).getResultList();

		List<ApplyStatusDto> applyStatusDtos = results.stream().map(result -> {
			ApplyStatusDto applyStatusDto = new ApplyStatusDto();

			// 각 DTO 인스턴스 생성
			PostingDto postingDto = PostingDto.builder().postingIdx((Long) result[0]).postingTitle((String) result[1])
					.build();
			ResumeDto resumeDto = ResumeDto.builder().resumeIdx((Long) result[2]).resumeTitle((String) result[3])
					.build();
			ApplyDto applyDto = ApplyDto.builder().applyIdx((Long) result[4]).applyStatus((Long) result[5])
					.createdDate((String) result[6]).build();
			PersonDto personDto = PersonDto.createPersonDto((Person) result[7]);
			CompanyDto companyDto = CompanyDto.builder().companyName((String) result[8]).build();

			// ApplyStatusDto에 DTO 인스턴스 설정
			applyStatusDto.setApplyDto(applyDto);
			applyStatusDto.setPostingDto(postingDto);
			applyStatusDto.setResumeDto(resumeDto);
			applyStatusDto.setPersonDto(personDto);
			applyStatusDto.setCompanyDto(companyDto);

			return applyStatusDto;
		}).collect(Collectors.toList());

		return applyStatusDtos;
	}

	public void deleteAllByApplyIdxs(Long applyIdx) {

		applyRepository.deleteById(applyIdx);
	}

	public List<ApplyStatusDto> findApplyListByCompanyIdx(Long companyIdx) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

		Root<Apply> applyRoot = cq.from(Apply.class);
		Join<Apply, Posting> postingJoin = applyRoot.join("posting", JoinType.INNER);
		Join<Apply, Resume> resumeJoin = applyRoot.join("resume", JoinType.INNER);
		Join<Apply, Person> personJoin = applyRoot.join("person", JoinType.INNER);
		Join<Apply, Company> companyJoin = applyRoot.join("company", JoinType.INNER);

		cq.multiselect(postingJoin.get("postingIdx"), postingJoin.get("postingTitle"), resumeJoin.get("resumeIdx"),
				resumeJoin.get("resumeTitle"), applyRoot.get("applyIdx"), applyRoot.get("applyStatus"),
				applyRoot.get("createdDate"), personJoin, companyJoin.get("companyName"))
				.where(cb.equal(companyJoin.get("companyIdx"), companyIdx));

		List<Object[]> results = entityManager.createQuery(cq).getResultList();

		List<ApplyStatusDto> applyStatusDtos = results.stream().map(result -> {
			ApplyStatusDto applyStatusDto = new ApplyStatusDto();

			// 각 DTO 인스턴스 생성
			PostingDto postingDto = PostingDto.builder().postingIdx((Long) result[0]).postingTitle((String) result[1])
					.build();
			ResumeDto resumeDto = ResumeDto.builder().resumeIdx((Long) result[2]).resumeTitle((String) result[3])
					.build();
			ApplyDto applyDto = ApplyDto.builder().applyIdx((Long) result[4]).applyStatus((Long) result[5])
					.createdDate((String) result[6]).build();
			PersonDto personDto = PersonDto.createPersonDto((Person) result[7]);
			CompanyDto companyDto = CompanyDto.builder().companyName((String) result[8]).build();

			// ApplyStatusDto에 DTO 인스턴스 설정
			applyStatusDto.setApplyDto(applyDto);
			applyStatusDto.setPostingDto(postingDto);
			applyStatusDto.setResumeDto(resumeDto);
			applyStatusDto.setPersonDto(personDto);
			applyStatusDto.setCompanyDto(companyDto);

			return applyStatusDto;
		}).collect(Collectors.toList());

		return applyStatusDtos;
	}

	public boolean existsByPersonIdxAndPostingIdx(Long personIdx, Long postingIdx) {

		return applyRepository.existsByPersonPersonIdxAndPostingPostingIdx(personIdx, postingIdx);
	}

	public void processApply(Long applyIdx, Long applyStatus) {
		Apply apply = applyRepository.findById(applyIdx)
				.orElseThrow(() -> new IllegalArgumentException("해당 지원 정보가 존재하지 않습니다. id=" + applyIdx));
		apply.changeApplyStatus(applyStatus);
		applyRepository.save(apply);
	}

	public PersonDto findPersonByPersonIdx(Long personIdx) {
		Optional<Person> personOpt = personRepository.findByPersonIdx(personIdx);
		if (!personOpt.isPresent()) {
			return null; // 또는 예외 처리
		}
		Person person = personOpt.get();
		PersonDto personDto = PersonDto.createPersonDto(person);
		return personDto;
	}

	public List<SkillDto> findSkillListByPersonIdx(Long personIdx) {
		List<PersonSkill> personSkills = personSkillRepository.findSkillIdxByPersonPersonIdx(personIdx);
		List<SkillDto> skillDtos = new ArrayList<>(); // SkillDto 리스트를 생성합니다.

		for (PersonSkill personSkill : personSkills) {
			Long skillIdx = personSkill.getSkill().getSkillIdx();
			List<Skill> skills = skillRepository.findSkillNameBySkillIdx(skillIdx);

			// 스트림을 사용하여 SkillDto 리스트에 추가
			skillDtos.addAll(skills.stream().map(SkillDto::createSkillDto).collect(Collectors.toList()));
		}
		return skillDtos; // 모든 PersonSkill에 대해 처리된 SkillDto 리스트를 반환합니다.
	}

	public ResumeFileDto findResumeFileByResumeIdx(Long resumeIdx) {
		Optional<ResumeFile> resumeFileOpt = resumeFileRepository.findByResumeResumeIdx(resumeIdx);
		if (!resumeFileOpt.isPresent()) {
			return null; // 또는 예외 처리
		}
		ResumeFileDto resumeFileDto = ResumeFileDto.createResumeFileDto(resumeFileOpt.get());
		return resumeFileDto;
	}

	public ApplyDto findApplyByResumeIdxAndPostingIdx(Long resumeIdx, Long postingIdx) {
		Optional<Apply> applyOpt = applyRepository.findApplyByResumeResumeIdxAndPostingPostingIdx(resumeIdx,
				postingIdx);
		if (!applyOpt.isPresent()) {
			return null; // 또는 예외 처리
		}
		ApplyDto applyDto = ApplyDto.createResumeFileDto(applyOpt.get());
		return applyDto;
	}

	public Page<CommunityDto> findAllCommunity(Pageable pageable) {
		Page<Community> communityPage = communityRepository.findAll(pageable);

		// createCommunitDtoList 메서드를 사용하여 Community 리스트를 CommunityDto 리스트로 변환

		return communityPage.map(CommunityDto::createCommunityDtoList);
	}

	public Page<CommunityDto> findSortedCommunities(Pageable pageable) {
		Page<Community> communityPage = communityRepository.findAll(pageable);
		return communityPage.map(CommunityDto::createCommunityDtoList);
	}

	@Transactional
	public void insertLike(CommunityLikesDto likeDto) {

		Community community = communityRepository.findById(likeDto.getCommunityIdx())
				.orElseThrow(() -> new NoSuchElementException("해당 Community를 찾을 수 없습니다."));
		User user = userRepository.findById(likeDto.getUserIdx())
				.orElseThrow(() -> new NoSuchElementException("해당 User를 찾을 수 없습니다."));

		CommunityLikes like = CommunityLikes.builder().community(community).user(user).build();

		communityLikesRepository.save(like);

		Long likeCount = communityLikesRepository.countByCommunityCommunityIdx(likeDto.getCommunityIdx());
		// Community 엔티티의 좋아요 수 업데이트를 위해 Builder 사용
		Community updatedCommunity = Community.builder().communityIdx(community.getCommunityIdx()) // 기존 Community ID
				.communityTitle(community.getCommunityTitle()).user(community.getUser())
				.communityName(community.getCommunityName()).communityContent(community.getCommunityContent())
				.createdDate(community.getCreatedDate()).viewCount(community.getViewCount()).likeCount(likeCount)
				.replyCount(community.getReplyCount()).build();
		communityRepository.save(updatedCommunity);

	}

	@Transactional
	public void deleteLike(CommunityLikesDto likeDto) {

		Community community = communityRepository.findById(likeDto.getCommunityIdx())
				.orElseThrow(() -> new NoSuchElementException("해당 Community를 찾을 수 없습니다."));
		User user = userRepository.findById(likeDto.getUserIdx())
				.orElseThrow(() -> new NoSuchElementException("해당 User를 찾을 수 없습니다."));

		CommunityLikes like = CommunityLikes.builder().community(community).user(user).build();

		communityLikesRepository.deleteByCommunityCommunityIdxAndUserUserIdx(like.getCommunity().getCommunityIdx(),
				like.getUser().getUserIdx());

		Long likeCount = communityLikesRepository.countByCommunityCommunityIdx(likeDto.getCommunityIdx());
		// Community 엔티티의 좋아요 수 업데이트를 위해 Builder 사용
		Community updatedCommunity = Community.builder().communityIdx(community.getCommunityIdx()) // 기존 Community ID
				.communityTitle(community.getCommunityTitle()).user(community.getUser())
				.communityName(community.getCommunityName()).communityContent(community.getCommunityContent())
				.createdDate(community.getCreatedDate()).viewCount(community.getViewCount()).likeCount(likeCount)
				.replyCount(community.getReplyCount()).build();
		communityRepository.save(updatedCommunity);

	}

	public int checkLike(Long communityIdx, Long userIdx) {
		int checkLike = communityLikesRepository.countByCommunityCommunityIdxAndUserUserIdx(communityIdx, userIdx);
		return checkLike;
	}

	public Long countLike(Long communityIdx) {
		// TODO Auto-generated method stub
		return communityLikesRepository.countByCommunityCommunityIdx(communityIdx);
	}

	public CommunityDto findCommunityBycommunityIdx(Long communityIdx) {
		Optional<Community> communityOpt = communityRepository.findById(communityIdx);
		if (!communityOpt.isPresent()) {
			return null; // 또는 예외 처리
		}
		CommunityDto communityDto = CommunityDto.createCommunityDtoList(communityOpt.get());
		return communityDto;
	}

	public List<ReplyDto> findReplysByCommunityIdx(Long communityIdx) {
		List<Reply> replyList = replyRepository.findReplysByCommunityCommunityIdxOrderByCreatedDateDesc(communityIdx);

		return replyList.stream().map(reply -> ReplyDto.createReplyDto(reply)) // Posting 엔티티를 PostingDto로 변환
				.collect(Collectors.toList());

	}

	@Transactional
	public void insertReply(ReplyDto reply) {
		Community community = communityRepository.findById(reply.getCommunityIdx())
				.orElseThrow(() -> new NoSuchElementException("해당 Community를 찾을 수 없습니다."));
		log.info("community = {}", community);
		User user = userRepository.findById(reply.getUserIdx())
				.orElseThrow(() -> new NoSuchElementException("해당 User를 찾을 수 없습니다."));
		log.info("user = {}", user);

		Reply replyEntity = Reply.builder().community(community).user(user).replyName(reply.getReplyName())
				.replyContent(reply.getReplyContent()).createdDate(reply.getCreatedDate()).build();
		log.info("replyEntity = {}", replyEntity);
		replyRepository.save(replyEntity);
		Long likeCount = communityLikesRepository.countByCommunityCommunityIdx(reply.getCommunityIdx());
		Long replyCount = replyRepository.countByCommunityCommunityIdx(reply.getCommunityIdx());
		// Community 엔티티의 좋아요 수 업데이트를 위해 Builder 사용
		Community updatedCommunity = Community.builder().communityIdx(community.getCommunityIdx()) // 기존 Community ID
				.communityTitle(community.getCommunityTitle()).user(community.getUser())
				.communityName(community.getCommunityName()).communityContent(community.getCommunityContent())
				.createdDate(community.getCreatedDate()).viewCount(community.getViewCount()).likeCount(likeCount)
				.replyCount(replyCount).build();
		communityRepository.save(updatedCommunity);
	}

	@Transactional
	public void insertView(CommunityViewDto viewDto) {
		Long userIdx = viewDto.getUserIdx();
		Long communityIdx = viewDto.getCommunityIdx();

		Optional<CommunityView> existingView = communityViewRepository
				.findByUserUserIdxAndCommunityCommunityIdx(userIdx, communityIdx);
		if (!existingView.isPresent()) {
			// 조회 기록이 없는 경우, 조회수를 증가시키고 조회 기록을 저장
			Community community = communityRepository.findById(viewDto.getCommunityIdx())
					.orElseThrow(() -> new NoSuchElementException("해당 Community를 찾을 수 없습니다."));
			User user = userRepository.findById(userIdx)
					.orElseThrow(() -> new NoSuchElementException("해당 User를 찾을 수 없습니다."));

			CommunityView view = CommunityView.builder().community(community).user(user).build();

			communityViewRepository.save(view);

			Long viewCount = communityViewRepository.countByCommunityCommunityIdx(viewDto.getCommunityIdx());
			// Community 엔티티의 좋아요 수 업데이트를 위해 Builder 사용
			Community updatedCommunity = Community.builder().communityIdx(community.getCommunityIdx())
					.communityTitle(community.getCommunityTitle()).user(community.getUser())
					.communityName(community.getCommunityName()).communityContent(community.getCommunityContent())
					.createdDate(community.getCreatedDate()).viewCount(viewCount).likeCount(viewDto.getViewIdx())
					.replyCount(community.getReplyCount()).build();
			communityRepository.save(updatedCommunity);

		}

	}

	public Long countView(Long communityIdx) {

		return communityViewRepository.countByCommunityCommunityIdx(communityIdx);

	}

	public void insertCommunity(CommunityDto communityDto, Long userIdx) {

		User user = userRepository.findById(userIdx)
				.orElseThrow(() -> new NoSuchElementException("해당 User를 찾을 수 없습니다."));
		Long userType = user.getUserType();
		if (userType == 1) {
			Company company = companyRepository.findCompanyByUserUserIdx(userIdx)
					.orElseThrow(() -> new NoSuchElementException("해당 Company를 찾을 수 없습니다."));

			Community community = Community.builder().communityTitle(communityDto.getCommunityTitle())
					.communityContent(communityDto.getCommunityContent()).createdDate(communityDto.getCreatedDate())
					.viewCount(communityDto.getViewCount()).likeCount(communityDto.getLikeCount())
					.replyCount(communityDto.getReplyCount()).user(user).communityName(company.getCompanyName())
					.build();

			communityRepository.save(community);

		} else {

			Person person = personRepository.findByUserUserIdx(userIdx)
					.orElseThrow(() -> new NoSuchElementException("해당 Person를 찾을 수 없습니다."));
			Community community = Community.builder().communityTitle(communityDto.getCommunityTitle())
					.communityContent(communityDto.getCommunityContent()).createdDate(communityDto.getCreatedDate())
					.viewCount(communityDto.getViewCount()).likeCount(communityDto.getLikeCount())
					.replyCount(communityDto.getReplyCount()).user(user).communityName(person.getPersonName()).build();
			communityRepository.save(community);
		}

	}

	public Long countReply(Long communityIdx) {
		// TODO Auto-generated method stub
		return replyRepository.countByCommunityCommunityIdx(communityIdx);
	}

	public Page<CommunityDto> findCommunityByKeywordAndPage(String keyword, Pageable pageable) {
		Page<Community> communityPage = communityRepository
				.findByCommunityTitleContainingOrCommunityContentContainingAllIgnoreCase(keyword, keyword, pageable);
		return communityPage.map(CommunityDto::createCommunityDtoList);
	}

	public void updateCommunity(CommunityDto communityDto) {
		Community community = communityRepository.findById(communityDto.getCommunityIdx())
				.orElseThrow(() -> new NoSuchElementException("해당 Community를 찾을 수 없습니다."));
		Community updatedCommunity = Community.builder().communityIdx(community.getCommunityIdx()) // 기존 Community ID
				.communityTitle(communityDto.getCommunityTitle()).user(community.getUser())
				.communityName(community.getCommunityName()).communityContent(communityDto.getCommunityContent())
				.createdDate(community.getCreatedDate()).viewCount(community.getViewCount())
				.likeCount(community.getLikeCount()).replyCount(community.getReplyCount()).build();
		communityRepository.save(updatedCommunity);
	}

	public void updateReply(ReplyDto replyDto) {
		Reply reply = replyRepository.findById(replyDto.getReplyIdx())
				.orElseThrow(() -> new NoSuchElementException("해당 Reply를 찾을 수 없습니다."));
		Reply updatedReply = Reply.builder().replyIdx(replyDto.getReplyIdx()).community(reply.getCommunity())
				.user(reply.getUser()).replyName(reply.getReplyName()).replyContent(replyDto.getReplyContent())
				.createdDate(reply.getCreatedDate()).build();
		replyRepository.save(updatedReply);

	}

	public void deleteReply(ReplyDto replyDto) {
		Long replyIdx = replyDto.getReplyIdx();

		replyRepository.deleteById(replyIdx);

	}

	public void deleteCommunity(CommunityDto communityDto) {
		Long communityIdx = communityDto.getCommunityIdx();
		communityRepository.deleteById(communityIdx);

	}

}

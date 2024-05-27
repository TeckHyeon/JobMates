package com.job;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.ModelAndView;

import com.job.controller.MainController;
import com.job.controller.UserController;
import com.job.dto.PersonDto;
import com.job.dto.PostingWithFileDto;
import com.job.dto.SkillDto;
import com.job.dto.UserDto;
import com.job.entity.Company;
import com.job.entity.CompanyFile;
import com.job.entity.Posting;
import com.job.entity.Skill;
import com.job.repository.SkillRepository;
import com.job.service.MainService;
import com.job.service.UserService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SpringBootTest
class JobMatesApplicationTests {
	@InjectMocks
    private MainController mainController;

    @Autowired
    private MainService mainService;
    @Autowired
    private UserController userController;

    @Mock
    private SkillRepository skillRepository;
    @Mock
    private HttpSession session;

    @Mock
    private EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<Object[]> criteriaQuery;

    @Mock
    private Root<Posting> postingRoot;

    @Mock
    private Root<Company> companyRoot;

    @Mock
    private Root<CompanyFile> fileRoot;

    private TypedQuery<Object[]> typedQuery;
    
    @SuppressWarnings("unchecked")
    @BeforeEach
    public void setUp() {
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Object[].class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Posting.class)).thenReturn(postingRoot);
        when(criteriaQuery.from(Company.class)).thenReturn(companyRoot);
        when(criteriaQuery.from(CompanyFile.class)).thenReturn(fileRoot);

        // Create a mock TypedQuery
        typedQuery = mock(TypedQuery.class);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
    }

  

    @Test
    public void testFindPostingsByUserIdx() throws Exception {
        Long userIdx = 1L;

        // 리플렉션을 사용하여 Posting 객체 생성
        Constructor<Posting> constructor = Posting.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Posting posting = constructor.newInstance();

        CompanyFile file = new CompanyFile();
        Company company = new Company();

        List<Object[]> mockResults = new ArrayList<>();
        mockResults.add(new Object[] {posting, file, company});
        
        // Mocking the EntityManager and related objects...
        // ...

        List<PostingWithFileDto> results = mainService.findPostingsByUserIdx(userIdx);

        assertNotNull(results);
        assertEquals(1, results.size()); // 추가 검증: 결과 리스트의 크기 확인
        // 추가 검증: 결과의 필드 값 확인
        PostingWithFileDto dto = results.get(0);
        assertNotNull(dto.getPostingDto());
        assertNotNull(dto.getCompanyFileDto());
        assertNotNull(dto.getCompanyDto());
    }

    @Test
    public void testFindAllSkills() {
        // Mock 스킬 목록 생성
        List<Skill> mockSkillList = new ArrayList<>();
        mockSkillList.add(Skill.builder().skillIdx(1L).skillName("Java").build());
        mockSkillList.add(Skill.builder().skillIdx(2L).skillName("Python").build());
        mockSkillList.add(Skill.builder().skillIdx(3L).skillName("JavaScript").build());
        mockSkillList.add(Skill.builder().skillIdx(4L).skillName("React").build());
        mockSkillList.add(Skill.builder().skillIdx(5L).skillName("SQL").build());
        mockSkillList.add(Skill.builder().skillIdx(6L).skillName("Digital Marketing").build());

        // skillRepository.findAll()이 호출될 때 Mock 스킬 목록을 반환하도록 설정
        when(skillRepository.findAll()).thenReturn(mockSkillList);

        // 메인 서비스의 findAllSkills() 메서드 호출
        List<SkillDto> results = mainService.findAllSkills();
        
        System.out.println("results = "+results);
  
        // 결과가 null이 아닌지 확인
        assertNotNull(results);

        // 결과 리스트의 크기가 6인지 확인
        assertEquals(6, results.size());

        // 결과의 필드 값이 올바른지 확인
        for (int i = 0; i < 6; i++) {
            SkillDto skillDto = results.get(i);
            assertEquals((long) (i + 1), skillDto.getSkillIdx());
            assertEquals(mockSkillList.get(i).getSkillName(), skillDto.getSkillName());
        }
    }




    @Test
    public void testFindPostingBySearchResult() {
        String region = "부산";
        String experience = "신입";
        List<Long> selectedSkills = List.of(1L);
        List<String> selectedJobs = List.of("웹프로그래머");
        String keyword = "포스코";
        Posting mockPosting = mock(Posting.class);
        CompanyFile mockCompanyFile = mock(CompanyFile.class);
        Company mockCompany = mock(Company.class);
        
        
        List<Object[]> mockResults = new ArrayList<>();
        mockResults.add(new Object[]{mockPosting, mockCompanyFile, mockCompany});
        when(entityManager.createQuery(criteriaQuery).getResultList()).thenReturn(mockResults);
        List<PostingWithFileDto> results = mainService.findPostingBySearchResult(region, experience, selectedSkills, selectedJobs,keyword);
        assertNotNull(results);
        assertEquals(1, results.size()); 
        PostingWithFileDto dto = results.get(0);
        assertNotNull(dto.getPostingDto());
        assertNotNull(dto.getCompanyFileDto());
        assertNotNull(dto.getCompanyDto());
    }

    @Test
    public void testFindAllPosting() throws Exception {

        List<PostingWithFileDto> results = mainService.findAllPosting();
        assertNotNull(results);
        assertEquals(2, results.size()); 
        PostingWithFileDto dto = results.get(0);
        assertNotNull(dto.getPostingDto());
        assertNotNull(dto.getCompanyFileDto());
        assertNotNull(dto.getCompanyDto());
    }
}
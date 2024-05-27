package com.job.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;

@Table(name = "USER_TB")  //  table 이름과 클래스 이름이 다를때 사용 (oracle은 user table 못만듬)
@Getter
@Entity
@SequenceGenerator(name="USER_SEQ_GENERATOR", 
sequenceName   = "USER_SEQ", 
initialValue   = 1,     // 초기값
allocationSize = 1 )   // 증가치
public class User {

    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "USER_SEQ_GENERATOR")
    @Column(name = "user_idx", nullable = false, updatable = false)
    private Long userIdx;
    
    @Column(name = "user_id", nullable = false, unique = true, updatable = false)
	private String userId;
    
    @Column(name = "user_pw", nullable = false)
	private String userPw;
    
    @Column(name = "user_type", nullable = false, updatable = false)
	private Long userType;
    
    @Column(name = "user_email", nullable = false)
	private String userEmail;
    
    @Column(name = "created_date", nullable = false, updatable = false)
	private String createdDate;
    
    // Lombok @NoArgsConstructor(access = AccessLevel.PROTECTED) 대신 직접 구현
    protected User() {
        // 보호된 기본 생성자
    }

}

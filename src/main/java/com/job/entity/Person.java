package com.job.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "PERSON_TB")
@Getter
@Entity
@ToString
@NoArgsConstructor
@SequenceGenerator(name="PERSON_SEQ_GENERATOR", 
sequenceName   = "PERSON_SEQ", 
initialValue   = 1,
allocationSize = 1 )
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "PERSON_SEQ_GENERATOR")
    @Column(name = "person_idx", nullable = false, unique = true, updatable = false)
    private Long personIdx;

    @ManyToOne
    @JoinColumn(name="user_idx", nullable = false)
    private User user;
    
    @Column(name = "person_name", nullable = false)
    private String personName;
    
    @Column(name = "person_phone", nullable = false)
    private String personPhone;
    
    @Column(name = "person_address")
    private String personAddress;
    
    @Column(name = "person_birth")
    private String personBirth;
    
    @Column(name = "person_education")
    private String personEducation;

    @Builder
    public Person(Long personIdx, User user, String personName, String personPhone, String personAddress, String personBirth, String personEducation) {
        this.personIdx = personIdx;
        this.user = user;
        this.personName = personName;
        this.personPhone = personPhone;
        this.personAddress = personAddress;
        this.personBirth = personBirth;
        this.personEducation = personEducation;
    }
}



	

	


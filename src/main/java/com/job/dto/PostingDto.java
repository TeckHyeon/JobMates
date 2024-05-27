package com.job.dto;

import java.util.Objects;

import com.job.entity.Posting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostingDto {   
	private Long postingIdx;
	private Long userIdx;
	private String postingTitle;
	private String postingComment;
	private String experience;
	private String empType;
	private String salary;
	private String startTime;
	private String endTime;
	private String postingDeadline;
	private String jobType;
	private String createdDate;
	private String postingAddress;
	
	 @Override
	    public boolean equals(Object object) {
	        if (this == object) return true;
	        if (object == null || getClass() != object.getClass()) return false;
	        PostingDto that = (PostingDto) object;
	        // 중복을 제거할 기준 필드 비교
	        return Objects.equals(jobType, that.jobType); // 예시로 id와 title을 기준으로 비교
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(jobType); // equals 메서드에서 사용한 필드들의 해시코드 반환
	    }
	
	
	public static PostingDto createPostingDto(Posting posting) {
	    return new PostingDto(
	        posting.getPostingIdx(),
	        posting.getUser().getUserIdx(), // userIdx를 올바른 위치로 이동
	        posting.getPostingTitle(),
	        posting.getPostingComment(),
	        posting.getExperience(),
	        posting.getEmpType(),
	        posting.getSalary(),
	        posting.getStartTime(),
	        posting.getEndTime(),
	        posting.getPostingDeadline(),
	        posting.getJobType(),
	        posting.getCreatedDate(),
	        posting.getPostingAddress()
	    );
	}

}

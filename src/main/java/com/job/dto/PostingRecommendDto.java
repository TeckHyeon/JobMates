package com.job.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostingRecommendDto {
    private Long postingIdx;
    private Long userIdx;
    private String postingTitle;
    private String companyName;
    private String filePath;
}

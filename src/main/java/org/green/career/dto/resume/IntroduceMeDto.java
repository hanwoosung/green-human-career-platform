package org.green.career.dto.resume;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class IntroduceMeDto {
    @JsonProperty("rm_no")
    private Long introductionId;    // 자기소개 ID

    @JsonProperty("r_no")
    private Long resumeId;        // 이력서 ID

    @JsonProperty("rm_title")
    private String titleRm;           // 자기소개 제목

    @JsonProperty("rm_content")
    private String content;         // 자기소개 내용

    @JsonProperty("files")
    private List<ResumeFileDto> file;    // 자기소개서 관련 파일
}

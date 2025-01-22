package org.green.career.dto.resume;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TechnicalStackDto {

    @JsonProperty("rs_no")
    private Long stackId;           // 기술 스택 ID

    @JsonProperty("r_no")
    private Long resumeId;          // 이력서 ID

    @JsonProperty("stack_cd")
    private String stackCode;       // 기술 스택 코드 (예: JAVA, SPRING 등)

    @JsonProperty("stack_nm")
    private String stackName;       // 기술 스택 이름 (예: "Java", "Spring Boot" 등)

    @JsonProperty("up_cd")
    private String categoryCode;    // up_cd를 categoryCode로 매핑
    @JsonProperty("stack_category")
    private String stackCategory;   
}

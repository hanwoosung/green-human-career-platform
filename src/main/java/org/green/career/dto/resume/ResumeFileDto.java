package org.green.career.dto.resume;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResumeFileDto {

    @JsonProperty("file_id")
    private Long fileId;        // 파일 고유 ID

    @JsonProperty("file_gbn_cd")
    private String fileGbnCd;   // 파일 구분 코드

    @JsonProperty("file_ref_id")
    private Long fileRefId;   // 파일 참조 ID

    @JsonProperty("file_name")
    private String fileName;    // 파일 이름

    @JsonProperty("file_ext")
    private String fileExt;

    @JsonProperty("inst_id")
    private String instId;     // 입력자

    @JsonProperty("file_url")
    private String fileUrl;     // 파일 URL
}

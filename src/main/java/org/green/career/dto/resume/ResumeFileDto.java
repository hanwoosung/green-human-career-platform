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

    public String getNormalizedFileUrl() {
        if (this.fileUrl != null) {
            // 파일 시스템 경로를 브라우저에서 접근 가능한 경로로 변환
            String normalizedUrl = this.fileUrl.replace("src\\main\\resources\\static\\", "/static/")
                    .replace("src/main/resources/static/", "/static/")
                    .replace("\\", "/");

            // 파일 이름이 이미 포함되어 있지 않다면 추가
            if (this.fileName != null && !normalizedUrl.endsWith(this.fileName)) {
                normalizedUrl = normalizedUrl.endsWith("/") ? normalizedUrl + this.fileName
                        : normalizedUrl + "/" + this.fileName;
            }
            return normalizedUrl;
        }
        return "/static/images/default_profile2.png"; // 기본 이미지 경로 반환
    }


}

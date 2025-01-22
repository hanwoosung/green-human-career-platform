package org.green.career.dto.jobopen.requset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class JobOpeningRequestDto {
    // 채용공고 번호
    private Long jNo;
    // 아이디
    private String id;
    // 공고명
    private String jTitle;
    // 서브타이틀
    private String jStitle;
    // 내용
    private String jContent;
    // 채용구분(C: 마감, O: 모집중, S:모집 일시중지)
    private String jGbnCd;
    // 시작일시
    private LocalDateTime sDt;
    // 마감일시
    private LocalDateTime eDt;
    // 학력
    private String educatCd;
    // 경력(S:신입.G:경력,SG:신입/경력)
    private String careerCd;
    // 우대사항
    private String preferences;
    // 근무지
    private String workPlace;
    // 근무시간
    private String workTime;
    // 근무형태 (C: 계약직, F: 정규직, A: 계약/정규직)
    private String workType;

    private String instId;

    private String updtId;

    private List<MultipartFile> companyImages;

    private List<String> skillList;

}

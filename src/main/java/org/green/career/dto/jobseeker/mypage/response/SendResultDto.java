package org.green.career.dto.jobseeker.mypage.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.green.career.dto.jobseeker.mypage.ScrapStackDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendResultDto {
    // 채용공고 번호
    private Long jNo;
    private Long rNo;
    // 작성자 ID
    private String id;
    // 공고명
    private String jTitle;
    // 서브타이틀
    private String jSTitle;
    private String resumeTitle;
    // 내용
    private String jContent;
    private String userId;
    private String careerNm;
    private String careerCd;

    // 채용구분(C: 마감, O: 모집중, S:모집 일시중지)
    private String jGbnCd;
    private String jrGbnCd;
    // 마감일시
    private LocalDate eDt;
    // 근무지
    private String workPlace;
    // 근무형태 (C: 계약직, F: 정규직, A: 계약/정규직)
    private String workType;
    //스킬
    private List<String> skills;
    // 파일 URL (예: 이미지 파일)
    private String fileUrl;

    private String name;


    // scrapCnt
    private int scrapCnt;
    private String fileId;
    private String fileName;
    private String fileExt;

    // bookmarkCnt
    private int bookmarkCnt;

    private String leftDt;

    private List<ScrapStackDto> stacks = new ArrayList<>();

}

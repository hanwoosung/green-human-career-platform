package org.green.career.dto.jobopen.requset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 작성자: 한우성
 * 작성일: 2024-11-30
 * 메인에서 채용공고 관련 DTO
 * 리퀘스트 리스폰 나눠서 메인에서는 리스폰 JobOpeningResponseDto를 활용중
 * TODO: 분리가능성 있음
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobOpeningResponseDto {
    // 채용공고 번호
    private Long jNo;
    // 작성자 ID
    private String id;
    private String name;
    // 공고명
    private String jTitle;
    // 서브타이틀
    private String jStitle;
    private String jContent;
    // 채용구분(C: 마감, O: 모집중, S:모집 일시중지)
    private String jGbnCd;
    // 마감일시
    private LocalDate eDt;
    private LocalDate sDt;
    // 남은 날짜
    private String leftDate;
    // 근무지
    private String workPlace;
    // 근무형태 (C: 계약직, F: 정규직, A: 계약/정규직)
    private String workType;
    private int vCnt;
    //스킬
    private List<String> skills;
    // 파일 URL (예: 이미지 파일)
    private String fileUrl;
    // scrapCnt
    private int scrapCnt;
    // bookmarkCnt
    private int bookmarkCnt;
    private LocalDate instDt;


    /**
     * 디비에서 스킬이 [JAVA,AWS]이런식으로 날라오게 만들어 놓아서
     * List<String> 형태로 변환
     */
    public void setSkills(String skillsString) {
        if (skillsString != null && !skillsString.isEmpty()) {
            String[] skillsArray = skillsString.replaceAll("[\\[\\]]", "").split(",");
            this.skills = Arrays.stream(skillsArray)
                    .map(String::trim)
                    .filter(skill -> !skill.isEmpty())
                    .collect(Collectors.toList());
        } else {
            this.skills = new ArrayList<>();
        }
    }


    /**
     * 마감일까지 남은 날짜 계산
     * TODO: 나중에 변경해야함 여러곳에서 쓰이기 떄문에
     * @return 남은 날짜 (D-day 형식, D-0 형식, D-n 형식)
     */
    public static String calculateLeftDate(LocalDate sDt, LocalDate eDt) {
        LocalDate today = LocalDate.now();

        if (today.isEqual(eDt)) {
            return "D-day";
        }

        if (today.isAfter(eDt)) {
            return "D-0";
        }

        if (today.isBefore(sDt)) {
            long daysLeft = ChronoUnit.DAYS.between(today, sDt);
            return "D-" + daysLeft;
        }

        long daysLeft = ChronoUnit.DAYS.between(today, eDt);
        return "D-" + daysLeft;
    }
}



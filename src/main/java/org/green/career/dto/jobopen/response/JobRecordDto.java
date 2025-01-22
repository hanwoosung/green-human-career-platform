package org.green.career.dto.jobopen.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobRecordDto {
    // 채용 번호
    private Long jNo;

    // 지원자 번호
    private Long rNo;

    //  이력서 제목
    private String rTitle;

    // 지원 기록 번호
    private Long jrNo;

    // 이름
    private String name;

    // 사용자 ID
    private String id;

    // 주소
    private String addr;

    // 삭제 여부
    private String delYn;

    // 등록 일시
    private LocalDateTime instDt;

    // 경력 코드
    private String careerCd;

    // 지원자 구분 코드
    private String jrGbnCd;

    private String useYn;

    // 대표 여부
    private String rRepYn;

}
package org.green.career.dto.jobopen.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMyResume {

    private String id;
    // 이력서 번호
    private Long rNo;
    private String name;
    // 이력서 제목
    private String rTitle;
    // 대표어부
    private String rRepYn;
    // 삭제여부
    private String delYn;
    // 경력(S:신입.G:경력,SG:신입/경럭)
    private String careerCd;
    // 입력자
    private String instId;
    // 입력일시
    private LocalDate instDt;
    // 수정자
    private String updtId;
    // 수정일시
    private LocalDate updtDt;

}

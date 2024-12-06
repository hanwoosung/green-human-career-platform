package org.green.career.dto.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NormalCompanyDto {

    // 기업 아이디
    private String id;
    // 사업자 등록번호
    private String cno;
    // 기업 구분 코드
    private String cGbnCd;
    // 기업 홈페이지
    private String homepage;
    // 기업 소개
    private String cDetail;
    // 기업 사원수
    private int cCnt;
    // 기업 사업분야
    private String cBusiness;

}
package org.green.career.dto.jobopen.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class JobOpenCompanyDto {
    // 이름
    private String name;
    // 이메일
    private String email;
    // 생년월일
    private LocalDate birth;
    private String formattedBirth;
    // 전화번호
    private String phone;
    // 주소
    private String addr;
    // 상세주소
    private String addr2;
    // 우편번호
    private String zipCd;
    // 사용여부
    private String useYn;
    // 사업자 등록번호
    private String cNo;
    // 기업분류(L: 대, M:중견, S:중소)
    private String cGbnCd;
    // 홈페이지
    private String homepage;
    // 기업소개
    private String cDetail;
    // 사원수
    private Integer cCnt;
    // 사업 분야
    private String cBusiness;
    private String fileUrl;
    private int jobOpenCount;
    private int bCount;
    private int sCount;

}
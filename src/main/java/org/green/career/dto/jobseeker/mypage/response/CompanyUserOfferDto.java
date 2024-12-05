package org.green.career.dto.jobseeker.mypage.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyUserOfferDto {
    private String cId;           // 회사 ID
    private String uId;           // 사용자 ID
    private String oDt;           // 오퍼 날짜
    private String name;          // 사용자 이름
    private String addr;          // 주소
    private String phone;         // 전화번호
    private String cBusiness;     // 회사 업종
    private String fileId;        // 파일 ID
    private String fileName;      // 파일 이름
    private String fileExt;       // 파일 확장자
    private String fileUrl;       // 파일 URL
}

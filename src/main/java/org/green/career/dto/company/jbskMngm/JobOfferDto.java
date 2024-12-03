package org.green.career.dto.company.jbskMngm;

import lombok.Data;

@Data
public class JobOfferDto {

    private String cId;       // 기업 ID
    private String uId;       // 사용자 ID
    private String oDt;       // 이력서 작성 날짜
    private String oGbnCd;    // 구분 코드
    private String name;      // 사용자 이름
    private String birth;     // 생년월일
    private int age;          // 나이
    private String zipCd;     // 우편번호
    private String addr;      // 주소
    private String addr2;     // 상세주소
    private String rNo;    // 이력서 번호
    private String rTitle;    // 이력서 제목
    private String fileId;    // 파일 ID
    private String fileName;  // 파일 이름
    private String fileExt;   // 파일 확장자
    private String fileUrl;   // 파일 URL

}

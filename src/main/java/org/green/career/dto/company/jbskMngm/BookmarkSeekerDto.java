package org.green.career.dto.company.jbskMngm;

import lombok.Data;

@Data
public class BookmarkSeekerDto {

    private String cjNo;       // 구직자 ID
    private String lGbnCd;    // 구분 코드
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

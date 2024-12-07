package org.green.career.dto.admin;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AdminUserDto {

    // 아이디
    private String id;
    // 비밀번호
    private String pw;
    // 이름
    private String name;
    // 이메일
    private String email;
    // 사용자구분(C: 기업, S: 구직자, M: 관리자)
    private String userGbnCd;
    // 사용자구분(C: 기업, S: 구직자, M: 관리자)
    private String userGbnNm;
    // 생년월일
    private LocalDate birth;
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
    // 입력일시
    private LocalDate instDt;
    // 수정자
    private String updtId;
    // 수정일시
    private LocalDate updtDt;

}

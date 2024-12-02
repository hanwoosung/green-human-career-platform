package org.green.career.dto.jobseeker.mypage;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 작성자: 김상준
 * 작성일: 2024-12-02
 * 구직자 마이페이지 북마크 DTO
 */

@Data
public class BookmarkDto {

    private String lNo; // "l_no"
    private String id; // "id"
    private String lGbnCd; // "l_gbn_cd"
    private String cjNo; // "cj_no"

    private String name; // "name"
    private String email; // "email"
    private String userGbnCd; // "user_gbn_cd"
    private String birth; // "birth"
    private int age; // "age"
    private String phone; // "phone"
    private String addr; // "addr"
    private String addr2; // "addr2"
    private String zipCd; // "zip_cd"
    private LocalDateTime instDt; // "inst_dt"
    private String updtId; // "updt_id"
    private LocalDateTime updtDt; // "updt_dt"

    private String fileId;
    private String fileName;
    private String fileExt;
    private String fileUrl;

    private String CBusiness;

}

package org.green.career.dto.jobseeker.mypage;

import lombok.Data;
import org.green.career.dto.common.CodeInfoDto;

import java.util.ArrayList;
import java.util.List;

@Data
public class ScrapDto {

    private String lNo;            // "l_no"
    private String id;             // "id"
    private String lGbnCd;         // "l_gbn_cd"
    private String cjNo;           // "cj_no"
    private String jNo;           // "j_no"
    private String name;           // "name"
    private String jTitle;       // "j_title"
    private String jSTitle;    // "j_stitle"
    private String workPlace;      // "work_place"
    private String careerCd;     // "career_cd"
    private String careerNm;     // "career_nm"
    private int leftDt; // 기술 스택 정보

    private String fileId;
    private String fileName;
    private String fileExt;
    private String fileUrl;

    // scrapCnt
    private int scrapCnt;

    // bookmarkCnt
    private int bookmarkCnt;

    private List<ScrapStackDto> stacks = new ArrayList<>(); // 기술 스택 정보

}

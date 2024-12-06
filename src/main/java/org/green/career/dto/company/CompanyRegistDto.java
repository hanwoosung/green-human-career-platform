package org.green.career.dto.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 작성자: 김재홍
 * 작성일: 2024-12-03
 * 기업정보 등록 관련 DTO
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyRegistDto {

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
    private String cCnt;
    // 기업 사업분야
    private String cBusiness;
    
    // 기업 대표 이미지
    private MultipartFile pImage;
    // 기업 이미지
    private List<MultipartFile> sImage;

    private String fileName;
    private String fileUrl;
    private List<String> fileUrls;
    private List<String> fileNames;

    // 기업 연혁 연/월
    private List<String> historyYear;
    // 기업 연혁 내용
    private List<String> historyTxt;

    // 기업 매출액 연/월
    private List<String> salesYear;
    // 기업 매출액
    private List<String> salesTxt;

}

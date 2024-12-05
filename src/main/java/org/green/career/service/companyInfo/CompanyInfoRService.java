package org.green.career.service.companyInfo;

import org.green.career.dto.common.file.request.FileDto;
import org.green.career.dto.company.CompanyRegistDto;
import org.green.career.dto.company.response.*;

import java.util.List;

public interface CompanyInfoRService {
    /**
    *   기업정보 등록(기업 정보)
     */
    int insertCompanyInfo(CompanyRegistDto company);

    /**
     *   기업정보 등록(기업 이미지)
     */
    int insertCompanyFile(CompanyRegistDto company) throws Exception;

    /**
     *   기업정보 등록(기업 연혁)
     */
    int insertCompanyHistory(CompanyRegistDto company);

    /**
     *   기업정보 등록(기업 매출액)
     */
    int insertCompanySales(CompanyRegistDto company);

    /**
     *   기업정보 조회(기업 테이블)
     */
    CompanyInfoResponseDto getCompanyInfo(String id);
    
    /**
     *   기업정보 조회(매출 테이블)    
     */
    List<CompanySalesResponseDto> getCompanySales(String id);

    /**
     *   기업정보 조회(연혁 테이블)
     */
    List<CompanyHistoryResponseDto> getCompanyHistory(String id);

    /**
     *   기업정보 조회(대표이미지 테이블)
     */
    FileDto getCompanyFileP(String id);

    /**
     *   기업정보 조회(서브이미지 테이블)
     */
    List<FileDto> getCompanyFileS(String id);

    /**
     *   기업정보 조회(프로필사진 테이블)
     */
    FileDto getCompanyFilePr(String id);

    /**
     *   기업정보 조회(개인정보 테이블)
     */
    CompanyUserResponseDto getCompanyUser(String id);

}

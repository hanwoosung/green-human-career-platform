package org.green.career.service.companyInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dao.company.CompanyDetailDao;
import org.green.career.dao.company.CompanyInfoDao;
import org.green.career.dto.common.file.request.FileDto;
import org.green.career.dto.common.file.request.TblFileRequestDto;
import org.green.career.dto.company.CompanyHistoryDto;
import org.green.career.dto.company.CompanyRegistDto;
import org.green.career.dto.company.CompanySalesDto;
import org.green.career.dto.company.NormalCompanyDto;
import org.green.career.dto.company.response.*;
import org.green.career.service.AbstractService;
import org.green.career.utils.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *  2024-12-04 김재홍
 *  기업 정보 등록
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyInfoRServiceImpl extends AbstractService implements CompanyInfoRService {

    private final CompanyInfoDao companyInfoDao;
    private final CommonUtils commonUtils;
    private final CompanyDetailDao companyDetailDao;

    // 기업 테이블 등록
    @Override
    public int insertCompanyInfo(CompanyRegistDto company) {
        NormalCompanyDto cdto = NormalCompanyDto.builder()
                .id(company.getId())
                .cno(company.getCno())
                .cGbnCd(company.getCGbnCd())
                .homepage(company.getHomepage())
                .cDetail(company.getCDetail())
                .cCnt(company.getCCnt())
                .cBusiness(company.getCBusiness())
                .build();
        return companyInfoDao.insertCompanyInfo(cdto);
    }

    // 기업 사진 등록
    @Override
    public int insertCompanyFile(CompanyRegistDto company) throws Exception {

        TblFileRequestDto fdto = commonUtils.saveCompanyImagesP(company.getPImage(), company.getId());
        log.error(fdto.toString()+"Asdasdsadsad");
        int result = companyInfoDao.insertCompanyImage(fdto);
        List<TblFileRequestDto> list = commonUtils.saveCompanyImagesB(company.getSImage(), company.getId());
        for (TblFileRequestDto file : list) {
            result = companyInfoDao.insertCompanyImage(file);
        }
        return result;

    }
    
    // 기업 연혁 등록
    @Override
    public int insertCompanyHistory(CompanyRegistDto company) {

        List<String> history = company.getHistoryYear();
        List<String> content = company.getHistoryTxt();

        int result = 0;

        for(int i = 0; i < company.getHistoryYear().size(); i++){
            CompanyHistoryDto dto = CompanyHistoryDto.builder()
                    .id(company.getId())
                    .cHistory(history.get(i))
                    .cContent(content.get(i))
                    .build();
            result = companyInfoDao.insertCompanyHistory(dto);
        }

        return result;
    }

    // 기업 매출 등록
    @Override
    public int insertCompanySales(CompanyRegistDto company) {

        List<String> date = company.getSalesYear();
        List<String> sales = company.getSalesTxt();

        int result = 0;
        for(int i = 0; i < company.getSalesYear().size(); i++){
            CompanySalesDto dto = CompanySalesDto.builder()
                    .id(company.getId())
                    .cSaleDt(date.get(i))
                    .cPay(sales.get(i))
                    .build();
            result = companyInfoDao.insertCompanySales(dto);
        }
        return 0;
    }

    //기업 정보 조회
    @Override
    public CompanyInfoResponseDto getCompanyInfo(String id) {

        // 기업 테이블 정보
        CompanyInfoResponseDto rdto = companyDetailDao.getCompanyInfo(id);

        return rdto;
    }

    // 기업 매출 조회
    @Override
    public List<CompanySalesResponseDto> getCompanySales(String id) {

        List<CompanySalesResponseDto> slist = companyDetailDao.getCompanySales(id);

        return slist;
    }

    // 기업 연혁 조회
    @Override
    public List<CompanyHistoryResponseDto> getCompanyHistory(String id) {

        List<CompanyHistoryResponseDto> hlist = companyDetailDao.getCompanyHistory(id);

        return hlist;
    }

    // 기업 대표 이미지 조회
    @Override
    public FileDto getCompanyFileP(String id) {

        FileDto file = companyDetailDao.getCompanyFileP(id);
        
        return file;
    }

    //기업 서브 이미지 조회
    @Override
    public List<FileDto> getCompanyFileS(String id) {

        List<FileDto> flist = companyDetailDao.getCompanyFileS(id);

        return flist;
    }

    // 기업 프로필 이미지 조회 
    @Override
    public FileDto getCompanyFilePr(String id) {

        FileDto file = companyDetailDao.getCompanyFilePr(id);

        return file;
    }

    // 기업 개인정보 조회
    @Override
    public CompanyUserResponseDto getCompanyUser(String id) {

        CompanyUserResponseDto rdto = companyDetailDao.getCompanyUser(id);

        return rdto;
    }

}

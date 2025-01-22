package org.green.career.service.company.mypage;

import org.green.career.dto.common.file.request.TblFileRequestDto;
import org.green.career.dto.company.mypage.CompanyModiDto;
import org.green.career.dto.company.mypage.CompanyUserDto;

public interface CompanyMypageService {

    CompanyModiDto getCompanyModi(CompanyUserDto dto);
    int insertMypageProfile(TblFileRequestDto dto);
    int updateMypageInfo(CompanyModiDto dto);

}

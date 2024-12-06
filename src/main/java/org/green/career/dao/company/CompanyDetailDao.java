package org.green.career.dao.company;

import org.apache.ibatis.annotations.Mapper;
import org.green.career.dto.common.file.request.FileDto;
import org.green.career.dto.company.response.CompanyHistoryResponseDto;
import org.green.career.dto.company.response.CompanyInfoResponseDto;
import org.green.career.dto.company.response.CompanySalesResponseDto;
import org.green.career.dto.company.response.CompanyUserResponseDto;

import java.util.List;

@Mapper
public interface CompanyDetailDao {

    CompanyInfoResponseDto getCompanyInfo(String id);

    List<CompanySalesResponseDto> getCompanySales(String id);

    List<CompanyHistoryResponseDto> getCompanyHistory(String id);

    FileDto getCompanyFileP(String id);

    List<FileDto> getCompanyFileS(String id);

    FileDto getCompanyFilePr(String id);

    CompanyUserResponseDto getCompanyUser(String id);

}
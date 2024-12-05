package org.green.career.dao.company;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.green.career.dto.common.file.request.TblFileRequestDto;
import org.green.career.dto.company.CompanyHistoryDto;
import org.green.career.dto.company.CompanySalesDto;
import org.green.career.dto.company.NormalCompanyDto;

@Mapper
public interface CompanyInfoDao {

    int insertCompanyInfo(@Param("company") NormalCompanyDto company);
    int insertCompanyImage(@Param("company") TblFileRequestDto file);
    int insertCompanyHistory(@Param("company") CompanyHistoryDto company);
    int insertCompanySales(@Param("company")CompanySalesDto company);

}

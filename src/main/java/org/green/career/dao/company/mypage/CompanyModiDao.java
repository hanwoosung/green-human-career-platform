package org.green.career.dao.company.mypage;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.green.career.dto.common.file.request.TblFileRequestDto;
import org.green.career.dto.company.mypage.CompanyModiDto;

@Mapper
public interface CompanyModiDao {

    CompanyModiDto getCompanyModiById(String id);
    int insertMypageProfile(@Param("file") TblFileRequestDto dto);
    int deleteMypageProfile(@Param("id") String id);
    int updateMyInfo(@Param("user") CompanyModiDto dto);
    int updateMypageProfile(@Param("file") TblFileRequestDto dto);
    CompanyModiDto getCompanyModiId(String id);
}

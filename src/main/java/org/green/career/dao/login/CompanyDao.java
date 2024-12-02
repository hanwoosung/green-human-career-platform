package org.green.career.dao.login;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.green.career.dto.login.CompanyDto;

@Mapper
public interface CompanyDao {
    int registCompany(@Param("company") CompanyDto company);

    CompanyDto findUserForLogin(String id, String pw, String userGbnCd);

}

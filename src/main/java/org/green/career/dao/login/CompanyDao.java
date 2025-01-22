package org.green.career.dao.login;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.green.career.dto.login.CompanyDto;
import org.green.career.dto.login.UserLoginDto;

import java.util.List;

@Mapper
public interface CompanyDao {
    int registCompany(@Param("user") UserLoginDto user);
    int checkId(String id);
}

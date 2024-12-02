package org.green.career.service.login;

import org.apache.ibatis.annotations.Param;
import org.green.career.dto.login.CompanyDto;

import java.security.NoSuchAlgorithmException;

public interface LoginService {
    int registCompany(CompanyDto company);

    CompanyDto login(String id, String pw, String userGbnCd);
}

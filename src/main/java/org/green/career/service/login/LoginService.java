package org.green.career.service.login;

import org.apache.ibatis.annotations.Param;
import org.green.career.dto.login.CompanyDto;

import java.security.NoSuchAlgorithmException;
import org.green.career.dto.login.UserLoginDto;

/**
 * 작성자: 구경림
 * 작성일: 2024-12-01
 * 로그인 서비스 인터페이스
 */
public interface LoginService {
    UserLoginDto login(String id, String pw, String userGbnCd);
    int registCompany(CompanyDto company);

    CompanyDto login(String id, String pw, String userGbnCd);
}

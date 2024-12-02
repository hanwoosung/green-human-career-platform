package org.green.career.service.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.config.MD5Config;
import org.green.career.dao.login.CompanyDao;
import org.green.career.dto.login.CompanyDto;
import org.green.career.exception.BaseException;
import org.green.career.service.AbstractService;
import org.green.career.type.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl extends AbstractService implements LoginService {

    @Autowired
    private CompanyDao companyDao;

    private final PasswordEncoder passwordEncoder;

    @Override
    public int registCompany(CompanyDto company){
        System.out.println(company.getPw() + "bbb");
        String pw = MD5Config.md5Util(company.getPw());
        String pwd = passwordEncoder.encode(company.getPw());
        company.setPw(pwd);
        System.out.println(pw + "aaa");
        System.out.println(company);
        int result = companyDao.registCompany(company);
        return result;
    }


    @Override
    public CompanyDto login(String id, String pw, String userGbnCd) {

        CompanyDto user = companyDao.findUserForLogin(id, pw, userGbnCd);

        if (id == null || id.trim().isEmpty()) {
            throw new BaseException(ResultType.VALIDATION_ERROR, "아이디는 필수 입력 항목입니다.");
        }
        if (pw == null || pw.trim().isEmpty()) {
            throw new BaseException(ResultType.VALIDATION_ERROR, "비밀번호는 필수 입력 항목입니다.");
        }
        if (user == null) {
            log.warn("존재하지 않는 사용자: {}", id);
            throw new BaseException(ResultType.VALIDATION_ERROR, "회원 정보가 일치하지 않습니다.");
        }
        if (!passwordEncoder.matches(pw, user.getPw())) {
            log.warn("비밀번호가 일치하지 않음. 입력 비밀번호: {}, 데이터베이스 비밀번호: {}", pw, user.getPw());
            throw new BaseException(ResultType.VALIDATION_ERROR, "회원 정보가 일치하지 않습니다.");
        }
        log.info("로그인 성공: {}", id);
        return user;
    }
}

package org.green.career.service.login.regist;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dao.login.CompanyDao;
import org.green.career.dao.login.LoginDao;
import org.green.career.dto.login.UserLoginDto;
import org.green.career.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistCompanyServiceImpl extends AbstractService implements RegistCompanyService {

    @Autowired
    private LoginDao loginDao;

    @Autowired
    private CompanyDao companyDao;

    private final PasswordEncoder passwordEncoder;

    @Override
    public int registCompany(UserLoginDto user) {
        System.out.println(user.getPw() + "bbb");
        String pwd = passwordEncoder.encode(user.getPw());
        user.setPw(pwd);
        System.out.println(pwd + "aaa");
        System.out.println(user);
        int result = companyDao.registCompany(user);
        return result;
    }

    @Override
    public int checkId(String id) {
        int result = companyDao.checkId(id);
        return result;
    }

}

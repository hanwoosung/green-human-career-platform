package org.green.career.service.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dao.login.LoginDao;
import org.green.career.dto.login.UserLoginDto;
import org.green.career.exception.BaseException;
import org.green.career.type.ResultType;
import org.springframework.stereotype.Service;

/**
 * 작성자: 구경림
 * 작성일: 2024-12-01
 * 로그인 서비스의 구현체 클래스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {
    private final LoginDao loginDao;
    @Override
    public UserLoginDto login(String id, String pw){
        UserLoginDto user =  loginDao.findUserForLogin(id,pw);
        if (user == null) {
            throw new BaseException(ResultType.AUTH_ERROR);
        }
        return user;
    }
}

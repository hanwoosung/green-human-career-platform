package org.green.career.service.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dao.login.LoginDao;
import org.green.career.dto.login.UserLoginDto;
import org.green.career.exception.BaseException;
import org.green.career.service.AbstractService;
import org.green.career.type.ResultType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 작성자: 구경림
 * 작성일: 2024-12-01
 * 로그인 서비스의 구현체 클래스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl extends AbstractService implements LoginService {
    private final LoginDao loginDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserLoginDto login(String id, String pw) {

        UserLoginDto user = loginDao.findUserForLogin(id, pw);

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

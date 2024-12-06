package org.green.career.service.regist;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dao.regist.RegistSDao;
import org.green.career.dto.regist.RegistSDto;
import org.green.career.exception.BaseException;
import org.green.career.type.ResultType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
/**
 * 작성자: 구경림
 * 작성일: 2024-12-03
 * 구직자 회원가입 인터페이스 구현체
 * TODO: 기업 회원가입과 통합필요, 서버측 유효성검사
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RegistSServiceImpl implements RegistSService {
    private final RegistSDao registSDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void saveSeeker(RegistSDto registSDto) {
        validateRegistSDto(registSDto);

        String encodedPassword = passwordEncoder.encode(registSDto.getPw());
        registSDto.setPw(encodedPassword);

        registSDao.insertSeeker(registSDto);
        log.info("회원가입 성공: {}", registSDto.getId());
    }
    @Override
    public boolean isDuplicateId(String id) {
        return registSDao.existsById(id);
    }
    private void validateRegistSDto(RegistSDto registSDto) {
        if (registSDto.getId() == null || registSDto.getId().trim().isEmpty()) {
            throw new BaseException(ResultType.VALIDATION_ERROR, "아이디는 필수 입력 항목입니다.");
        }
        if (registSDto.getPw() == null || registSDto.getPw().trim().isEmpty()) {
            throw new BaseException(ResultType.VALIDATION_ERROR, "비밀번호는 필수 입력 항목입니다.");
        }
        if (registSDto.getName() == null || registSDto.getName().trim().isEmpty()) {
            throw new BaseException(ResultType.VALIDATION_ERROR, "이름은 필수 입력 항목입니다.");
        }
        if (registSDto.getEmail() == null || registSDto.getEmail().trim().isEmpty()) {
            throw new BaseException(ResultType.VALIDATION_ERROR, "이메일은 필수 입력 항목입니다.");
        }
        if (registSDto.getBirth() == null || registSDto.getBirth().trim().isEmpty()) {
            throw new BaseException(ResultType.VALIDATION_ERROR, "생년월일은 필수 입력 항목입니다.");
        }
        if (registSDto.getPhone() == null || registSDto.getPhone().trim().isEmpty()) {
            throw new BaseException(ResultType.VALIDATION_ERROR, "전화번호는 필수 입력 항목입니다.");
        }
        if (registSDto.getAddr() == null || registSDto.getAddr().trim().isEmpty()) {
            throw new BaseException(ResultType.VALIDATION_ERROR, "주소는 필수 입력 항목입니다.");
        }
        if (registSDto.getZipCd() == null || registSDto.getZipCd().trim().isEmpty()) {
            throw new BaseException(ResultType.VALIDATION_ERROR, "우편번호는 필수 입력 항목입니다.");
        }

        log.info("유효성 검사 완료: {}", registSDto.getId());
    }
}

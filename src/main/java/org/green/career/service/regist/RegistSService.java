package org.green.career.service.regist;

import org.green.career.dto.regist.RegistSDto;
/**
 * 작성자: 구경림
 * 작성일: 2024-12-03
 * 구직자 회원가입 인터페이스
 * TODO: 기업 회원가입과 통합필요
 */
public interface RegistSService {
    void saveSeeker(RegistSDto registSDto);
    boolean isDuplicateId(String id);
}

package org.green.career.dao.regist;

import org.apache.ibatis.annotations.Mapper;
import org.green.career.dto.regist.RegistSDto;
/**
 * 작성자: 구경림
 * 작성일: 2024-12-03
 * 구직자 회원가입에서 사용중인 매퍼 인터페이스
 */
@Mapper
public interface RegistSDao {
    void insertSeeker(RegistSDto registSDto);
    int existsById(String id);
}
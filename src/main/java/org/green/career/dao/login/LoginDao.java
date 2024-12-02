package org.green.career.dao.login;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.green.career.dto.login.UserLoginDto;

/**
 * 작성자: 구경림
 * 작성일: 2024-12-01
 * 로그인에서 사용중인 매퍼 인터페이스
 */
@Mapper
public interface LoginDao {
    UserLoginDto findUserForLogin(@Param("id") String id, @Param("pw") String pw);
    UserLoginDto findUserForLogin(@Param("id") String id);
}

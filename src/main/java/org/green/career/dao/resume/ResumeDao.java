package org.green.career.dao.resume;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.green.career.dto.resume.ResumeDto;
/**
 * 작성자: 구경림
 * 작성일: 2024-12-04
 * 이력서에서 사용중인 매퍼 인터페이스
 */
@Mapper
public interface ResumeDao {
    ResumeDto getUserInfo(String id);
}

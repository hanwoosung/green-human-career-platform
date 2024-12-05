package org.green.career.dao.main;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.green.career.dto.common.CodeInfoDto;
import org.green.career.dto.jobopen.requset.JobOpeningResponseDto;

import java.util.List;

/**
 * 작성자: 한우성
 * 작성일: 2024-11-30
 * USER_MAIN에서 사용중인 매퍼 인터페이스
 */
@Mapper
public interface MainDao {

    List<CodeInfoDto> findSkillList();

    List<JobOpeningResponseDto> findJobOpeningList(@Param("offset") int offset, @Param("limit") int limit, @Param("id") String id);

    List<JobOpeningResponseDto> searchJobOpenings(@Param("searchText") String searchText,
                                                  @Param("skills") List<String> skills,
                                                  @Param("offset") int offset,
                                                  @Param("limit") int limit,
                                                  @Param("id") String id);

    int countJobOpenings();

    int countSearchJobOpenings(@Param("searchText") String searchText, @Param("skills") List<String> skills);
}

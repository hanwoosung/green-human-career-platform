package org.green.career.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.green.career.dto.likes.response.ResponseLikesDto;

/**
 * 작성자: 김상준
 * 작성일: 2024-12-02
 * 북마크, 스크랩 서비스 Dao
 */

@Mapper
public interface LikesDao {

    int getLikesCnt(@Param("cJNo") String cJNo, @Param("id") String id, @Param("lGbnCd") String lGbnCd);

    int deleteLikes(@Param("cJNo") String cJNo, @Param("id") String id, @Param("lGbnCd") String lGbnCd);

    int insertLikes(@Param("cJNo") String cJNo, @Param("id") String id, @Param("lGbnCd") String lGbnCd);

    ResponseLikesDto getLikes(@Param(("jobId")) int jobId, @Param("userId") String userId, @Param("companyId") String companyId);

}
package org.green.career.dao.jobseeker.mypage;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.green.career.dto.jobseeker.mypage.response.CompanyUserOfferDto;

import java.util.List;

@Mapper
public interface SeekerOfferDao {

    List<CompanyUserOfferDto> selectOffer(@Param("id") String id,
                                          @Param("search") String search,
                                          @Param("offset") int offset,
                                          @Param("limit") int limit);

    int selectOfferCount(@Param("id") String id, @Param("search") String search);
}

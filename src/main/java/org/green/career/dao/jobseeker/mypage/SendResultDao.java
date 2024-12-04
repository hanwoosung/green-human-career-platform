package org.green.career.dao.jobseeker.mypage;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.green.career.dto.common.RatingRequestDto;
import org.green.career.dto.jobseeker.mypage.response.SendResultDto;

import java.util.List;

@Mapper
public interface SendResultDao {
    List<SendResultDto> selectSendResult(@Param("offset") int offset, @Param("limit") int limit, @Param("id") String id);
    int sendResultTotalCount(@Param("id") String id);
    int giveRating(@Param("rating") RatingRequestDto rating);
}

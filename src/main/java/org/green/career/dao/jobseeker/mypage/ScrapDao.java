package org.green.career.dao.jobseeker.mypage;

import org.apache.ibatis.annotations.Mapper;
import org.green.career.dto.jobseeker.mypage.ScrapDto;

import java.util.List;

@Mapper
public interface ScrapDao {

    List<ScrapDto> getScrapList(int offset, int limit, String id);

    int getTotalCnt(String id);

    <T> List<T> getStacks(String jNos);

}
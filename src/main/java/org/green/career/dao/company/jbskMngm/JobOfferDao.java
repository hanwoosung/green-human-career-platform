package org.green.career.dao.company.jbskMngm;

import org.apache.ibatis.annotations.Mapper;
import org.green.career.dto.company.jbskMngm.JobOfferDto;

import java.util.List;

@Mapper
public interface JobOfferDao {

    List<JobOfferDto> getJobOfferList(int offset, int limit, String id, String search);

    int getTotalCnt(String id, String search);

}
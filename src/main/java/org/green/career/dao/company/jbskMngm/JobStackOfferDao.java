package org.green.career.dao.company.jbskMngm;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.green.career.dto.common.CodeInfoDto;
import org.green.career.dto.company.jbskMngm.JobStackOfferDto;
import org.green.career.dto.company.jbskMngm.JobStackOfferStackDto;

import java.util.List;


@Mapper
public interface JobStackOfferDao {

    List<CodeInfoDto> getStackList();

    int getStackListCount(String search,
                          List<String> stacks);

    List<JobStackOfferDto> getJobStackOfferList(String search,
                                           List<String> stacks,
                                           int offset,
                                           int limit);

    List<JobStackOfferStackDto> getJobStackOfferStackList(List<String> jNos);

    int saveOffer(String id, String uId);

}

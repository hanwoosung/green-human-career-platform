package org.green.career.dao.company.jbskMngm;

import org.apache.ibatis.annotations.Mapper;
import org.green.career.dto.common.CodeInfoDto;

import java.util.List;


@Mapper
public interface JobStackOfferDao {

    List<CodeInfoDto> getStackList();

}

package org.green.career.service.company.jbskMngm.jobstackoffer;

import org.green.career.dto.common.CodeInfoDto;

import java.util.List;
import java.util.Map;

public interface JobStackOfferService {

    List<CodeInfoDto> getJobStackOfferList(int page, String search);

}

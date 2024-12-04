package org.green.career.service.company.jbskMngm.jobstackoffer;

import org.green.career.dto.common.CodeInfoDto;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface JobStackOfferService {

    Map<String, Object> getJobStackOfferList(int page, String search, List<String> stacks);

    int saveOffer(String id, String s_id);

}

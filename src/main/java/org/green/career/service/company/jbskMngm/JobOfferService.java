package org.green.career.service.company.jbskMngm;

import org.green.career.dto.company.jbskMngm.JobOfferDto;

import java.util.List;
import java.util.Map;

public interface JobOfferService {

    Map<String, Object> getJobOfferList(int page, String search);

}

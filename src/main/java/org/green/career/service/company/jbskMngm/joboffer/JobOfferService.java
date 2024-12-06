package org.green.career.service.company.jbskMngm.joboffer;

import java.util.Map;

public interface JobOfferService {

    Map<String, Object> getJobOfferList(int page, String search);

}

package org.green.career.service.jobseeker.mypage.offer;

import java.util.Map;

public interface SeekerService {
    Map<String, Object> getJobOfferList(int page, String search,String id);

}

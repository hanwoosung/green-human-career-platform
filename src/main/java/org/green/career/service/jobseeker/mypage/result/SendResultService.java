package org.green.career.service.jobseeker.mypage.result;


import org.green.career.dto.common.RatingRequestDto;

import java.util.Map;


public interface SendResultService {
    Map<String, Object> selectSendResult(int page, String id);

    int sendResultTotalCount(String id);

    int giveRating(RatingRequestDto rating);
}
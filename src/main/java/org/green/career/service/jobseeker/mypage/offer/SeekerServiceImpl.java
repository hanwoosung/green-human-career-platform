package org.green.career.service.jobseeker.mypage.offer;

import lombok.RequiredArgsConstructor;
import org.green.career.dao.jobseeker.mypage.SeekerOfferDao;
import org.green.career.dto.jobseeker.mypage.response.CompanyUserOfferDto;
import org.green.career.utils.PagingBtn;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 2024-12-05
 * 한우성
 */
@Service
@RequiredArgsConstructor
public class SeekerServiceImpl implements SeekerService {

    private final SeekerOfferDao seekerOfferDao;

    @Override
    public Map<String, Object> getJobOfferList(int page, String search, String id) {

        Map<String, Object> result = new HashMap<>();

        int pageSize = 8;
        int offset = (page - 1) * pageSize;

        int totalCount = totalCount(id, search);
        List<CompanyUserOfferDto> jobOfferList = jobOfferListPaging(offset, pageSize, id, search);

        PagingBtn paging = totalCount > 0
                ? new PagingBtn(totalCount, page, pageSize, 10)
                : new PagingBtn(0, 1, pageSize, 10);

        result.put("list", jobOfferList);
        result.put("paging", paging);

        return result;
    }


    private List<CompanyUserOfferDto> jobOfferListPaging(int offset, int limit, String id, String search) {

        List<CompanyUserOfferDto> jobofferList = seekerOfferDao.selectOffer(id, search, offset, limit);

        if (jobofferList.isEmpty()) {
            return Collections.emptyList();
        }
        return jobofferList;
    }

    private int totalCount(String id, String search) {
        return seekerOfferDao.selectOfferCount(id, search);
    }
}

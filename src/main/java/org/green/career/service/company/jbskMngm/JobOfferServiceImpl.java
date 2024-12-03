package org.green.career.service.company.jbskMngm;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dao.company.jbskMngm.JobOfferDao;
import org.green.career.dao.jobseeker.mypage.BookmarkDao;
import org.green.career.dto.company.jbskMngm.JobOfferDto;
import org.green.career.dto.jobseeker.mypage.BookmarkDto;
import org.green.career.service.AbstractService;
import org.green.career.service.jobseeker.mypage.bookmark.BookmarkService;
import org.green.career.utils.PagingBtn;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 작성자: 김상준
 * 작성일: 2024-12-03
 * 기업 입사제안현황
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class JobOfferServiceImpl extends AbstractService implements JobOfferService {

    private final JobOfferDao jobOfferDao;
    private final HttpSession session;

    /*
    * 페이징을 추가한 list 뿌려주기
    * @param page = 현재 페이지
    * */

    @Override
    public Map<String, Object> getJobOfferList(int page, String search) {

        String id = (String) session.getAttribute("userId");

        Map<String, Object> result = new HashMap<>();

        int pageSize = 8;
        int offset = (page - 1) * pageSize;

        int totalCount = totalCount(id, search);
        List<JobOfferDto> jobOfferList = jobOfferListPaging(offset, pageSize, id, search);

        PagingBtn paging = totalCount > 0
                ? new PagingBtn(totalCount, page, pageSize, 10)
                : new PagingBtn(0, 1, pageSize, 10);

        result.put("list", jobOfferList);
        result.put("paging", paging);

        return result;
    }

    /*
     * 페이징을 추가한 list 뿌려주기
     * @param offset = 현재 페이지
     * @param limit = 최대 개수+
     * @param offset = ID
     * */

    private List<JobOfferDto> jobOfferListPaging(int offset, int limit, String id, String search) {

//        return returnData(() -> {
            List<JobOfferDto> jobofferList = jobOfferDao.getJobOfferList(offset, limit, id, search);

            if (jobofferList.isEmpty()) {
                log.info("조회된 입사 제안한 데이터 없음.");
                return Collections.emptyList();
            }

            return jobofferList;
//        });
    }

    /*
     * 페이징을 위한 총 개수 가져오기
     * @param offset = 현재 페이지
     * @param limit = 최대 개수+
     * @param id = 로그인 ID
     * */

    private int totalCount(String id, String search) {
        return jobOfferDao.getTotalCnt(id, search);
    }

}

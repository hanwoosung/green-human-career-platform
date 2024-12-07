package org.green.career.service.company.jbskMngm.bookmarkSeeker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dao.company.jbskMngm.BookmarkSeekerDao;
import org.green.career.dto.company.jbskMngm.JobOfferDto;
import org.green.career.service.AbstractService;
import org.green.career.utils.PagingBtn;
import org.springframework.stereotype.Service;

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
public class BookmarkSeekerServiceImpl extends AbstractService implements BookmarkSeekerService {

    private final BookmarkSeekerDao bookmarkSeekerDao;
    /*
     * 페이징을 추가한 list 뿌려주기
     * @param page = 현재 페이지
     * */

    @Override
    public Map<String, Object> getBookmarkSeekerList(int page, String search, String id) {

        Map<String, Object> result = new HashMap<>();

        int pageSize = 8;
        int offset = (page - 1) * pageSize;

        int totalCount = totalCount(id, search);
        List<JobOfferDto> bookmarkSeeker = jobOfferListPaging(offset, pageSize, id, search);

        PagingBtn paging = totalCount > 0
                ? new PagingBtn(totalCount, page, pageSize, 10)
                : new PagingBtn(0, 1, pageSize, 10);

        result.put("list", bookmarkSeeker);
        result.put("paging", paging);

        return result;
    }

    @Override
    public List<String> selectBookmarkSeekerIds(String id) {
        return  bookmarkSeekerDao.selectBookmarkSeekerIds(id);
    }

    /*
     * 페이징을 추가한 list 뿌려주기
     * @param offset = 현재 페이지
     * @param limit = 최대 개수+
     * @param offset = ID
     * */

    private List<JobOfferDto> jobOfferListPaging(int offset, int limit, String id, String search) {
        return bookmarkSeekerDao.getBookmarkSeekerList(offset, limit, id, search);
    }

    /*
     * 페이징을 위한 총 개수 가져오기
     * @param offset = 현재 페이지
     * @param limit = 최대 개수+
     * @param id = 로그인 ID
     * */

    private int totalCount(String id, String search) {
        return bookmarkSeekerDao.getTotalCnt(id, search);
    }

}

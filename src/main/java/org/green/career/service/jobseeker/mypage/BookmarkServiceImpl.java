package org.green.career.service.jobseeker.mypage;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dao.jobseeker.mypage.BookmarkDao;
import org.green.career.dto.jobseeker.BookmarkDto;
import org.green.career.service.AbstractService;
import org.green.career.utils.PagingBtn;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 작성자: 김상준
 * 작성일: 2024-12-02
 * 구직자 마이페이지 북마크 서비스 구현체
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class BookmarkServiceImpl extends AbstractService implements BookmarkService {

    private final BookmarkDao bookmarkDao;
    private final HttpSession session;

    /*
    * 페이징을 추가한 list 뿌려주기
    * @param page = 현재 페이지
    * */
    
    @Override
    public Map<String, Object> selectAllBookmarks(int page) {
        String id = (String) session.getAttribute("userId");

        Map<String, Object> result = new HashMap<>();

        int pageSize = 15;
        int offset = (page - 1) * pageSize;

        int totalCount = totalCount(id);
        List<BookmarkDto> bookmarkList = boomarkListPaging(offset, pageSize, id);

        PagingBtn paging = totalCount > 0
                ? new PagingBtn(totalCount, page, pageSize, 10)
                : new PagingBtn(0, 1, pageSize, 10);

        result.put("list", bookmarkList);
        result.put("paging", paging);

        return result;
    }

    /*
     * 페이징을 추가한 list 뿌려주기
     * @param offset = 현재 페이지
     * @param limit = 최대 개수+
     * @param offset = ID
     * */

    private List<BookmarkDto> boomarkListPaging(int offset, int limit, String id) {

//        return returnData(() -> {
            List<BookmarkDto> bookmarkList = bookmarkDao.getBookmarkList(offset, limit, id);

            if (bookmarkList.isEmpty()) {
                log.info("조회된 북마크한 기업 없음.");
                return Collections.emptyList();
            }

            return bookmarkList;
//        });
    }

    /*
     * 페이징을 위한 총 개수 가져오기
     * @param offset = 현재 페이지
     * @param limit = 최대 개수+
     * @param id = 로그인 ID
     * */

    private int totalCount(String id) {
        return bookmarkDao.getTotalCnt(id);
    }

}

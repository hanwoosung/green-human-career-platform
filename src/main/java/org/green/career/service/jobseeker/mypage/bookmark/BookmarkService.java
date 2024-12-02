package org.green.career.service.jobseeker.mypage.bookmark;

import java.util.Map;

/**
 * 작성자: 김상준
 * 작성일: 2024-12-02
 * 구직자 마이페이지 북마크 서비스 인터페이스
 */

public interface BookmarkService {

    Map<String, Object> selectAllBookmarks(int page);

}

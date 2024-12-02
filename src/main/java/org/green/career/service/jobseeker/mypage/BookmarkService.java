package org.green.career.service.jobseeker.mypage;

import org.green.career.dto.jobopen.JobOpeningDto;
import org.green.career.dto.jobseeker.BookmarkDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 작성자: 김상준
 * 작성일: 2024-12-02
 * 구직자 마이페이지 북마크 서비스 인터페이스
 */

public interface BookmarkService {

    Map<String, Object> selectAllBookmarks(int page);

}

package org.green.career.dao.jobseeker.mypage;

import org.apache.ibatis.annotations.Mapper;
import org.green.career.dto.jobseeker.mypage.BookmarkDto;

import java.util.List;

/**
 * 작성자: 김상준
 * 작성일: 2024-12-02
 * 구직자 마이페이지 북마크 DAO
 */

@Mapper
public interface BookmarkDao {

    List<BookmarkDto> getBookmarkList(int offset, int limit, String id);

    int getTotalCnt(String id);

}

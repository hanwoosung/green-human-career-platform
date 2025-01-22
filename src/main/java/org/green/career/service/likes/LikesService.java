package org.green.career.service.likes;

import org.green.career.dto.likes.LikesDto;
import org.green.career.dto.likes.response.ResponseLikesDto;

/**
 * 작성자: 김상준
 * 작성일: 2024-12-02
 * 북마크, 스크랩 서비스 인터페이스
 */

public interface LikesService {
    int setLikes(String cJno, String lGbnCd, boolean flag);

    ResponseLikesDto getLikes(int jobId, String companyId);

}

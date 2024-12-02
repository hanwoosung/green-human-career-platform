package org.green.career.service.likes;

/**
 * 작성자: 김상준
 * 작성일: 2024-12-02
 * 북마크, 스크랩 서비스 구현체
 */

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dao.LikesDao;
import org.green.career.exception.BaseException;
import org.green.career.service.AbstractService;
import org.green.career.type.ResultType;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikesServiceImpl extends AbstractService implements LikesService {

    final private LikesDao likesDao;
    final private HttpSession session;

    /*
     * Likes 등록
     * @param cJNo = 내가 할 채용공고, 회사ID
     * @param flag = 활성화 여부 (활성화, true), (비활성화, false)
     * @param lGbnCd = S: 스크랩, B: 북마크
     * */

    @Override
    public int setLikes(String cJNo, String lGbnCd, boolean flag) {
        return returnData(() -> {
            int result;

            String id = (String) session.getAttribute("userId");
            if (id == null) {
                throw new BaseException(ResultType.SESSION_ERROR, "로그인 후 이용가능합니다.");
            }
            int scrapCnt = getScrapCnt(cJNo, id, lGbnCd);
            System.out.println(scrapCnt);
            // 삭제이거나, 스크랩이 0이상일때
            try {
                result = likesDao.deleteLikes(cJNo, id, lGbnCd);
            } catch (Exception e) {
                throw new BaseException(ResultType.ERROR, "Likes 삭제 실패~!", e);
            }

            if (!flag) {
                return result;
            }

            try {
                result = likesDao.insertLikes(cJNo, id, lGbnCd);
            } catch (Exception e) {
                throw new BaseException(ResultType.ERROR, "Likes 저장 실패~!", e);
            }

            // 활성화 일때
            return result;
        });
    }

    /*
     * 현재 등록된 Likes에 구분자에 맞는 수 가져오기
     * @param cJNo = 내가 할 채용공고, 회사ID
     * @param flag = 활성화 여부 (활성화, true), (비활성화, false)
     * @param lGbnCd = S: 스크랩, B: 북마크
     * */

    private int getScrapCnt(String cJNo, String id, String lGbnCd) {
        int result;
        try {
            result = likesDao.getLikesCnt(cJNo, id, lGbnCd);
        } catch (Exception e) {
            throw new BaseException(ResultType.ERROR, "Likes 수량 가져오기 실패~!", e);
        }
        return result;
    }

}

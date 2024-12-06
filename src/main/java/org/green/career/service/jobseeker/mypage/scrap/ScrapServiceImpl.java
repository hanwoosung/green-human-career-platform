package org.green.career.service.jobseeker.mypage.scrap;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dao.jobseeker.mypage.ScrapDao;
import org.green.career.dto.jobseeker.mypage.ScrapDto;
import org.green.career.dto.jobseeker.mypage.ScrapStackDto;
import org.green.career.service.AbstractService;
import org.green.career.utils.PagingBtn;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 작성자: 김상준
 * 작성일: 2024-12-02
 * 구직자 마이페이지 스크랩 서비스 구현체
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class ScrapServiceImpl extends AbstractService implements ScrapService {

    private final ScrapDao scrapDao;
    private final HttpSession session;

    /*
     * 페이징을 추가한 list 뿌려주기
     * @param page = 현재 페이지
     * */

    @Override
    public Map<String, Object> selectAllScraps(int page) {

        String id = (String) session.getAttribute("userId");

        Map<String, Object> result = new HashMap<>();

        int pageSize = 6;
        int offset = (page - 1) * pageSize;

        int totalCount = totalCount(id);
        List<ScrapDto> scrapList = listPaging(offset, pageSize, id);

        PagingBtn paging = totalCount > 0
                ? new PagingBtn(totalCount, page, pageSize, 10)
                : new PagingBtn(0, 1, pageSize, 10);

        result.put("list", scrapList);
        result.put("paging", paging);

        return result;
    }




    /*
     * 페이징을 추가한 list 뿌려주기
     * @param offset = 현재 페이지
     * @param limit = 최대 개수+
     * @param offset = ID
     * */

    private List<ScrapDto> listPaging(int offset, int limit, String id) {

        List<ScrapDto> scrapList = scrapDao.getScrapList(offset, limit, id);

        setStack(scrapList);
        return scrapList;
    }

    /*
     * 페이징을 위한 총 개수 가져오기
     * @param offset = 현재 페이지
     * @param limit = 최대 개수+
     * @param id = 로그인 ID
     * */

    private int totalCount(String id) {
        return scrapDao.getTotalCnt(id);
    }


    /*
     * 스크랩한 공고에 스택 조회해서 넣어 주는 함수
     *
     * */

    private void setStack(List<ScrapDto> list) {

        String cjNos = getCjNo(list);
        System.out.println(cjNos);
        List<ScrapStackDto> stacks = scrapDao.getStacks(cjNos);

        for (ScrapDto scrap : list) {
            for (ScrapStackDto stack : stacks) {
                if (scrap.getCjNo().equals(stack.getJNo())) {
                    scrap.getStacks().add(stack);
                }
            }
        }
    }

    /*
     * 스크랩한 공고에 cjno 추출하는 함수
     *
     * */

    private String getCjNo(List<ScrapDto> list) {
        List<String> cjNo = new ArrayList<>();
        cjNo.add("0");
        for (ScrapDto dto : list) {
            cjNo.add(dto.getCjNo());
        }

        return String.join(",", cjNo);
    }
}
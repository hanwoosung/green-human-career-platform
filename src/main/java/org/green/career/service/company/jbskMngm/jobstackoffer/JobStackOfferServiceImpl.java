package org.green.career.service.company.jbskMngm.jobstackoffer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dao.company.jbskMngm.JobStackOfferDao;
import org.green.career.dto.common.CodeInfoDto;
import org.green.career.dto.company.jbskMngm.JobOfferDto;
import org.green.career.dto.company.jbskMngm.JobStackOfferDto;
import org.green.career.dto.company.jbskMngm.JobStackOfferStackDto;
import org.green.career.dto.jobseeker.mypage.ScrapDto;
import org.green.career.dto.jobseeker.mypage.ScrapStackDto;
import org.green.career.exception.BaseException;
import org.green.career.service.AbstractService;
import org.green.career.type.ResultType;
import org.green.career.utils.PagingBtn;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 작성자: 김상준
 * 작성일: 2024-12-03
 * 기업 스택별 입사 제안
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class JobStackOfferServiceImpl extends AbstractService implements JobStackOfferService {

    final private JobStackOfferDao jobStackOfferDao;




    @Override
    public int saveOffer(String id, String uId) {

        return returnData(() -> {
            int result;

            try{
                result = jobStackOfferDao.saveOffer(id, uId);
            }catch (Exception e){
                throw new BaseException(ResultType.DUPLiCARTE_ERROR, "이미 해당일자에는 이미 입력된 제안 입니다.", e);
            }

            // 활성화 일때
            return result;
        });

    }

    /**
     * 이력서 리스트 가져오기
     */

    @Override
    public Map<String, Object> getJobStackOfferList(int page, String search, List<String> stacks) {

        Map<String, Object> result = new HashMap<>();

        int pageSize = 6;
        int offset = (page - 1) * pageSize;

        List<CodeInfoDto> stackList = getStackList();

        int totalCount = totalCount(search, stacks);

        List<JobStackOfferDto> jobStackOfferList = listPaging(search, stacks, offset, pageSize);

        PagingBtn paging = totalCount > 0
                ? new PagingBtn(totalCount, page, pageSize, 10)
                : new PagingBtn(0, 1, pageSize, 10);
//
        result.put("stackList", stackList);
        result.put("list", jobStackOfferList);
        result.put("paging", paging);

        return result;
    }

    /**
     * 스택 리스트 가져오기
     */

    private List<CodeInfoDto> getStackList() {
        List<CodeInfoDto> stackList = jobStackOfferDao.getStackList();

        return stackList;
    }

    /**
     * 스택이 연관있는 공고 총 개수 가져오기 ~
     */
    
    private int totalCount(String search, List<String> stacks) {
        return jobStackOfferDao.getStackListCount(search, stacks);
    }


    /*
     * 페이징을 추가한 list 뿌려주기
     * @param offset = 현재 페이지
     * @param limit = 최대 개수+
     * @param offset = ID
     * */

    private List<JobStackOfferDto> listPaging(String search, List<String> stacks, int offset, int limit) {

        List<JobStackOfferDto> jobStackOfferDtoList = jobStackOfferDao.getJobStackOfferList(search, stacks, offset, limit);

        setStack(jobStackOfferDtoList);

        return jobStackOfferDtoList;
    }

    /*
     * 공고에 스택 조회해서 넣어 주는 함수
     *
     * */

    private void setStack(List<JobStackOfferDto> list) {

        List<String> jNos = getRjNo(list);

        List<JobStackOfferStackDto> stacks = jobStackOfferDao.getJobStackOfferStackList(jNos);

        for (JobStackOfferDto offer : list) {
            for (JobStackOfferStackDto stack : stacks) {
                if (offer.getRNo().equals(stack.getRNo())) {
                    offer.getStacks().add(stack);
                }
            }

            System.out.println(offer);
        }
    }

    /*
     * 공고에 jno 추출하는 함수
     *
     * */

    private List<String> getRjNo(List<JobStackOfferDto> list) {
        List<String> jNo = new ArrayList<>();

        for (JobStackOfferDto dto : list) {
            jNo.add(dto.getRNo());
        }

        return jNo;
    }

}

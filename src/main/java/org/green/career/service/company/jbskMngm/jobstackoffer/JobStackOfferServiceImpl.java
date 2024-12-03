package org.green.career.service.company.jbskMngm.jobstackoffer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dao.company.jbskMngm.JobStackOfferDao;
import org.green.career.dto.common.CodeInfoDto;
import org.green.career.dto.company.jbskMngm.JobOfferDto;
import org.green.career.service.AbstractService;
import org.green.career.utils.PagingBtn;
import org.springframework.stereotype.Service;

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


    /**
     * 이력서 리스트 가져오기
     */

    @Override
    public List<CodeInfoDto> getJobStackOfferList(int page, String search) {

        int pageSize = 8;
        int offset = (page - 1) * pageSize;

        List<CodeInfoDto> result = getStackList();

        System.out.println(result);


//        int totalCount = totalCount(id, search);
//        List<JobOfferDto> jobOfferList = jobOfferListPaging(offset, pageSize, id, search);

//        PagingBtn paging = totalCount > 0
//                ? new PagingBtn(totalCount, page, pageSize, 10)
//                : new PagingBtn(0, 1, pageSize, 10);
//
//        result.put("list", jobOfferList);
//        result.put("paging", paging);

        return result;
    }

    /**
     * 스택 리스트 가져오기
     */

    private List<CodeInfoDto> getStackList() {

        List<CodeInfoDto> stackList = jobStackOfferDao.getStackList();
        
        return stackList;
    }

}

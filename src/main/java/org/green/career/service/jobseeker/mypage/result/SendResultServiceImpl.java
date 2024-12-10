package org.green.career.service.jobseeker.mypage.result;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dao.jobopen.JobOpeningDao;
import org.green.career.dao.jobseeker.mypage.ScrapDao;
import org.green.career.dao.jobseeker.mypage.SendResultDao;
import org.green.career.dto.common.RatingRequestDto;
import org.green.career.dto.jobopen.requset.JobOpeningResponseDto;
import org.green.career.dto.jobseeker.mypage.ScrapStackDto;
import org.green.career.dto.jobseeker.mypage.response.SendResultDto;
import org.green.career.exception.BaseException;
import org.green.career.service.AbstractService;
import org.green.career.type.ResultType;
import org.green.career.utils.PagingBtn;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendResultServiceImpl extends AbstractService implements SendResultService {

    private final SendResultDao sendResultDao;
    private final ScrapDao scrapDao;
    private final JobOpeningDao jobOpeningDao;

    @Override
    public Map<String, Object> selectSendResult(int page, String id) {

        Map<String, Object> result = new HashMap<>();
        int totalCount = sendResultTotalCount(id);
        int pageSize = 6;
        int offset = (page - 1) * pageSize;

        List<SendResultDto> sendResultDtoList = sendResultDao.selectSendResult(offset, 6, id);


        for (SendResultDto job : sendResultDtoList) {
            job.setSkills(String.valueOf(job.getSkills()));
        }

        PagingBtn paging = totalCount > 0
                ? new PagingBtn(totalCount, page, pageSize, 10)
                : new PagingBtn(0, 1, pageSize, 10);


        result.put("list", sendResultDtoList);
        result.put("paging", paging);


        return result;
    }

    @Override
    public int sendResultTotalCount(String id) {
        return sendResultDao.sendResultTotalCount(id);
    }

    @Override
    public int giveRating(RatingRequestDto rating) {
        //TODO: 이거말도 안됨 나중에 수정해야함 서비스단에서 디비 중복이나 이런 캐치들이 안먹힘 찾아볼예정
        return returnData(() -> {
            int result;
            try {
                result = sendResultDao.giveRating(rating);
            } catch (Exception e) {
                throw new BaseException(ResultType.DUPLiCARTE_ERROR, "이미 입력했었음", e);
            }
            return result;
        });
    }


    private void setStack(List<SendResultDto> list) {

        String cjNos = getCjNo(list);
        System.out.println(cjNos);
        List<ScrapStackDto> stacks = scrapDao.getStacks(cjNos);

        for (SendResultDto scrap : list) {
            for (ScrapStackDto stack : stacks) {
                scrap.getStacks().add(stack);
            }
        }
    }

    private String getCjNo(List<SendResultDto> list) {
        List<String> cjNo = new ArrayList<>();
        cjNo.add("0");
        for (SendResultDto dto : list) {
            cjNo.add(String.valueOf(dto.getJNo()));
        }
        return String.join(",", cjNo);
    }

}

package org.green.career.service.jobseeker.mypage.stack;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dao.jobseeker.mypage.StackDao;
import org.green.career.dto.common.CodeInfoDto;
import org.green.career.dto.common.file.CategoryDto;
import org.green.career.dto.jobopen.JobSearchResult;
import org.green.career.dto.jobopen.requset.JobOpeningResponseDto;
import org.green.career.service.AbstractService;
import org.green.career.utils.CodeMapper;
import org.green.career.utils.PagingBtn;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 작성자: 한우성
 * 작성일: 2024-11-30
 * 메인 서비스의 구현체 클래스
 */
@Service
@RequestMapping("/job-seeker/my-page/stack")
@RequiredArgsConstructor
@Slf4j
public class StackServiceImpl extends AbstractService implements StackService {

    private final StackDao stackDao;
    private final HttpSession session;

    /**
     * 기술 리스트 조회
     */
    @Override
    public Map<String, Object> findSkillList() {
        return returnData(() -> {
            List<CodeInfoDto> codeInfoList = stackDao.findSkillList();

            List<CodeInfoDto> distinctSkills = codeInfoList.stream()
                    .distinct()
                    .collect(Collectors.toList());


            List<CategoryDto> categoryList = codeInfoList.stream()
                    .map(CodeInfoDto::getUpCd)
                    .distinct()
                    .map(upCd -> new CategoryDto(upCd, CodeMapper.getDescription("upCd", upCd)))
                    .collect(Collectors.toList());

            Map<String, Object> result = new HashMap<>();
            result.put("skills", distinctSkills);
            result.put("categories", categoryList);

            return result;
        });
    }


    /**
     * 채용 공고 리스트 조회
     */
    @Override
    public List<JobOpeningResponseDto> findJobOpeningList(int offset, int limit) {
        String id = null;
        if (session.getAttribute("userId") != null) {
             id = session.getAttribute("userId").toString();
        }
        List<JobOpeningResponseDto> jobList = stackDao.findJobOpeningList(offset, limit, id);
        if (jobList.isEmpty()) {
            log.info("조회된 채용 공고 없음.");
            return Collections.emptyList();
        }
        processJobList(jobList);
        return jobList;
    }

    /**
     * 검색 조건에 따른 채용 공고 조회
     */
    @Override
    public List<JobOpeningResponseDto> searchJobOpenings(String searchText, List<String> skills, int offset, int limit) {
        try {

            String id = session.getAttribute("userId").toString();

            List<JobOpeningResponseDto> jobList = stackDao.searchJobOpenings(searchText, skills, offset, limit, id);

            if (jobList == null || jobList.isEmpty()) {
                log.info("검색 조건에 맞는 채용 공고 없음.");
                return Collections.emptyList();
            }

            processJobList(jobList);
            return jobList;

        } catch (Exception e) {
            log.error("채용 공고 검색 중 오류 : {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * 페이징 정보와 함께 채용 공고 조회
     */
    @Override
    public JobSearchResult getJobOpeningsWithPaging(String searchText, List<String> skills, int page) {
        int pageSize = 6;
        int offset = (page - 1) * pageSize;

        List<JobOpeningResponseDto> jobList;
        int totalCount;

        boolean isSearch = (searchText != null && !searchText.isEmpty()) || (skills != null && !skills.isEmpty());

        if (isSearch) {
            jobList = searchJobOpenings(searchText, skills, offset, pageSize);
            totalCount = countSearchJobOpenings(searchText, skills);
        } else {
            jobList = findJobOpeningList(offset, pageSize);
            totalCount = countJobOpenings();
        }

        PagingBtn paging = totalCount > 0
                ? new PagingBtn(totalCount, page, pageSize, 10)
                : new PagingBtn(0, 1, pageSize, 10);

        return new JobSearchResult(jobList, paging);
    }

    /**
     * 공고 리스트 가공
     */
    private void processJobList(List<JobOpeningResponseDto> jobList) {
        if (jobList != null) {
            for (JobOpeningResponseDto job : jobList) {
                processJob(job);
            }
        }
    }

    /**
     * 공고 단일 데이터 가공
     */
    private void processJob(JobOpeningResponseDto job) {
        job.setLeftDate(JobOpeningResponseDto.calculateLeftDate(job.getEDt()));
        job.setJGbnCd(CodeMapper.getDescription("jobStatus", job.getJGbnCd()));
        job.setWorkType(CodeMapper.getDescription("workType", job.getWorkType()));
        job.setSkills(String.valueOf(job.getSkills()));

        if (job.getFileUrl() != null && !job.getFileUrl().isEmpty()) {
            job.setFileUrl(job.getFileUrl());
        } else {
            job.setFileUrl("/static/images/empty_company.png");
        }
    }

    /**
     * 총 채용 공고 수 조회
     */
    @Override
    public int countJobOpenings() {
        return stackDao.countJobOpenings();
    }

    /**
     * 검색 조건에 따른 채용 공고 수 조회
     */
    @Override
    public int countSearchJobOpenings(String searchText, List<String> skills) {
        return stackDao.countSearchJobOpenings(searchText, skills);
    }
}

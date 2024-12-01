package org.green.career.service.main;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dao.main.MainDao;
import org.green.career.dto.common.CodeInfoDto;
import org.green.career.dto.jobopen.JobOpeningDto;
import org.green.career.dto.jobopen.JobSearchResult;
import org.green.career.service.AbstractService;
import org.green.career.utils.CodeMapper;
import org.green.career.utils.PagingBtn;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 작성자: 한우성
 * 작성일: 2024-11-30
 * 메인 서비스의 구현체 클래스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MainServiceImpl extends AbstractService implements MainService {

    private final MainDao mainDao;

    /**
     * 기술 리스트 조회
     */
    @Override
    public Map<String, List<CodeInfoDto>> findSkillList() {
        return returnData(() -> {
            List<CodeInfoDto> codeInfoList = mainDao.findSkillList();
            Map<String, List<CodeInfoDto>> groupedMap = new HashMap<>();
            groupedMap.put("back", new ArrayList<>());
            groupedMap.put("front", new ArrayList<>());

            for (CodeInfoDto codeInfo : codeInfoList) {
                String category = "back_cd".equals(codeInfo.getUpCd()) ? "back" : "front";
                groupedMap.get(category).add(codeInfo);
            }
            return groupedMap;
        });
    }

    /**
     * 채용 공고 리스트 조회
     */
    @Override
    public List<JobOpeningDto.JobOpeningResponseDto> findJobOpeningList(int offset, int limit) {
        return returnData(() -> {
            List<JobOpeningDto.JobOpeningResponseDto> jobList = mainDao.findJobOpeningList(offset, limit);
            if (jobList.isEmpty()) {
                log.info("조회된 채용 공고 없음.");
                return Collections.emptyList();
            }
            processJobList(jobList);
            return jobList;
        });
    }

    /**
     * 검색 조건에 따른 채용 공고 조회
     */
    @Override
    public List<JobOpeningDto.JobOpeningResponseDto> searchJobOpenings(String searchText, List<String> skills, int offset, int limit) {
        try {
            List<JobOpeningDto.JobOpeningResponseDto> jobList = mainDao.searchJobOpenings(searchText, skills, offset, limit);

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
        int pageSize = 16;
        int offset = (page - 1) * pageSize;

        List<JobOpeningDto.JobOpeningResponseDto> jobList;
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
    private void processJobList(List<JobOpeningDto.JobOpeningResponseDto> jobList) {
        if (jobList != null) {
            for (JobOpeningDto.JobOpeningResponseDto job : jobList) {
                processJob(job);
            }
        }
    }

    /**
     * 공고 단일 데이터 가공
     */
    private void processJob(JobOpeningDto.JobOpeningResponseDto job) {
        job.setLeftDate(JobOpeningDto.JobOpeningResponseDto.calculateLeftDate(job.getEDt()));
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
        return mainDao.countJobOpenings();
    }

    /**
     * 검색 조건에 따른 채용 공고 수 조회
     */
    @Override
    public int countSearchJobOpenings(String searchText, List<String> skills) {
        return mainDao.countSearchJobOpenings(searchText, skills);
    }
}
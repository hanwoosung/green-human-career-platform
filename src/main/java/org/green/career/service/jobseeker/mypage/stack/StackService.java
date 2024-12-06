package org.green.career.service.jobseeker.mypage.stack;

import org.green.career.dto.jobopen.JobSearchResult;
import org.green.career.dto.jobopen.requset.JobOpeningResponseDto;

import java.util.List;
import java.util.Map;

/**
 * 작성자: 한우성
 * 작성일: 2024-11-30
 * 메인서비스
 */
public interface StackService {

    Map<String, Object> findSkillList();

    List<JobOpeningResponseDto> findJobOpeningList(int offset, int limit, String id);

    List<JobOpeningResponseDto> searchJobOpenings(String searchText, List<String> skills, int offset, int limit);

    JobSearchResult getJobOpeningsWithPaging(String searchText, List<String> skills, int page, String id);

    int countJobOpenings();

    int countSearchJobOpenings(String searchText, List<String> skills);


}
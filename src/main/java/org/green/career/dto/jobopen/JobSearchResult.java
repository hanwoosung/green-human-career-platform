package org.green.career.dto.jobopen;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.green.career.dto.jobopen.requset.JobOpeningResponseDto;
import org.green.career.utils.PagingBtn;

import java.util.List;

/**
 * 작성자: 한우성
 * 작성일: 2024-11-30
 * 채용 공고 검색 결과를 담는 클래스
 * 공고 리스트와 페이징 정보를 건내줌
 */
@Data
@AllArgsConstructor
public class JobSearchResult {
    private List<JobOpeningResponseDto> jobList;
    private PagingBtn paging;
}
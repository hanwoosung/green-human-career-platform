package org.green.career.service.jobopen;

import org.green.career.dto.common.file.request.TblFileRequestDto;
import org.green.career.dto.common.file.response.FileResponseDto;
import org.green.career.dto.jobopen.JobOpeningDetailDto;
import org.green.career.dto.jobopen.requset.JobOpeningRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 작성자: 한우성
 * 작성일: 2024-11-30
 * 채용공고 전체 관리 서비스
 */
public interface JobOpeningService {

    /**
     * 채용 공고 등록
     */
    int insertJobOpening(JobOpeningRequestDto jobOpeningRequestDto);

    /**
     * 최대 채용 공고 번호 조회
     */
    int selectMax();

    /**
     * 채용 공고에 스킬 목록 등록
     */
    int insertSkills(int jNo, List<String> skillList);

    /**
     * 파일 목록 등록
     */
    int insertFiles(List<TblFileRequestDto> fileList);

    /**
     * 채용 공고 상세 조회
     */
    JobOpeningDetailDto getJobOpening(int jNo);

    /**
     * 채용 공고에 연관된 파일 목록 조회
     */
    List<FileResponseDto> getFile(int jNo);

    /**
     * 파일 삭제
     */
    void deleteFileDB(List<Long> fileIds, int refNo);

    /**
     * 새로 업로드된 파일 추가
     */
    void addFiles(int jNo, List<MultipartFile> companyImages) throws Exception;

    /**
     * 채용 공고 수정
     */
    int updateJobOpening(int jNo, JobOpeningRequestDto jobOpeningRequestDto);

    /**
     * 추가된 스킬 등록
     */
    int addSkills(int jNo, List<String> addedSkills);

    /**
     * 삭제된 스킬 삭제
     */
    int removeSkills(int jNo, List<String> removedSkills);

}

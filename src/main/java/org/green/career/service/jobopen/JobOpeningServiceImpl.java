package org.green.career.service.jobopen;

import lombok.RequiredArgsConstructor;
import org.green.career.dao.jobopen.JobOpeningDao;
import org.green.career.dto.common.file.request.TblFileRequestDto;
import org.green.career.dto.jobopen.requset.JobOpeningRequestDto;
import org.green.career.exception.BaseException;
import org.green.career.service.AbstractService;
import org.green.career.type.ResultType;
import org.green.career.utils.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobOpeningServiceImpl extends AbstractService implements JobOpeningService {

    private final JobOpeningDao jobOpeningDao;
    private final CommonUtils commonUtils;

    @Override
    public int insertJobOpening(JobOpeningRequestDto jobOpeningRequestDto) {
        return returnData(() -> {
            JobOpeningRequestDto jobRequestDto = buildJobOpeningRequestDto(jobOpeningRequestDto);
            int result;

            try {
                result = jobOpeningDao.insertJobOpening(jobRequestDto);
            } catch (Exception e) {
                throw new BaseException(ResultType.ERROR, "공고 데이터 인설트 실패", e);
            }

            if (result == 0) {
                throw new BaseException(ResultType.ERROR, "공고 데이터 결과 없음");
            }

            // 최대 번호 조회
            int maxNum;
            try {
                maxNum = selectMax();
            } catch (Exception e) {
                throw new BaseException(ResultType.ERROR, "최대 번호 조회 실패", e);
            }

            // 스킬 등록
            try {
                insertSkills(maxNum, jobOpeningRequestDto.getSkillList());
            } catch (Exception e) {
                throw new BaseException(ResultType.ERROR, "스킬 데이터 삽입 실패", e);
            }

            // 파일 업로드 처리
            if (jobOpeningRequestDto.getCompanyImages() != null && !jobOpeningRequestDto.getCompanyImages().isEmpty()) {
                try {
                    handleFileUpload(jobOpeningRequestDto, maxNum);
                } catch (BaseException e) {
                    throw e;
                } catch (Exception e) {
                    throw new BaseException(ResultType.ERROR, "파일 업로드 실패", e);
                }
            }

            return maxNum;
        });
    }


    private JobOpeningRequestDto buildJobOpeningRequestDto(JobOpeningRequestDto requestDto) {
        return JobOpeningRequestDto.builder()
                .id("company2") //TODO: 추후 세션 아이디로
                .jTitle(requestDto.getJTitle())
                .jStitle(requestDto.getJStitle())
                .jContent(requestDto.getJContent())
                .jGbnCd(requestDto.getJGbnCd())
                .sDt(requestDto.getSDt())
                .eDt(requestDto.getEDt())
                .educatCd(requestDto.getEducatCd())
                .careerCd(requestDto.getCareerCd())
                .preferences(requestDto.getPreferences())
                .workPlace(requestDto.getWorkPlace())
                .workTime(requestDto.getWorkTime())
                .workType(requestDto.getWorkType())
                .instId(requestDto.getInstId())
                .build();
    }

    private void handleFileUpload(JobOpeningRequestDto requestDto, int maxNum) {
        try {
            List<TblFileRequestDto> fileList = commonUtils.saveCompanyImages(requestDto.getCompanyImages(), maxNum);
            insertFiles(fileList);
        } catch (Exception e) {
            throw new RuntimeException("파일 업로드 중 오류 발생", e);
        }
    }

    @Override
    public int selectMax() {
        return jobOpeningDao.selectMax();
    }

    @Override
    public int insertSkills(int jNo, List<String> skillList) {
        return jobOpeningDao.insertSkills(jNo, skillList);
    }

    @Override
    public int insertFiles(List<TblFileRequestDto> fileList) {
        return jobOpeningDao.insertFiles(fileList);
    }
}
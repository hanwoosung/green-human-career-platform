package org.green.career.service.jobopen;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dao.jobopen.JobOpeningDao;
import org.green.career.dto.common.CodeInfoDto;
import org.green.career.dto.common.file.request.TblFileRequestDto;
import org.green.career.dto.common.file.response.FileResponseDto;
import org.green.career.dto.jobopen.JobOpeningDetailDto;
import org.green.career.dto.jobopen.requset.JobOpeningRequestDto;
import org.green.career.dto.jobopen.response.JobOpenCompanyDto;
import org.green.career.dto.jobopen.response.JobRecordDto;
import org.green.career.dto.jobopen.response.ResponseMyResume;
import org.green.career.exception.BaseException;
import org.green.career.service.AbstractService;
import org.green.career.type.ResultType;
import org.green.career.utils.CommonUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 작성자: 한우성
 * 작성일: 2024-11-30
 * 채용공고 기능 구현체
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JobOpeningServiceImpl extends AbstractService implements JobOpeningService {

    private final JobOpeningDao jobOpeningDao;
    private final CommonUtils commonUtils;

    @Override
    public int insertJobOpening(JobOpeningRequestDto jobOpeningRequestDto, String id) {
        return returnData(() -> {
            JobOpeningRequestDto jobRequestDto = buildJobOpeningRequestDto(jobOpeningRequestDto, id);
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

    private JobOpeningRequestDto buildJobOpeningRequestDto(JobOpeningRequestDto requestDto, String id) {
        return JobOpeningRequestDto.builder()
                .id(id)
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
            throw new RuntimeException("파일 업로드 오류", e);
        }
    }

    @Override
    public JobOpeningDetailDto getJobOpening(int jNo) {
        JobOpeningDetailDto dto = jobOpeningDao.getJobOpening(jNo);

        if (dto == null) {
            throw new BaseException(ResultType.ERROR, "공고 정보 못 찾구나...");
        } else {
            if (dto.getSkills() != null && dto.getSkills().size() == 1) {
                dto.setSkills(String.valueOf(dto.getSkills()));
            }
            if (!getFile(jNo).isEmpty() && getFile(jNo) != null) {
                dto.setFiles(getFile(jNo));
            }
        }
        return dto;
    }


    @Override
    public void deleteFileDB(List<Long> fileIds, int refNo) {
        if (fileIds == null || fileIds.isEmpty()) {
            throw new BaseException(ResultType.ERROR, "삭제할 파일 ID가 없네...");
        }

        fileIds.forEach(fileId -> {
            FileResponseDto file = jobOpeningDao.getFileById(fileId);
            if (file != null) {
                log.info("삭제 요청 파일 : {}", file);
                boolean isDeleted = commonUtils.deleteFile(file.getFileUrl());
                if (!isDeleted) {
                    throw new BaseException(ResultType.ERROR, "파일 삭제 오류: " + file.getFileName());
                }
                jobOpeningDao.deleteFileDB(fileId, String.valueOf(refNo));
            }
        });
    }

    @Override
    public int addSkills(int jNo, List<String> addedSkills) {
        if (addedSkills == null || addedSkills.isEmpty()) {
            throw new BaseException(ResultType.ERROR, "추가할 스킬 없다.");
        }
        try {
            return jobOpeningDao.insertSkills(jNo, addedSkills);
        } catch (Exception e) {
            throw new BaseException(ResultType.ERROR, "스킬 추가 오류 ", e);
        }
    }

    @Override
    public int removeSkills(int jNo, List<String> removedSkills) {
        if (removedSkills == null || removedSkills.isEmpty()) {
            throw new BaseException(ResultType.ERROR, "삭제할 스킬이 없다.");
        }
        try {
            return jobOpeningDao.deleteSkills(jNo, removedSkills);
        } catch (Exception e) {
            throw new BaseException(ResultType.ERROR, "스킬 삭제 오류", e);
        }
    }

    @Override
    public JobOpenCompanyDto getCompany(String companyId) {
        JobOpenCompanyDto company = jobOpeningDao.getCompany(companyId);

        //TODO :임시 로직 일단 여기둠

        // 설립일 변환 로직
        String birthDate = String.valueOf(company.getBirth());
        LocalDate establishmentDate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int yearsSince = Period.between(establishmentDate, LocalDate.now()).getYears();

        // 변환된 값 설정
        String formattedBirth = establishmentDate.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일"));
        String displayBirth = formattedBirth + " (" + yearsSince + "년차)";
        company.setFormattedBirth(displayBirth);

        return company;
    }

    @Override
    public List<JobRecordDto> getResumeList(int jNo) {
        return jobOpeningDao.getResumeList(jNo);
    }

    @Override
    public int jobOpeningPass(int jrNo, String type) {
        return returnData(() -> jobOpeningDao.jobOpeningPass(jrNo, type));
    }

    @Override
    public List<ResponseMyResume> myResumes(String id) {
        return returnData(() -> jobOpeningDao.myResumes(id));
    }

    @Override
    public int resumeApply(int jNo, int rNo, String id) {
        if (existsResume(jNo, rNo, id) != 0) {
            throw new BaseException(ResultType.EXISTS_ERROR);
        }

        return returnData(() -> jobOpeningDao.resumeApply(jNo, rNo, id));
    }

    @Override
    public int existsResume(int jNo, int rNo, String id) {
        return jobOpeningDao.existsResume(jNo, rNo, id);
    }

    @Override
    public int delete(int jNo) {
        return returnData(() -> jobOpeningDao.delete(jNo));
    }

    @Override
    public List<CodeInfoDto> mySkill(int jNo) {
        return jobOpeningDao.mySkill(jNo);
    }

    @Override
    public void addFiles(int jNo, List<MultipartFile> companyImages) throws Exception {
        List<TblFileRequestDto> fileList = commonUtils.saveCompanyImages(companyImages, jNo);
        insertFiles(fileList);
    }

    @Override
    public int updateJobOpening(int jNo, JobOpeningRequestDto jobOpeningRequestDto) {
        return jobOpeningDao.updateJobOpening(jNo, jobOpeningRequestDto);
    }

    @Override
    public List<FileResponseDto> getFile(int jNo) {
        return jobOpeningDao.getFile(jNo);
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
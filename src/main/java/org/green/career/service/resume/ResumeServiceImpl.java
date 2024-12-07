package org.green.career.service.resume;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dao.resume.ResumeDao;
import org.green.career.dto.resume.*;
import org.green.career.exception.BaseException;
import org.green.career.service.AbstractService;
import org.green.career.type.ResultType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeServiceImpl extends AbstractService implements ResumeService {

    private final ResumeDao resumeDao;
    private final ResumeFileService resumeFileService;

    public ResumeDto getUserInfo(String id) {
        return returnData(() -> resumeDao.getUserInfo(id));
    }
    public List<ResumeDto> getAllResumes(String userId) {
        return returnData(() -> resumeDao.getResumesByUserId(userId));
    }


    // 카테고리별로 기술 스택(카테고리, 기술스택)을 맵 형태로 반환
    public Map<String, List<TechnicalStackDto>> getAllTechnicalStacks() {
        return returnData(()->{
            Map<String, List<TechnicalStackDto>> stacksByCategory = new HashMap<>();
            stacksByCategory.put("back_cd", resumeDao.getTechnicalStacksByCategory("back_cd"));
            stacksByCategory.put("design_cd", resumeDao.getTechnicalStacksByCategory("design_cd"));
            stacksByCategory.put("mobile_cd", resumeDao.getTechnicalStacksByCategory("mobile_cd"));
            stacksByCategory.put("front_cd", resumeDao.getTechnicalStacksByCategory("front_cd"));
            return stacksByCategory;
        });
    }

    public List<TreatDto> getAllTreatCodes() {
        return returnData(() -> resumeDao.getAllTreatCodes());
    }

    public void deleteResume(String resumeId) {
            executeSafely(() -> resumeDao.deleteResume(resumeId), "이력서 삭제 실패");
    }



    @Transactional
    public void saveResumeToDatabase(ResumeDto resumeDto, MultipartFile profilePicture,
                                     List<MultipartFile> portfolioFiles,
                                     List<MultipartFile> introduceMeFiles) {
        executeSafely(() -> resumeDao.saveResume(resumeDto), "이력서 저장 실패");
        Long generatedResumeId = resumeDto.getResumeId();

        if (generatedResumeId == null) {
            throw new IllegalStateException("이력서 ID가 생성되지 않았습니다.");
        }

        executeSafely(() -> {
            try {
                // 프로필 사진 저장
                if (profilePicture != null && !profilePicture.isEmpty()) {
                    resumeFileService.saveFile(profilePicture, resumeDto, "resume_profile", generatedResumeId, "50");
                }

                // 포트폴리오 파일 저장
                if (portfolioFiles != null && !portfolioFiles.isEmpty()) {
                    for (MultipartFile file : portfolioFiles) {
                        resumeFileService.saveFile(file, resumeDto, "portfolio", generatedResumeId, "40");
                    }
                }

                // 자기소개서 파일 저장
                if (introduceMeFiles != null && !introduceMeFiles.isEmpty()) {
                    for (MultipartFile file : introduceMeFiles) {
                        resumeFileService.saveFile(file, resumeDto, "introduceMe", generatedResumeId, "35");
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("파일 저장 중 오류 발생", e);
            }

            saveRelatedData(resumeDto, generatedResumeId);
        }, "이력서 저장 실패");
    }


    // 이력서 관련 데이터 저장 로직
    private void saveRelatedData(ResumeDto resumeDto, Long resumeId) {
        // 학력 정보 저장
        for (EducationDto education : resumeDto.getEducations()) {
            education.setResumeId(resumeId);
            resumeDao.saveEducation(education);
        }
        // 경력 정보 저장
        for (CareerDto career : resumeDto.getCareers()) {
            career.setResumeId(resumeId);
            resumeDao.saveCareer(career);
        }
        // 자기소개서 정보 저장
        for (IntroduceMeDto introduce : resumeDto.getIntroduces()) {
            introduce.setResumeId(resumeId);
            resumeDao.saveIntroduce(introduce);
        }
        // 자격증 정보 저장
        for (QualificationDto qualification : resumeDto.getQualifications()) {
            qualification.setResumeId(resumeId);
            resumeDao.saveQualification(qualification);
        }
        // 기술 스택 정보 저장
        for (TechnicalStackDto stack : resumeDto.getTechnicalStacks()) {
            stack.setResumeId(resumeId);
            resumeDao.saveTechnicalStack(stack);
        }
        // 우대 사항 저장
        for (TreatDto treat : resumeDto.getTreats()) {
            treat.setResumeId(resumeId);
            resumeDao.saveTreat(treat);
        }
        // 포트폴리오 저장
        for (PortfolioDto portfolio : resumeDto.getPortfolios()) {
            portfolio.setResumeId(resumeId);
            resumeDao.savePortfolio(portfolio);
        }
    }

    public ResumeDto getResumeById(Long id) {

        return resumeDao.getResumeById(id);
    }


    // 이력서 수정 TODO:작성중
    public void updateResume(String id, ResumeDto resumeDto) {
    }

    @Transactional
    public void setRepresentativeResume(Long resumeId, String userId) {
        // 기존의 모든 대표 이력서를 'N'으로 변경
        resumeDao.updateAllResumesToNonRepresentative(userId);
        // 특정 이력서를 대표 이력서로 설정
        resumeDao.updateResumeToRepresentative(resumeId);
    }


    /*
    * 오류확인
    * */
    private void executeSafely(Runnable action, String errorMessage) {
        try {
            action.run();
        } catch (Exception e) {
            log.error("{}: {}", errorMessage, e.getMessage());
            throw new BaseException(ResultType.SERVER_ERROR, errorMessage, e);
        }
    }
}

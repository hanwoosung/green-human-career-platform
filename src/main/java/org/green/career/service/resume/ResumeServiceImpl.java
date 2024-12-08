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
       return resumeDao.getUserInfo(id);
    }
    public List<ResumeDto> getAllResumes(String userId) {
        return resumeDao.getResumesByUserId(userId);
    }



    public List<EducationDto> getEducationsByUserId(String userId) {
        return resumeDao.findEducationsByUserId(userId);
    }
    
    public List<CareerDto> getCareersByUserId(String userId) {
        return resumeDao.findCareersByUserId(userId);
    }
    
    public List<QualificationDto> getQualificationsByUserId(String userId) {
        return resumeDao.findQualificationsByUserId(userId);
    }


    public Map<String, List<TechnicalStackDto>> getAllTechnicalStacks() {
        // 모든 기술 스택 조회
        List<TechnicalStackDto> allStacks = resumeDao.selectAllTechnicalStacks();
        
        // 결과를 담을 새로운 맵 생성 (순서 유지를 위해 LinkedHashMap 사용)
        Map<String, List<TechnicalStackDto>> translatedStacks = new LinkedHashMap<>();
        
        // 카테고리별로 리스트 생성
        List<TechnicalStackDto> frontEnd = new ArrayList<>();
        List<TechnicalStackDto> backEnd = new ArrayList<>();
        List<TechnicalStackDto> data = new ArrayList<>();
        List<TechnicalStackDto> cloud = new ArrayList<>();
        List<TechnicalStackDto> mobile = new ArrayList<>();
        List<TechnicalStackDto> devops = new ArrayList<>();
        List<TechnicalStackDto> design = new ArrayList<>();
        
        // 각 기술 스택을 해당하는 카테고리에 분류
        for(TechnicalStackDto stack : allStacks) {
            switch(stack.getCategoryCode()) {
                case "front_cd": frontEnd.add(stack); break;
                case "back_cd": backEnd.add(stack); break;
                case "data_cd": data.add(stack); break;
                case "cloud_cd": cloud.add(stack); break;
                case "mobile_cd": mobile.add(stack); break;
                case "devops_cd": devops.add(stack); break;
                case "design_cd": design.add(stack); break;
            }
        }
        
        // 한글 카테고리명으로 매핑
        translatedStacks.put("프론트엔드", frontEnd);
        translatedStacks.put("백엔드", backEnd);
        translatedStacks.put("데이터", data);
        translatedStacks.put("클라우드", cloud);
        translatedStacks.put("모바일", mobile);
        translatedStacks.put("데브옵스", devops);
        translatedStacks.put("디자인", design);
        
        return translatedStacks;
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

    @Transactional
    public void updateResumeInDatabase(ResumeDto resumeDto, MultipartFile profilePhoto,
                                     List<MultipartFile> portfolioFiles, List<MultipartFile> introduceMeFiles) {
        Long resumeId = resumeDto.getResumeId();
        
        // 기존 데이터 삭제
        resumeDao.deleteEducationByResumeId(resumeId);
        resumeDao.deleteCareerByResumeId(resumeId);
        resumeDao.deleteIntroduceMeByResumeId(resumeId);
        resumeDao.deleteQualificationByResumeId(resumeId);
        resumeDao.deleteTechnicalStacks(resumeId);
        resumeDao.deleteTreats(resumeId);
        resumeDao.deletePortfolioByResumeId(resumeId);
        
        // 기본 이력서 정보 업데이트
        resumeDao.updateResume(resumeDto);
        
        // 파일 처리
        try {
            if (profilePhoto != null && !profilePhoto.isEmpty()) {
                resumeFileService.deleteProfilePhoto(resumeId, resumeDto.getId());
                resumeFileService.saveFile(profilePhoto, resumeDto, "resume_profile", resumeId, "50");
            }
            
            // 포트폴리오 파일 처리
            if (portfolioFiles != null && !portfolioFiles.isEmpty()) {
                resumeFileService.deleteFiles(resumeId, "40"); // 기존 포트폴리오 파일 삭제
                for (MultipartFile file : portfolioFiles) {
                    resumeFileService.saveFile(file, resumeDto, "portfolio", resumeId, "40");
                }
            }
            
            // 자기소개서 파일 처리
            if (introduceMeFiles != null && !introduceMeFiles.isEmpty()) {
                resumeFileService.deleteFiles(resumeId, "35"); // 기존 자기소개서 파일 삭제
                for (MultipartFile file : introduceMeFiles) {
                    resumeFileService.saveFile(file, resumeDto, "introduceMe", resumeId, "35");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 처리 중 오류 발생", e);
        }
        
        // 관련 데이터 저장
        saveRelatedData(resumeDto, resumeId);
    }

    
    private void updateEducations(Long resumeId, List<EducationDto> educations) {
        executeSafely(() -> {
            // 기존 학력 삭제 후 새로 추가
            resumeDao.deleteEducationByResumeId(resumeId);
            for (EducationDto education : educations) {
                education.setResumeId(resumeId);
                resumeDao.saveEducation(education);
            }
        }, "학력 정보 업데이트 실패");
    }

    private void updateCareers(Long resumeId, List<CareerDto> careers) {
        executeSafely(() -> {
            resumeDao.deleteCareerByResumeId(resumeId);
            for (CareerDto career : careers) {
                career.setResumeId(resumeId);
                resumeDao.saveCareer(career);
            }
        }, "경력 정보 업데이트 실패");
    }

    private void updateQualifications(Long resumeId, List<QualificationDto> qualifications) {
        executeSafely(() -> {
            resumeDao.deleteQualificationByResumeId(resumeId);
            for (QualificationDto qualification : qualifications) {
                qualification.setResumeId(resumeId);
                resumeDao.saveQualification(qualification);
            }
        }, "자격증 정보 업데이트 실패");
    }

    private void updatePortfolios(Long resumeId, List<PortfolioDto> portfolios, List<MultipartFile> files) {
        executeSafely(() -> {
            resumeDao.deletePortfolioByResumeId(resumeId);
            for (PortfolioDto portfolio : portfolios) {
                portfolio.setResumeId(resumeId);
                resumeDao.savePortfolio(portfolio);
            }
            // 파일 저장 로직 추가 (필요할 경우)
        }, "포트폴리오 정보 업데이트 실패");
    }

    private void updateIntroduces(Long resumeId, List<IntroduceMeDto> introduces, List<MultipartFile> files) {
        executeSafely(() -> {
            resumeDao.deleteIntroduceMeByResumeId(resumeId);
            for (IntroduceMeDto introduce : introduces) {
                introduce.setResumeId(resumeId);
                resumeDao.saveIntroduce(introduce);
            }
            // 파일 저장 로직 추가 (필요할 경우)
        }, "자기소개서 정보 업데이트 실패");
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

    @Transactional
    public void setRepresentativeResume(Long resumeId, String userId) {
        // 기존의 모든 대표 이력서를 'N'으로 변경
        resumeDao.updateAllResumesToNonRepresentative(userId);
        // 특정 이력서를 대표 이력서로 설정
        resumeDao.updateResumeToRepresentative(resumeId);
    }


    private void executeSafely(Runnable action, String errorMessage) {
        try {
            action.run();
        } catch (Exception e) {
            log.error("{}: {}", errorMessage, e.getMessage());
            throw new BaseException(ResultType.SERVER_ERROR, errorMessage, e);
        }
    }
}

package org.green.career.dao.resume;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.green.career.dto.common.CodeInfoDto;
import org.green.career.dto.resume.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 작성자: 구경림
 * 작성일: 2024-12-04
 * 이력서에서 사용중인 매퍼 인터페이스
 */
@Mapper
public interface ResumeDao {

    List<EducationDto> findEducationsByUserId(String userId) ;
    List<CareerDto> findCareersByUserId(String userId);
    List<QualificationDto> findQualificationsByUserId(String userId);

    
    void updateAllResumesToNonRepresentative(String userId);

    // 특정 이력서를 대표 이력서로 설정
    void updateResumeToRepresentative(Long resumeId);

    ResumeDto getResumeById(Long id);

    List<TreatDto> getAllTreatCodes();

    void saveResume(ResumeDto resumeDto);

    void saveEducation(EducationDto educationDto);

    void saveCareer(CareerDto careerDto);

    void saveQualification(QualificationDto qualificationDto);

    void saveTechnicalStack(TechnicalStackDto technicalStackDto);

    void saveTreat(TreatDto treatDto);

    void savePortfolio(PortfolioDto portfolioDto);

    Map<String, List<TechnicalStackDto>> getAllTechnicalStacks();
    List<TechnicalStackDto> selectAllTechnicalStacks();

    void saveIntroduce(IntroduceMeDto introduceMeDto);

    ResumeDto getUserInfo(String id);

    List<ResumeDto> getResumesByUserId(String userId);

    void deleteResume(String resumeId);

    void saveFile(ResumeFileDto resumeFileDto);

    void updateResume(ResumeDto resumeDto);

    void deleteEducationByResumeId(Long resumeId);
    void deleteCareerByResumeId(Long resumeId);
    void deleteQualificationByResumeId(Long resumeId);
    void deleteIntroduceMeByResumeId(Long resumeId);
    void deletePortfolioByResumeId(Long resumeId);
    void deleteTechnicalStacks(Long resumeId);
    void deleteTreats(Long resumeId);


    List<ResumeFileDto> findFilesByRefIdAndGbnCd(Map<String, Object> params);
    void deleteFilesByRefId(Map<String, Object> params);

    
    ResumeFileDto findFileById(Long fileId);    


}
package org.green.career.dao.resume;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.green.career.dto.common.CodeInfoDto;
import org.green.career.dto.resume.*;

import java.util.List;

/**
 * 작성자: 구경림
 * 작성일: 2024-12-04
 * 이력서에서 사용중인 매퍼 인터페이스
 */
@Mapper
public interface ResumeDao {
    void saveResume(ResumeDto resumeDto);

    void saveEducation(EducationDto educationDto);

    void saveCareer(CareerDto careerDto);

    void saveQualification(QualificationDto qualificationDto);

    void saveTechnicalStack(TechnicalStackDto technicalStackDto);

    void saveTreat(TreatDto treatDto);

    void savePortfolio(PortfolioDto portfolioDto);

    List<TechnicalStackDto> getTechnicalStacks();

    List<TechnicalStackDto> getTechnicalStacksByCategory(@Param("categoryCode") String categoryCode);

    // 특정 코드 카테고리에 해당하는 코드 가져오기
    List<CodeInfoDto> getCodeByCategory(@Param("category") String category);

    void saveIntroduce(IntroduceMeDto introduceMeDto);

    ResumeDto getUserInfo(String id);

    List<ResumeDto> getResumesByUserId(String userId);

    List<ResumeDto> getAllResumes();

    void updateResume(ResumeDto resumeDto);

    void deleteResume(String resumeId);

    // 파일 저장
    void saveFile(ResumeFileDto resumeFileDto);


}
package org.green.career.service.resume;

import org.green.career.dto.resume.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ResumeService {

    ResumeDto getUserInfo(String id);

    List<ResumeDto> getAllResumes(String userId);

    Map<String, List<TechnicalStackDto>> getAllTechnicalStacks();

    List<TreatDto> getAllTreatCodes();

    void deleteResume(String resumeId);

    void saveResumeToDatabase(ResumeDto resumeDto, MultipartFile profilePicture,
                              List<MultipartFile> portfolioFiles,
                              List<MultipartFile> introduceMeFiles);

    ResumeDto getResumeById(Long id);

    void updateResume(String id, ResumeDto resumeDto);

    void setRepresentativeResume(Long resumeId, String userId);
}

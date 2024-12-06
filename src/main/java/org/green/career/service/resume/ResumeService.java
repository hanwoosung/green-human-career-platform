package org.green.career.service.resume;

import org.green.career.dto.resume.ResumeFileDto;
import org.green.career.dto.resume.ResumeDto;
import org.green.career.dto.resume.TechnicalStackDto;
import org.green.career.dto.resume.TreatDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ResumeService {

    ResumeDto getResumeById(Long id);

    /*
    * Get
    * */

    ResumeDto getUserInfo(String id);
    List<TreatDto> getAllTreatCodes();
    List<ResumeDto> getAllResumes(String id);

    Map<String, List<TechnicalStackDto>> getAllTechnicalStacks();

    void deleteResume(String resumeId);


    /*
    * Save
    * */

    void saveResumeToDatabase(ResumeDto resumeDto, MultipartFile profilePicture, List<MultipartFile> portfolioFiles, List<MultipartFile> introduceMeFiles);

    ResumeFileDto saveProfilePicture(MultipartFile profilePicture, String userId, Long generatedResumeId) throws IOException;

    void savePortfolioFiles(String userId, List<MultipartFile> portfolioFiles, Long generatedResumeId) throws IOException;

    void saveIntroduceMeFiles(String userId, List<MultipartFile> introduceMeFiles, Long generatedResumeId) throws IOException;

    String saveFile(MultipartFile file, String userId, String directory) throws IOException;

    void setRepresentativeResume(Long resumeId, String loginedUser);
}

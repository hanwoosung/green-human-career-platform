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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeServiceImpl extends AbstractService implements ResumeService {

    private final ResumeDao resumeDao;

    public ResumeDto getUserInfo(String id) {
        // AbstractService의 returnData 사용
        return returnData(() -> resumeDao.getUserInfo(id));
    }

    public List<ResumeDto> getAllResumes(String id) { //TODO: 빈값처리
        return resumeDao.getResumesByUserId(id);
    }

    public Map<String, List<TechnicalStackDto>> getAllTechnicalStacks() {
        // 카테고리별로 기술 스택을 맵 형태로 반환
        Map<String, List<TechnicalStackDto>> stacksByCategory = new HashMap<>();
        stacksByCategory.put("back_cd", resumeDao.getTechnicalStacksByCategory("back_cd"));
        stacksByCategory.put("design_cd", resumeDao.getTechnicalStacksByCategory("design_cd"));
        stacksByCategory.put("mobile_cd", resumeDao.getTechnicalStacksByCategory("mobile_cd"));
        stacksByCategory.put("front_cd", resumeDao.getTechnicalStacksByCategory("front_cd"));
        return stacksByCategory;
    }

    public List<TreatDto> getAllTreatCodes() {
        return returnData(() -> resumeDao.getAllTreatCodes());
    }

    public void deleteResume(String resumeId) {
        // AbstractService의 executeSafely 사용
        executeSafely(() -> resumeDao.deleteResume(resumeId), "이력서 삭제 실패");
    }

    @Transactional
    public void saveResumeToDatabase(ResumeDto resumeDto, MultipartFile profilePicture, List<MultipartFile> portfolioFiles, List<MultipartFile> introduceMeFiles) {
        // 기본 이력서 저장
        executeSafely(() -> resumeDao.saveResume(resumeDto), "이력서 저장 실패");

        Long generatedResumeId = resumeDto.getResumeId();
        if (generatedResumeId == null) {
            throw new IllegalStateException("이력서 ID가 생성되지 않았습니다.");
        }

        try {
            // 프로필 사진 저장
            if (profilePicture != null && !profilePicture.isEmpty()) {
                saveProfilePicture(profilePicture, resumeDto.getCreatedBy(), generatedResumeId);
            }

            // 포트폴리오 파일 저장
            if (portfolioFiles != null && !portfolioFiles.isEmpty()) {
                savePortfolioFiles(resumeDto.getCreatedBy(), portfolioFiles, generatedResumeId);
            }

            // 자기소개서 파일 저장
            if (introduceMeFiles != null && !introduceMeFiles.isEmpty()) {
                saveIntroduceMeFiles(resumeDto.getCreatedBy(), introduceMeFiles, generatedResumeId);
            }


            // 나머지 데이터 저장 로직
            for (EducationDto education : resumeDto.getEducations()) {
                education.setResumeId(generatedResumeId);
                resumeDao.saveEducation(education);
            }

            for (CareerDto career : resumeDto.getCareers()) {
                career.setResumeId(generatedResumeId);
                resumeDao.saveCareer(career);
            }

            for (IntroduceMeDto itroduce : resumeDto.getIntroduces()) {
                itroduce.setResumeId(generatedResumeId);
                resumeDao.saveIntroduce(itroduce);
            }

            for (QualificationDto quali : resumeDto.getQualifications()) {
                quali.setResumeId(generatedResumeId);
                resumeDao.saveQualification(quali);
            }

            for (TechnicalStackDto stack : resumeDto.getTechnicalStacks()) {
                stack.setResumeId(generatedResumeId);
                resumeDao.saveTechnicalStack(stack);
            }

            for (TreatDto treat : resumeDto.getTreats()) {
                treat.setResumeId(generatedResumeId);
                resumeDao.saveTreat(treat);
            }

            for (PortfolioDto port : resumeDto.getPortfolios()) {
                port.setResumeId(generatedResumeId);
                resumeDao.savePortfolio(port);
                System.out.println(port);
            }

        } catch (IOException e) {
            throw new RuntimeException("파일 저장 중 오류가 발생했습니다.", e);
        }
    }   // 특정 코드 카테고리에 해당하는 코드 가져오기


    public ResumeDto getResumeById(Long id) {
        return resumeDao.getResumeById(id);
    }


    // 이력서 조회
    public ResumeDto getResumeById(String id) {
        // 데이터베이스에서 해당 id의 이력서를 조회하여 반환하는 로직
        // 예시로 임시 객체 반환
        ResumeDto resumeDto = new ResumeDto();

        return resumeDto;
    }

    // 이력서 수정
    public ResumeDto updateResume(String id, ResumeDto resumeDto) {
        // 데이터베이스에서 id를 기준으로 해당 이력서를 수정하는 로직
        // 수정된 이력서를 반환
        resumeDto.setResumeId(1L);  // 예시로 수정된 이력서 ID를 반환
        return resumeDto;
    }

    @Transactional
    public void setRepresentativeResume(Long resumeId, String userId) {
        // 기존의 모든 대표 이력서를 'N'으로 변경
        resumeDao.updateAllResumesToNonRepresentative(userId);
        // 특정 이력서를 대표 이력서로 설정
        resumeDao.updateResumeToRepresentative(resumeId);
    }


    /*
     * 파일 관련
     * */
    public ResumeFileDto saveProfilePicture(MultipartFile profilePicture, String userId, Long generatedResumeId) throws IOException {
        String filePath = saveFile(profilePicture, userId, "profile");
        System.out.println(filePath);

        ResumeFileDto resumeFileDto = new ResumeFileDto();
        resumeFileDto.setFileGbnCd("50");
        resumeFileDto.setFileRefId(generatedResumeId); // 이력서 ID를 참조 ID로 설정
        resumeFileDto.setInstId(userId);
        resumeFileDto.setFileExt(getFileExtension(profilePicture.getOriginalFilename()));
        resumeFileDto.setFileName(profilePicture.getOriginalFilename());
        resumeFileDto.setFileUrl(filePath);

        // 파일 정보를 DB에 저장
        resumeDao.saveFile(resumeFileDto);

        return resumeFileDto;
    }

    public void savePortfolioFiles(String userId, List<MultipartFile> portfolioFiles, Long generatedResumeId) throws IOException {
        List<PortfolioDto> portfolios = new ArrayList<>();

        for (MultipartFile file : portfolioFiles) {
            String portfolioFilePath = saveFile(file, userId, "portfolio");

            ResumeFileDto resumeFileDto = new ResumeFileDto();
            resumeFileDto.setFileGbnCd("40"); // 포트폴리오 코드
            resumeFileDto.setFileRefId(generatedResumeId); // 이력서 ID를 참조 ID로 설정
            resumeFileDto.setInstId(userId);
            resumeFileDto.setFileExt(getFileExtension(file.getOriginalFilename()));
            resumeFileDto.setFileName(file.getOriginalFilename());
            resumeFileDto.setFileUrl(portfolioFilePath);

            // 파일 정보를 DB에 저장
            resumeDao.saveFile(resumeFileDto);

            PortfolioDto portfolioDto = new PortfolioDto();
            portfolioDto.setPortfolioUrl(portfolioFilePath);
            portfolioDto.setDescription(file.getOriginalFilename());
            portfolios.add(portfolioDto);
        }
    }

    public void saveIntroduceMeFiles(String userId, List<MultipartFile> introduceMeFiles, Long generatedResumeId) throws IOException {
        List<IntroduceMeDto> introduces = new ArrayList<>();

        for (MultipartFile file : introduceMeFiles) {
            // 사용자별 디렉토리와 자기소개서 저장 경로 설정
            String introduceFilePath = saveFile(file, userId, "introduceMe");

            // 파일 정보 생성 및 DB 저장
            ResumeFileDto resumeFileDto = new ResumeFileDto();
            resumeFileDto.setFileGbnCd("35"); // 자기소개서 파일 코드
            resumeFileDto.setFileRefId(generatedResumeId); // 이력서 ID를 참조 ID로 설정
            resumeFileDto.setInstId(userId);
            resumeFileDto.setFileExt(getFileExtension(file.getOriginalFilename()));
            resumeFileDto.setFileName(file.getOriginalFilename());
            resumeFileDto.setFileUrl(introduceFilePath);

            // 파일 정보를 DB에 저장
            resumeDao.saveFile(resumeFileDto);

            // 자기소개서 DTO 생성 및 데이터 설정
            IntroduceMeDto introduceDto = new IntroduceMeDto();
            introduceDto.setContent(introduceFilePath);
            introduceDto.setTitle(file.getOriginalFilename());
            introduces.add(introduceDto);
        }
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }

    public String saveFile(MultipartFile file, String userId, String directory) throws IOException {
        // 사용자별 경로를 추가
        return saveFileInternal(file, userId, directory);
    }

    private String saveFileInternal(MultipartFile file, String userId, String directory) throws IOException {
        // 사용자 ID 기반 디렉토리 경로 구성
        String baseDirectory = "src/main/resources/static/uploads/user/";
        String saveDirectory = baseDirectory + userId + "/" + directory;

        // 디렉토리 경로 생성
        Path directoryPath = Paths.get(saveDirectory);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        // 고유 파일 이름 생성
        String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = directoryPath.resolve(uniqueFileName);

        // 파일 저장
        try {
            Files.copy(file.getInputStream(), filePath);
            System.out.println("파일 저장 성공: " + filePath);
        } catch (IOException e) {
            System.err.println("파일 저장 실패: " + e.getMessage());
            throw e;
        }

        // 저장된 파일 경로 반환
        return filePath.toString();
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

package org.green.career.service.resume;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dao.resume.ResumeDao;
import org.green.career.dto.resume.ResumeDto;
import org.green.career.dto.resume.ResumeFileDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class ResumeFileService {

    private final ResumeDao resumeDao;
    @Value("${file.upload-dir-common}")
    private String uploadDir;
    private String baseUrl = "/static/uploads/user";

    public ResumeFileService(ResumeDao resumeDao) {
        this.resumeDao = resumeDao;
    }

    public void saveFile(MultipartFile file, ResumeDto resumeDto, String subDir, Long refId, String fileGbnCd) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("빈 파일은 저장할 수 없습니다.");
        }

        // 파일 이름 생성
        String originalFilename = file.getOriginalFilename();
        String fileExt = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf('.') + 1) : "unknown";

        String prefix = getPrefix(subDir);

        // 고유 파일 이름 생성: userId_이력서프로필_yyyyMMddHHmmss.확장자
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        log.info("기본 유저 정보 : {}", resumeDto);
        String uniqueFileName = resumeDto.getName() + "_" + prefix + "_" + timestamp + "." + fileExt;

        // 저장 경로 설정
        Path targetDir = Paths.get(uploadDir+"/user", subDir);
        System.out.println(targetDir);
        Files.createDirectories(targetDir); // 디렉토리 생성
        Path targetPath = targetDir.resolve(uniqueFileName);
        file.transferTo(targetPath.toFile());

        // 파일 정보 DTO 생성
        ResumeFileDto fileDto = new ResumeFileDto();
        fileDto.setFileRefId(refId);
        fileDto.setFileName(uniqueFileName);
        fileDto.setInstId(resumeDto.getId());
        fileDto.setFileExt(fileExt);
        fileDto.setFileGbnCd(fileGbnCd);
        fileDto.setFileUrl(baseUrl + "/" + subDir + "/" + uniqueFileName);

        System.out.println("파일 저장 성공 :" + fileDto);

        resumeDao.saveFile(fileDto);

    }

    private String getPrefix(String subDir) {
        switch (subDir) {
            case "introduceMe": return "자기소개서";
            case "portfolio": return "포트폴리오";
            case "resume_profile": return "이력서프로필";
            default: return "기타";
        }
    }

    public Resource downloadFile(String fileUrl) throws IOException {
        Path filePath = Paths.get(fileUrl.replace(baseUrl, uploadDir));
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new IOException("파일을 읽을 수 없습니다: " + fileUrl);
        }
        return resource;
    }

}

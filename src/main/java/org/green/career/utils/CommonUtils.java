package org.green.career.utils;

import lombok.extern.slf4j.Slf4j;
import org.green.career.dto.common.file.request.TblFileRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 작성자: 한우성
 * 작성일: 2024-11-30
 * 파일 유틸
 * TODO : 파일이름 저장방식 수정필요
 */
@Component
@Slf4j
public class CommonUtils {

    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * 채용공고 이미지를 서버에 저장하고, 저장된 파일 정보를 담은 DTO 리스트를 반환
     *
     * @param companyImages 업로드된 이미지 파일들의 목록
     * @param id            채용 공고의 ID (파일 참조용)
     * TODO : 대대적인 수정 있을 예정
     */
    public List<TblFileRequestDto> saveCompanyImages(List<MultipartFile> companyImages, int id) throws Exception {
        List<TblFileRequestDto> fileList = new ArrayList<>();
        for (MultipartFile file : companyImages) {
            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                String fileName = originalFilename != null ? originalFilename : "unknown";
                String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
                String baseName = fileName.substring(0, fileName.lastIndexOf("."));

                String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                String uniqueFileName = baseName + "_" + timestamp + "." + fileExt;

                Path path = Paths.get(uploadDir, uniqueFileName);
                Files.createDirectories(path.getParent());
                file.transferTo(path.toFile());

                TblFileRequestDto fileDto = new TblFileRequestDto();
                fileDto.setFileGbnCd("100");
                fileDto.setFileRefId(String.valueOf(id));
                fileDto.setFileName(uniqueFileName);
                fileDto.setFileExt(fileExt);
                fileDto.setFileUrl("/static/uploads/company/" + uniqueFileName);
                fileList.add(fileDto);
            }
        }
        return fileList;
    }

    /**
     * 주어진 경로의 파일을 삭제
     * @param filePath 삭제할 파일의 경로
     */
    public boolean deleteFile(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            log.warn("파일 경로가 유효하지 않습니다.");
            return false;
        }

        try {
            String absolutePath = Paths.get(uploadDir, filePath.replace("/static/uploads/company/", "")).toString();

            File file = new File(absolutePath);
            if (file.exists()) {
                boolean result = file.delete();
                if (result) {
                    log.info("파일 {} 삭제 성공", absolutePath);
                } else {
                    log.warn("파일 {} 삭제 실패", absolutePath);
                }
                return result;
            } else {
                log.warn("파일 {}이(가) 존재하지 않습니다.", absolutePath);
                return true;
            }
        } catch (Exception e) {
            log.error("파일 삭제 중 예외 발생: {}", e.getMessage());
            return false;
        }
    }

}

package org.green.career.utils;

import org.green.career.dto.common.file.request.TblFileRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CommonUtils {

    @Value("${file.upload-dir}")
    private String uploadDir;

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
}

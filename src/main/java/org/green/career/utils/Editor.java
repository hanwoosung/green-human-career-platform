package org.green.career.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class Editor {

    @Value("${file.upload-dir-common}")
    private String uploadDir;

//    file.upload-dir-common=${user.dir}/src/main/resources/static/uploads/

    public String saveFile(MultipartFile file,
                           String url) throws IOException {

        String originalFilename = file.getOriginalFilename();
        System.out.println(originalFilename);
        String fileName = originalFilename != null ? originalFilename : "unknown";
        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
        String baseName = fileName.substring(0, fileName.lastIndexOf("."));

        String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String uniqueFileName = baseName + "_" + timestamp + "." + fileExt;
        System.out.println(uniqueFileName);
        Path path = Paths.get(uploadDir, "editor/", uniqueFileName);
        Files.createDirectories(path.getParent());
        file.transferTo(path.toFile());

        url += "static/uploads/editor/" + uniqueFileName;

        return url;
    }

}

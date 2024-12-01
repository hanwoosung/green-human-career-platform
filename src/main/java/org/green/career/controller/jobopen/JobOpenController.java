package org.green.career.controller.jobopen;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.controller.AbstractController;
import org.green.career.dto.common.ResponseDto;
import org.green.career.dto.jobopen.requset.JobOpeningRequestDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/job-open")
@RequiredArgsConstructor
@Slf4j
public class JobOpenController extends AbstractController {

    @GetMapping("/register")
    public String jobOpenRegister() {
        return "jobopen/job_open_register";
    }

    @PostMapping
    @ResponseBody
    public ResponseDto<Void> jobOpeningRegister(
            @RequestBody JobOpeningRequestDto formData) {

        log.info("데이터: {}", formData);

        if (formData.getCompanyImages() != null && !formData.getCompanyImages().isEmpty()) {
            for (var file : formData.getCompanyImages()) {
                try {
                    // 파일 정보 출력
                    log.info("파일이름: {}", file.getName());
                    log.info("사이즈: {} bytes", file.getSize());


                } catch (Exception e) {
                    log.error("에러에러: {}", file.getName(), e);
                }
            }
        } else {
            log.warn("파일없음");
        }

        return ResponseDto.ok();
    }

}

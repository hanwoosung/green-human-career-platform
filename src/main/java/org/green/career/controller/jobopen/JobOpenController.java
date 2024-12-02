package org.green.career.controller.jobopen;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.controller.AbstractController;
import org.green.career.dto.common.CodeInfoDto;
import org.green.career.dto.common.ResponseDto;
import org.green.career.dto.common.file.request.FileRequestDto;
import org.green.career.dto.jobopen.requset.JobOpeningRequestDto;
import org.green.career.service.jobopen.JobOpeningService;
import org.green.career.service.main.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/job-open")
@RequiredArgsConstructor
@Slf4j
public class JobOpenController extends AbstractController {

    private final JobOpeningService jobOpeningService;
    private final MainService mainService;

    @GetMapping("/register")
    public String getJobOpenRegisterPage(Model model) {
        Map<String, Object> skillData = mainService.findSkillList();

        model.addAttribute("skillList", skillData.get("skills"));
        model.addAttribute("categories", skillData.get("categories"));


        return "jobopen/job_open_register";
    }


    @PostMapping
    @ResponseBody
    public ResponseDto<Void> jobOpeningRegister(@ModelAttribute JobOpeningRequestDto formData) {

        log.info("데이터: {}", formData);

        if (formData.getSkillList() != null) {
            log.info("Skill List: {}", formData.getSkillList());
        }

        if (formData.getCompanyImages() != null && !formData.getCompanyImages().isEmpty()) {
            for (MultipartFile file : formData.getCompanyImages()) {
                log.info("파일이름: {}", file.getName());
                log.info("사이즈: {} bytes", file.getSize());
            }
        } else {
            log.warn("파일 없음");
        }

        jobOpeningService.insertJobOpening(formData);

        return ResponseDto.ok();
    }


}

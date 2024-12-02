package org.green.career.controller.jobopen;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.controller.AbstractController;
import org.green.career.dto.common.ResponseDto;
import org.green.career.dto.jobopen.requset.JobOpeningRequestDto;
import org.green.career.service.jobopen.JobOpeningService;
import org.green.career.service.main.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 작성자: 한우성
 * 작성일: 2024-11-30
 * 채용공고 등록 수정 상세 삭제
 */
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

    @GetMapping("/{jNo}")
    public String getJobOpeningModi(@PathVariable("jNo") int jNo, Model model) {
        Map<String, Object> skillData = mainService.findSkillList();
        model.addAttribute("skillList", skillData.get("skills"));
        model.addAttribute("jobItem", jobOpeningService.getJobOpening(jNo));
        System.out.println(jobOpeningService.getJobOpening(jNo));
        return "jobopen/job_open_modi";
    }

    @PutMapping("/{jNo}")
    @ResponseBody
    public ResponseDto<Void> modifyJobOpening(
            @PathVariable("jNo") int jNo,
            @ModelAttribute JobOpeningRequestDto formData,
            @RequestParam(value = "filesToDelete", required = false) List<Long> filesToDelete,
            @RequestParam(value = "addedSkills", required = false) List<String> addedSkills,
            @RequestParam(value = "removedSkills", required = false) List<String> removedSkills) throws Exception {


        int result = jobOpeningService.updateJobOpening(jNo, formData);
        if (result != 0) {
            //TODO: 로그 필요없을 때 제거
            if (filesToDelete != null && !filesToDelete.isEmpty()) {
                log.info("삭제 요청된 파일 ID: {}", filesToDelete);
                jobOpeningService.deleteFileDB(filesToDelete, jNo);
            }

            if (formData.getCompanyImages() != null && !formData.getCompanyImages().isEmpty()) {
                log.info("새로 업로드된 파일 개수: {}", formData.getCompanyImages().size());
                jobOpeningService.addFiles(jNo, formData.getCompanyImages());
            }

            // 스킬 추가 처리
            if (addedSkills != null && !addedSkills.isEmpty()) {
                log.info("추가된 스킬: {}", addedSkills);
                jobOpeningService.addSkills(jNo, addedSkills);
            }

            // 스킬 삭제 처리
            if (removedSkills != null && !removedSkills.isEmpty()) {
                log.info("삭제된 스킬: {}", removedSkills);
                jobOpeningService.removeSkills(jNo, removedSkills);
            }

        }
        return ResponseDto.ok();
    }
}
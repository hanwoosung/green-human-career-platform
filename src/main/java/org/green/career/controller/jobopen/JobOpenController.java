package org.green.career.controller.jobopen;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.controller.AbstractController;
import org.green.career.dto.common.ResponseDto;
import org.green.career.dto.jobopen.JobOpeningDetailDto;
import org.green.career.dto.jobopen.requset.JobOpeningRequestDto;
import org.green.career.dto.jobopen.response.ResponseMyResume;
import org.green.career.dto.likes.response.ResponseLikesDto;
import org.green.career.service.company.jbskMngm.bookmarkSeeker.BookmarkSeekerService;
import org.green.career.service.jobopen.JobOpeningService;
import org.green.career.service.likes.LikesService;
import org.green.career.service.main.MainService;
import org.green.career.service.sse.SseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private final LikesService likesService;
    private final BookmarkSeekerService bookmarkSeekerService;
    private final SseService sseService;

    @GetMapping("/register")
    public String getJobOpenRegisterPage(Model model) throws Exception {
        sessionGoLogin();
        ifGoReferer((!Objects.equals(sessionUserInfo("userType"), "C")));
        Map<String, Object> skillData = mainService.findSkillList();

        model.addAttribute("skillList", skillData.get("skills"));
        model.addAttribute("categories", skillData.get("categories"));


        return "jobopen/job_open_register";
    }

    @PostMapping
    @ResponseBody
    public ResponseDto<Integer> jobOpeningRegister(@ModelAttribute JobOpeningRequestDto formData) {
        String id = sessionUserInfo("userId");

        int result = jobOpeningService.insertJobOpening(formData, id);

        if (result > 0) {
            List<String> bookmarkedUserIds = bookmarkSeekerService.selectBookmarkSeekerIds(id);

            if (!bookmarkedUserIds.isEmpty()) {
                String notificationMessage = jobOpeningService.getCompanyName(id) + "에서 새로운 채용공고가 등록되었습니다!" + jobOpeningService.selectMax();
                for (String userId : bookmarkedUserIds) {
                    sseService.sendNotification(userId, notificationMessage);
                }
            }
        }
        return ResponseDto.ok(result);
    }


    @GetMapping("/{jNo}")
    public String getJobOpeningModi(@PathVariable("jNo") int jNo, Model model) throws Exception {
        Map<String, Object> skillData = mainService.findSkillList();

        sessionGoLogin();
        model.addAttribute("skillList", skillData.get("skills"));
        model.addAttribute("mySkillList", jobOpeningService.mySkill(jNo));
        model.addAttribute("jobItem", jobOpeningService.getJobOpening(jNo));
        System.out.println(jobOpeningService.getJobOpening(jNo));
        return "jobopen/job_open_modi";
    }

    @PutMapping("/{jNo}")
    @ResponseBody
    public ResponseDto<Integer> modifyJobOpening(
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
        return ResponseDto.ok(jobOpeningService.selectMax());
    }

    @GetMapping("/detail/{jNo}")
    public String getJobOpeningDetail(@PathVariable("jNo") int jNo, Model model) throws Exception {
        jobOpeningService.viewCountUp(jNo);
        JobOpeningDetailDto jobOpening = jobOpeningService.getJobOpening(jNo);
        log.info(jobOpening.toString());

        ifGoReferer(jobOpening.getDelYn().equals("Y"));
        model.addAttribute("likes", isSessionCheck() == null ? new ResponseLikesDto(0, 0) : likesService.getLikes(jNo, jobOpening.getId()));
        model.addAttribute("jobItem", jobOpening);
        model.addAttribute("mySkillList", jobOpeningService.mySkill(jNo));
        model.addAttribute("companyItem", jobOpeningService.getCompany(jobOpening.getId()));
        model.addAttribute("resumeList", jobOpeningService.getResumeList(jNo));
        return "jobopen/job_open_detail";
    }

    @PostMapping("/pass")
    @ResponseBody
    public ResponseDto<String> jobOpeningPass(
            @RequestParam("jrNo") int jrNo,
            @RequestParam("type") String type) {
        jobOpeningService.jobOpeningPass(jrNo, type);
        return ok("성공");
    }

    @GetMapping("/my-resumes")
    @ResponseBody
    public ResponseDto<List<ResponseMyResume>> myResumes(HttpSession session) {
        String id = (String) session.getAttribute("userId");
        sessionApiError();

        return ok(jobOpeningService.myResumes(id));
    }

    @GetMapping("/resume-apply")
    @ResponseBody
    public ResponseDto<Integer> resumeApply(@RequestParam("rNo") int rNo,
                                            @RequestParam("jNo") int jNo,
                                            @RequestParam("jId") String jId,
                                            @RequestParam("userName") String userName) {

        int result = jobOpeningService.resumeApply(jNo, rNo, sessionUserInfo("userId"));
        if (result > 0) {
            sseService.sendNotification(jId, userName + "님이 이력서를 신청하였습니다");
        }
        return ResponseDto.ok(result);
    }

    @PatchMapping("/{jNo}")
    @ResponseBody
    public ResponseDto<String> deleteJob(@PathVariable("jNo") int jNo) {
        jobOpeningService.delete(jNo);
        return ok("성공");
    }
}
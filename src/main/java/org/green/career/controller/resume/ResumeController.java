package org.green.career.controller.resume;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.controller.AbstractController;
import org.green.career.dto.common.CodeInfoDto;
import org.green.career.dto.common.ResponseDto;
import org.green.career.dto.resume.ResumeFileDto;
import org.green.career.dto.resume.ResumeDto;
import org.green.career.dto.resume.TechnicalStackDto;
import org.green.career.dto.resume.TreatDto;
import org.green.career.service.resume.ResumeService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RequestMapping("/resume")
@Controller
@RequiredArgsConstructor
@Slf4j
public class ResumeController extends AbstractController {
    private final ResumeService resumeService;


    // 이력서 목록 페이지
    @GetMapping
    public String resume(Model model, HttpSession session) {
        // 세션에서 로그인된 사용자 정보 가져오기
        String loginedUser = (String) session.getAttribute("userId");
        System.out.println(loginedUser);
        if (loginedUser == null) {
            return "mypage_login";
        } else {

            List<ResumeDto> resumes = resumeService.getAllResumes(loginedUser);
            model.addAttribute("resumes", resumes);
            return "resume";
        }
    }


    @GetMapping("/regist")
    public String resume(HttpSession session, Model model) {
        // 세션에서 로그인된 사용자 정보 가져오기
        String loginedUser = (String) session.getAttribute("userId");
        System.out.println(loginedUser);

        if (loginedUser == null) {
            return "mypage_login";
        } else {
            // 사용자 기본 정보 가져오기
            ResumeDto userInfo = resumeService.getUserInfo(loginedUser);
            model.addAttribute("userInfo", userInfo);

            // 기술 스택 목록을 카테고리별로 가져오기
            Map<String, List<TechnicalStackDto>> technicalStacks = resumeService.getAllTechnicalStacks();
            model.addAttribute("technicalStacks", technicalStacks);
            // 우대사항 코드 목록 가져오기
            List<TreatDto> treatCodes = resumeService.getAllTreatCodes();
            model.addAttribute("treatCodes", treatCodes);

            return "resume_regist";
        }
    }

    @ResponseBody
    @PostMapping("/regist")
    public ResponseDto<Void> postResume(
            @RequestPart("resumeData") String resumeData, // 이력서 JSON 데이터
            @RequestPart(value = "profilePhoto", required = false) MultipartFile profilePhoto, // 프로필 사진
            @RequestPart(value = "portfolioFiles", required = false) List<MultipartFile> portfolioFiles, // 포트폴리오 파일들
            @RequestPart(value = "introduceMeFiles", required = false) List<MultipartFile> introduceMeFiles, // 자기소개서 파일들
            HttpSession session
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ResumeDto resumeDto = objectMapper.readValue(resumeData, ResumeDto.class);
        String userId = resumeDto.getCreatedBy();
        log.info("이력서 저장 요청: {}", resumeData);
        // 이력서 저장
        resumeService.saveResumeToDatabase(resumeDto, profilePhoto, portfolioFiles, introduceMeFiles);
        return ok();
    }
    // 이력서 상세 조회 (GET)
    @GetMapping("/{id}")
    public String resume(@PathVariable("id") Long id, Model model) {
        ResumeDto resume = resumeService.getResumeById(id);

        if (resume.getProfilePhoto() != null) {
            String profilePhotoUrl = resume.getProfilePhoto().getNormalizedFileUrl();
            System.out.println("Generated Profile Photo URL: " + profilePhotoUrl);
            model.addAttribute("profilePhotoUrl", profilePhotoUrl);
        } else {
            model.addAttribute("profilePhotoUrl", "/static/images/default_profile2.png");
        }

        model.addAttribute("resume", resume);
        return "resume_detail";
    }



    // 이력서 삭제 (DELETE)
    @ResponseBody
    @DeleteMapping("/{resumeId}")
    public ResponseDto<Void> deleteResume(@PathVariable String resumeId,HttpSession session) {
        resumeService.deleteResume(resumeId);
        return ok();
    }

    @ResponseBody
    @PutMapping("/{resumeId}/representative")
    public ResponseDto<Void> setRepresentativeResume(@PathVariable("resumeId") Long resumeId, HttpSession session) {
        // 진입 확인 로그 추가
        System.out.println("Entering setRepresentativeResume method with resumeId: " + resumeId);

        String loginedUser = (String) session.getAttribute("userId");


        // 현재 로그인된 사용자 ID 확인 로그 추가
        System.out.println("Logged in user ID: " + loginedUser);

        // 서비스 호출 전에 로그 추가
        System.out.println("Calling resumeService.setRepresentativeResume()");
        resumeService.setRepresentativeResume(resumeId, loginedUser);

        // 성공 로그
        System.out.println("Successfully updated representative resume");

        return ok();
    }


    // 파일 다운로드 엔드포인트
    @GetMapping("/file/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        try {
            // 서비스 호출을 통해 파일을 다운로드합니다.
            Resource resource = resumeService.downloadFile(fileId);

            // 파일 다운로드를 위한 응답 헤더 설정
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            throw new RuntimeException("파일 다운로드 중 오류가 발생했습니다.", e);
        }
    }


//    // 이력서 수정 (PUT)
//    @ResponseBody
//    @PutMapping("/api/{id}")
//    public ResponseDto<Void>  updateResume(@PathVariable("id") String id, @RequestBody ResumeDto resumeDto) {
////        resumeService.updateResume(id, resumeDto);
//        return ok();
//    }

}
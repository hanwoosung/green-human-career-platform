package org.green.career.controller.resume;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.tomcat.util.file.ConfigurationSource.Resource;
import org.green.career.controller.AbstractController;
import org.green.career.dto.common.ResponseDto;
import org.green.career.dto.common.file.request.FileDto;
import org.green.career.dto.resume.CareerDto;
import org.green.career.dto.resume.EducationDto;
import org.green.career.dto.resume.QualificationDto;
import org.green.career.dto.resume.ResumeDto;
import org.green.career.dto.resume.ResumeFileDto;
import org.green.career.dto.resume.TechnicalStackDto;
import org.green.career.dto.resume.TreatDto;
import org.green.career.service.resume.ResumeFileService;
import org.green.career.service.resume.ResumeService;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
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
    private final ResumeFileService resumeFileService;

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
            model.addAttribute("totalResumes", resumes.size());
            System.out.println("resumes.size()>>>>>"+resumes.size());
            model.addAttribute("resumes", resumes);
            return "resume";
        }
    }


    @GetMapping({"/regist", "/edit/{resumeId}"})
    public String resume(HttpSession session,
                         @PathVariable(required = false) Long resumeId,
                         Model model) throws JsonProcessingException {

        // 세션에서 로그인된 사용자 정보 가져오기
        String loginedUser = (String) session.getAttribute("userId");
        System.out.println(loginedUser);

        if (loginedUser == null) {
            return "mypage_login";
        } else {



            if (resumeId != null) {

                ResumeDto userInfo = resumeService.getResumeById(resumeId);
                log.info("userInfo{}", userInfo);


                ObjectMapper objectMapper = new ObjectMapper();
                userInfo.setCreatedBy(loginedUser);
                objectMapper.registerModule(new JavaTimeModule());

                String userInfoJson = objectMapper.writeValueAsString(userInfo);
                log.info("userInfoJson{}" ,userInfoJson);

                model.addAttribute("userInfoJson", userInfoJson);
                model.addAttribute("userInfo", userInfo);

                // 수정 모드
                model.addAttribute("mode", "edit");
            } else {
                // 입력 모드
                ResumeDto userInfo = resumeService.getUserInfo(loginedUser);
                log.info("userInfo{}", userInfo);

                ObjectMapper objectMapper = new ObjectMapper();
                userInfo.setCreatedBy(loginedUser);
                objectMapper.registerModule(new JavaTimeModule());

                String userInfoJson = objectMapper.writeValueAsString(userInfo);
                log.info("userInfoJson{}" ,userInfoJson);

                model.addAttribute("userInfoJson", userInfoJson);
                model.addAttribute("userInfo", userInfo);


                model.addAttribute("mode", "create");
            }

            // 기술 스택 목록을 카테고리별로 가져오기
            Map<String, List<TechnicalStackDto>> technicalStacks = resumeService.getAllTechnicalStacks();
            model.addAttribute("technicalStacks", technicalStacks);

            // 우대사항 코드 목록 가져오기
            List<TreatDto> treatCodes = resumeService.getAllTreatCodes();
            log.info("treatCodes 정보 >> {}", treatCodes);

            model.addAttribute("treatCodes", treatCodes);

            log.info("mode 정보 >> {}",model.getAttribute("mode"));
            log.info("기술 스택 크기: {}", (technicalStacks != null ? technicalStacks.size() : "null"));
            
            return "resume_regist";
        }
    }

    // 기존의 학력,경력,자격증 정보 불러오기

    @GetMapping("/getEducations")
    @ResponseBody
    public List<EducationDto> getEducations(@RequestParam String userId) {
        log.info("Fetching educations for userId: {}", userId);
        List<EducationDto> educations = resumeService.getEducationsByUserId(userId);
        log.info("Fetched educations: {}", educations);
        return educations;
    }
    
    @GetMapping("/getCareers")
    @ResponseBody
    public List<CareerDto> getCareers(@RequestParam String userId) {
        log.info("Fetching careers for userId: {}", userId);
        List<CareerDto> careers = resumeService.getCareersByUserId(userId);
        log.info("Fetched careers: {}", careers);
        return careers;
    }
    
    @GetMapping("/getQualifications")
    @ResponseBody
    public List<QualificationDto> getQualifications(@RequestParam String userId) {
        log.info("Fetching qualifications for userId: {}", userId);
        List<QualificationDto> qualifications = resumeService.getQualificationsByUserId(userId);
        log.info("Fetched qualifications: {}", qualifications);
        return qualifications;
    }
    @ResponseBody
    @GetMapping("/getTreatOptions")
    public ResponseDto<List<TreatDto>> getTreatOptions() {
        List<TreatDto> treatCodes = resumeService.getAllTreatCodes();

        return ok(treatCodes);
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

        log.info("이력서 저장 요청: {}", resumeData);
        log.info("이력서 저장 요청: {}", resumeDto);

        // 이력서 저장
        resumeService.saveResumeToDatabase(resumeDto, profilePhoto, portfolioFiles, introduceMeFiles);
        return ok();
    }

    // 이력서 상세 조회 (GET)
    @GetMapping("/{id}")
    public String resume(@PathVariable("id") Long id, Model model) {
        ResumeDto resume = resumeService.getResumeById(id);
        model.addAttribute("resume", resume);
        log.info("상세조회 이력서정보 " , resume);
        System.out.println(resume);
        return "resume_detail";
    }

    // 이력서 수정 (PUT)
    @ResponseBody
    @PutMapping("/{id}")
    public ResponseDto<Void> updateResume(
            @PathVariable("id") Long id,
            @RequestPart("resumeData") String resumeData,
            @RequestPart(value = "profilePhoto", required = false) MultipartFile profilePhoto,
            @RequestPart(value = "portfolioFiles", required = false) List<MultipartFile> portfolioFiles,
            @RequestPart(value = "introduceMeFiles", required = false) List<MultipartFile> introduceMeFiles,
            HttpSession session
    ) throws IOException {
        String loginedUser = (String) session.getAttribute("userId");
        
        // 권한 체크
        if (loginedUser == null) {

        }
        
        ObjectMapper objectMapper = new ObjectMapper();
        ResumeDto resumeDto = objectMapper.readValue(resumeData, ResumeDto.class);
        resumeDto.setResumeId(id);
        resumeDto.setUpdatedBy(loginedUser); // 수정자 정보 설정
        
        // 해당 이력서의 소유자 체크
        ResumeDto existingResume = resumeService.getResumeById(id);
        if (!existingResume.getId().equals(loginedUser)) {
            
        }
        
        resumeService.updateResumeInDatabase(resumeDto, profilePhoto, portfolioFiles, introduceMeFiles);
        return ok();
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

}
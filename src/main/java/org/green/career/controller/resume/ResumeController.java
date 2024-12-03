package org.green.career.controller.resume;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.controller.AbstractController;
import org.green.career.dto.common.ResponseDto;
import org.green.career.dto.regist.RegistSDto;
import org.green.career.dto.resume.ResumeDto;
import org.green.career.service.resume.ResumeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/resume")
@Controller
@RequiredArgsConstructor
@Slf4j
public class ResumeController extends AbstractController {
    private final ResumeService resumeService;

    @GetMapping("/regist")
    public String resume(HttpSession session, Model model) {
        //세션아이디로 기본정보 가져오기
        String loginedUser = (String)session.getAttribute("userId");
        System.out.println(loginedUser);
        if(loginedUser == null) {
            return "mypage_login";
        }else{
            ResumeDto userInfo = resumeService.getUserInfo(loginedUser);
            model.addAttribute("userInfo", userInfo);
            return "resume_regist";
        }
    }
    @ResponseBody
    @PostMapping("/regist")
    public ResponseDto<String> resume(@RequestBody @Valid ResumeDto resumeDto) {
        
        return ok();
    }
}

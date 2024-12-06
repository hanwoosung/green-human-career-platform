package org.green.career.controller.regist;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.controller.AbstractController;
import org.green.career.dto.common.ResponseDto;
import org.green.career.dto.regist.RegistSDto;
import org.green.career.service.regist.RegistSService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 작성자: 구경림
 * 작성일: 2024-12-03
 * 구직자로그인 컨트롤러
 * 회원가입 및 클라이언트 유효성 검사 구현
 * TODO: 서버측 유효성검사 검토, 기업 회원가입과 통합필요
 */
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/regist")
public class SeekerRegistController extends AbstractController {
    private final RegistSService registSService;

    @GetMapping("/s")
    public String regist(HttpSession httpSession) {
        if (httpSession.getAttribute("userId") != null) {
            return "redirect:/test";
        }
        return "seeker_regist";
    }

    @ResponseBody
    @PostMapping("/s")
    public ResponseDto<String> regist(@RequestBody @Valid RegistSDto registSDto) {
        registSService.saveSeeker(registSDto);
        return ok("회원가입 성공");
    }

    @ResponseBody
    @GetMapping("/s/ckDpId/{id}")
    public ResponseDto<Boolean> checkDuplicateId(@PathVariable String id) {
        boolean isDuplicate = registSService.isDuplicateId(id);
        System.out.println(isDuplicate);
        return ok(isDuplicate);
    }
}

package org.green.career.controller.login;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.controller.AbstractController;
import org.green.career.dto.common.ResponseDto;
import org.green.career.dto.login.UserLoginDto;
import org.green.career.exception.BaseException;
import org.green.career.service.login.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * 작성자: 구경림
 * 작성일: 2024-12-01
 * 로그인 컨트롤러
 * 기초적 로그인 기능만 구현함
 * TODO: 유효성검증
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController extends AbstractController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseDto<String> login(@RequestBody UserLoginDto loginDto, HttpSession session) {
        UserLoginDto user = loginService.login(loginDto.getId(), loginDto.getPw(), loginDto.getUserGbnCd());
        session.setAttribute("userId", user.getId());
        session.setAttribute("userName", user.getName());
        session.setAttribute("userId", user.getId());
        session.setAttribute("userType", user.getUserGbnCd());
        System.out.println(user.getId());
        System.out.println(user.getUserGbnCd());
        return ok("로그인 성공");
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/test";
    }

}

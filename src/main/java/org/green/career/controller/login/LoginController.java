package org.green.career.controller.login;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dto.login.UserLoginDto;
import org.green.career.service.login.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam String id, @RequestParam String pw, HttpSession session) {
        UserLoginDto user = loginService.login(id, pw);
        session.setAttribute("userName", user.getName());
        session.setAttribute("userType", user.getUserGbnCd());
        System.out.println("userType: " + session.getAttribute("userType"));
        System.out.println("userType: " + session.getAttribute("userName"));
        return "redirect:/test";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/test";
    }

}

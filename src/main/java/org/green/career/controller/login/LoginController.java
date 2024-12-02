package org.green.career.controller.login;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.service.login.LoginService;
import org.springframework.stereotype.Controller;
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


    @GetMapping("/signUp")
    public String signUp(Model model) {
        model.addAttribute("company", new CompanyDto());
        return "/login/registCompany";
    }

    @PostMapping("/registCompany")
    public String registCompany(@ModelAttribute("company") CompanyDto company) {
        System.out.println("company : " + company);
        loginService.registCompany(company);
        return "redirect:/";
    }
}


package org.green.career.controller.login;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dto.ResponseDto;
import org.green.career.dto.login.CompanyDto;
import org.green.career.service.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static org.green.career.dto.ResponseDto.ok;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseDto<String> login(@RequestBody CompanyDto loginDto, HttpSession session) {
        CompanyDto user = loginService.login(loginDto.getId(), loginDto.getPw(), loginDto.getUser_gbn_cd());
        session.setAttribute("userId", user.getId());
        session.setAttribute("userName", user.getName());
        session.setAttribute("userType", user.getUser_gbn_cd());
        System.out.println(user.getId());
        System.out.println(user.getUser_gbn_cd());
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


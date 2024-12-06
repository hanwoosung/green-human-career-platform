package org.green.career.controller.login;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.controller.AbstractController;
import org.green.career.dao.login.CompanyDao;
import org.green.career.dto.common.ResponseDto;
import org.green.career.dto.login.UserLoginDto;
import org.green.career.service.login.LoginService;
import org.green.career.service.login.regist.RegistCompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 작성자: 구경림
 * 작성일: 2024-12-01
 * 로그인 컨트롤러
 * 기초적 로그인 기능만 구현함
 * TODO: 로그인상태일때 /login접근시 사용자에게 알리기(ex) 모달-로그아웃,로그인 유지 선택,
 * TODO: referer 서버에서 구현해보기
 */
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/login")
public class LoginController extends AbstractController {
    private final LoginService loginService;
    private final RegistCompanyService registCompanyService;
    private final CompanyDao companyDao;

    @GetMapping
    public String login(HttpSession session) {
        if (session.getAttribute("userId") != null) {
            return "redirect:/test";
        }
        return "login";
    }

    @ResponseBody
    @PostMapping
    public ResponseDto<String> login(@RequestBody UserLoginDto loginDto, HttpSession session) {
        UserLoginDto user = loginService.login(loginDto.getId(), loginDto.getPw(), loginDto.getUserGbnCd());
        session.setAttribute("userId", user.getId());
        session.setAttribute("userName", user.getName());
        session.setAttribute("userId", user.getId());
        session.setAttribute("userType", user.getUserGbnCd());

        session.setMaxInactiveInterval(30 * 60);

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
        model.addAttribute("company", new UserLoginDto());
        return "/login/registCompany";
    }

    @PostMapping("/registCompany")
    public String registCompany(@ModelAttribute("company") UserLoginDto company) {
        System.out.println("company : " + company);
        registCompanyService.registCompany(company);
        return "redirect:/";
    }

    @GetMapping("/checkId")
    @ResponseBody
    public Map<String, Object> checkId(@RequestParam("id") String id) {
        int result = companyDao.checkId(id);
        Map<String, Object> map = new HashMap<>();
        System.out.println(result+" a1");
        if(result == 0) {
            map.put("code", "200");
            map.put("message", "사용가능한 아이디 입니다.");
            return map;
        } else {
            map.put("code", "400");
            map.put("message", "중복된 아이디 입니다.");
            return map;
        }
    }

}


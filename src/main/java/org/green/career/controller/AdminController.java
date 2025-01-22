package org.green.career.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dto.common.ResponseDto;
import org.green.career.dto.login.UserLoginDto;
import org.green.career.service.admin.AdminService;
import org.green.career.service.login.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 작성자: 구경림
 * 작성일: 2024-12-06
 * 로그인 컨트롤러
 * 기초적 로그인 기능만 구현함
 * TODO: 로그인상태일때 /login접근시 사용자에게 알리기(ex) 모달-로그아웃,로그인 유지 선택,
 * TODO: referer 서버에서 구현해보기
 */
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class AdminController extends AbstractController {

    final private AdminService adminService;

    @GetMapping
    public String adminMain(Model model,
                            @RequestParam(value = "page", defaultValue = "1") int page,
                            @RequestParam(value = "toggle", defaultValue = "") String toggle,
                            @RequestParam(value = "search", defaultValue = "") String search) throws Exception {

        sessionGoPage("/admin/login");

        String userType = sessionUserInfo("userType");

        ifGourl(!userType.equals("M"), "/admin/login");

        Map<String, Object> result = adminService.getUserList(page, search, toggle);

        model.addAttribute("paging", result.get("paging"));
        model.addAttribute("list", result.get("list"));
        model.addAttribute("search", search);
        model.addAttribute("toggle", toggle);

        return "admin/main";
    }

    @PostMapping("/status")
    @ResponseBody
    public ResponseDto<Void> login(@RequestBody Map<String, Object> params) throws Exception {

        sessionApiError();

        List<String> id = (List<String>) params.get("id");
        String status = (String) params.get("status");
        adminService.updateUser(id, status);

        return ResponseDto.ok();
    }

    @GetMapping("/login")
    public String login(HttpSession session) {

        if (session.getAttribute("userId") != null) {
            return "redirect:/admin";
        }

        return "adminLogin";
    }

}

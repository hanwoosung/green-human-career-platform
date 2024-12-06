package org.green.career.controller.company;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.controller.AbstractController;
import org.green.career.dto.common.file.request.TblFileRequestDto;
import org.green.career.dto.company.mypage.CompanyModiDto;
import org.green.career.dto.company.mypage.CompanyUserDto;
import org.green.career.service.company.mypage.CompanyMypageService;
import org.green.career.utils.CommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
@Slf4j
public class CompanyMypageController extends AbstractController {

    private final CompanyMypageService companyMypageService;
    private final CommonUtils utils;

    @GetMapping("/check/{user_id}")
    public String check(@PathVariable String user_id, Model model) {
        model.addAttribute("user_id", user_id);
        return "/company/check";
    }

    @PostMapping("/myInfo/{user_id}")
    public String mypage(@PathVariable("user_id") String user_id, CompanyUserDto user, Model model) {//qwer1 - qwer1234!

        System.out.println("!@#$!@#$" + user);

        CompanyModiDto dto = companyMypageService.getCompanyModi(user);
        System.out.println("%^&*%^&*" + dto);
        if (dto == null) {
            return "redirect:/company/check/" + user_id;
        } else {
            model.addAttribute("user", dto);
            if(sessionUserInfo("userType").equals("C")) {
                return "/company/mypage";
            } else if(sessionUserInfo("userType").equals("S")){
                return "/normal/mypage";
            }
            return "/company/mypage";
        }
    }

    @PostMapping("/modifyMyinfo")
    public String modifyCompany(CompanyModiDto user, Model model) throws Exception {
        TblFileRequestDto fileList = new TblFileRequestDto();
        System.out.println("Update" + user);
        if (user.getFileUrl() == null && user.getFileName() == null) {
            fileList = utils.saveCompanyImage(user.getFile(), user.getId());
            companyMypageService.insertMypageProfile(fileList);
        }
        companyMypageService.updateMypageInfo(user);

        return "redirect:/";
    }
}

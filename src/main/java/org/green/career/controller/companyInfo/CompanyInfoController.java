package org.green.career.controller.companyInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.controller.AbstractController;
import org.green.career.dto.common.file.request.FileDto;
import org.green.career.dto.company.CompanyRegistDto;
import org.green.career.dto.company.response.*;
import org.green.career.service.companyInfo.CompanyInfoRServiceImpl;
import org.green.career.service.main.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/company")
@RequiredArgsConstructor
@Slf4j
public class CompanyInfoController extends AbstractController {

    private final CompanyInfoRServiceImpl companyInfoRservice;
    private final MainService mainService;

    @GetMapping("/companyRegist/{user_id}")
    public String companyIR(@PathVariable("user_id") String user_id, Model model) {

        model.addAttribute("company", new CompanyRegistDto());
        model.addAttribute("uid", user_id);
        return "/companyInfo/regist";
    }

    @PostMapping("/companyIR")
    public String companyIR(CompanyRegistDto company) throws Exception {
        System.out.println(company + "1234");
        companyInfoRservice.insertCompanyInfo(company);
        companyInfoRservice.insertCompanyFile(company);
        companyInfoRservice.insertCompanyHistory(company);
        companyInfoRservice.insertCompanySales(company);
        return "redirect:/";
    }

    @GetMapping("/companyInfo/{user_id}")
    public String companyInfo(@PathVariable("user_id") String user_id, Model model) {
        CompanyInfoResponseDto idto = companyInfoRservice.getCompanyInfo(user_id);
        List<CompanySalesResponseDto> slist = companyInfoRservice.getCompanySales(user_id);
        List<CompanyHistoryResponseDto> hlist = companyInfoRservice.getCompanyHistory(user_id);
        FileDto fdto = companyInfoRservice.getCompanyFileP(user_id);
        List<FileDto> flist = companyInfoRservice.getCompanyFileS(user_id);
        FileDto pdto = companyInfoRservice.getCompanyFilePr(user_id);
        CompanyUserResponseDto udto = companyInfoRservice.getCompanyUser(user_id);
        model.addAttribute("company", idto);
        model.addAttribute("sales", slist);
        model.addAttribute("histories", hlist);
        model.addAttribute("file", fdto);
        model.addAttribute("files", flist);
        model.addAttribute("profile", pdto);
        model.addAttribute("user", udto);
        return "/companyInfo/info";
    }
}

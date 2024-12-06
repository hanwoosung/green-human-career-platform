package org.green.career.controller.companyInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.controller.AbstractController;
import org.green.career.dto.common.file.request.FileDto;
import org.green.career.dto.company.CompanyRegistDto;
import org.green.career.dto.company.response.CompanyHistoryResponseDto;
import org.green.career.dto.company.response.CompanyInfoResponseDto;
import org.green.career.dto.company.response.CompanySalesResponseDto;
import org.green.career.dto.company.response.CompanyUserResponseDto;
import org.green.career.dto.jobopen.JobSearchResult;
import org.green.career.service.companyInfo.CompanyInfoRService;
import org.green.career.service.main.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
//@RequestMapping("/company")
@RequiredArgsConstructor
@Slf4j
public class CompanyInfoController extends AbstractController {

    private final CompanyInfoRService companyInfoRservice;
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
    public String companyInfo(@PathVariable("user_id") String user_id,
                              @RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        CompanyInfoResponseDto idto = companyInfoRservice.getCompanyInfo(user_id);
        List<CompanySalesResponseDto> slist = companyInfoRservice.getCompanySales(user_id);
        List<CompanyHistoryResponseDto> hlist = companyInfoRservice.getCompanyHistory(user_id);
        FileDto fdto = companyInfoRservice.getCompanyFileP(user_id);

        List<FileDto> flist = companyInfoRservice.getCompanyFileS(user_id);
        FileDto pdto = companyInfoRservice.getCompanyFilePr(user_id);
        CompanyUserResponseDto udto = companyInfoRservice.getCompanyUser(user_id);

        JobSearchResult result = mainService.getCompanyOpeningsInfo(page, user_id);
        log.info("main" + result);

        model.addAttribute("jobList", result.getJobList());
        model.addAttribute("paging", result.getPaging());

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
package org.green.career.controller.companyInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.controller.AbstractController;
import org.green.career.dto.common.file.request.FileDto;
import org.green.career.dto.company.CompanyRegistDto;
import org.green.career.dto.company.mypage.CompanyModiDto;
import org.green.career.dto.company.response.*;
import org.green.career.dto.jobopen.JobSearchResult;
import org.green.career.service.companyInfo.CompanyInfoRService;
import org.green.career.service.main.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        return "redirect:/companyInfo/" + company.getId();
    }

    @GetMapping("/companyInfo/{user_id}")
    public String companyInfo(@PathVariable("user_id") String user_id,
                              @RequestParam(value = "page", defaultValue = "1") int page,Model model) {
        CompanyInfoResponseDto idto = companyInfoRservice.getCompanyInfo(user_id);

        List<CompanySalesResponseDto> slist = companyInfoRservice.getCompanySales(user_id);

        List<CompanyHistoryResponseDto> hlist = companyInfoRservice.getCompanyHistory(user_id);

        FileDto fdto = companyInfoRservice.getCompanyFileP(user_id);

        List<FileDto> flist = companyInfoRservice.getCompanyFileS(user_id);
        int size = flist.size();
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
        model.addAttribute("size", size);
        return "/companyInfo/info";
    }

    @GetMapping("/companyModi/{user_id}")
    public String companyModi(@PathVariable("user_id") String user_id,
                              Model model) {
        CompanyInfoResponseDto idto = companyInfoRservice.getCompanyInfo(user_id);

        List<CompanySalesResponseDto> slist = companyInfoRservice.getCompanySales(user_id);
        List<CompanySalesResponseDto> slist1 = companyInfoRservice.getSales(user_id);

        List<CompanyHistoryResponseDto> hlist = companyInfoRservice.getCompanyHistory(user_id);
        List<CompanyHistoryResponseDto> hlist1 = companyInfoRservice.getHistory(user_id);

        FileDto fdto = companyInfoRservice.getCompanyFileP(user_id);

        List<FileDto> flist = companyInfoRservice.getCompanyFileS(user_id);
        System.out.println("flist" + flist);
        int size = flist.size();
        FileDto pdto = companyInfoRservice.getCompanyFilePr(user_id);

        CompanyUserResponseDto udto = companyInfoRservice.getCompanyUser(user_id);

        CompanyRegistDto rdto = new CompanyRegistDto();
            rdto.setId(idto.getId());
            rdto.setCno(idto.getCno());
            rdto.setCGbnCd(idto.getCGbnCd());
            rdto.setHomepage(idto.getHomepage());
            rdto.setCDetail(idto.getCDetail());
            rdto.setCCnt(idto.getCCnt());
            rdto.setCBusiness(idto.getCBusiness());
            rdto.setFileName(fdto.getFileName());
            rdto.setFileUrl(fdto.getFileUrl());
            List<String> fn = new ArrayList<>();
            List<String> furl = new ArrayList<>();
            for(int i = 0; i < flist.size(); i++) {
                fn.add(flist.get(i).getFileName());
                furl.add(flist.get(i).getFileUrl());
            }
            rdto.setFileNames(fn);
            rdto.setFileUrls(furl);
            List<String> hy = new ArrayList<>();
            List<String> ht = new ArrayList<>();
            List<String> hy1 = new ArrayList<>();
            List<String> ht1 = new ArrayList<>();
            for(int i = 0; i < hlist.size(); i++) {
                hy.add(hlist.get(i).getCHistory());
                hy1.add(hlist1.get(i).getCHistory());
                ht.add(hlist.get(i).getCContent());
                ht1.add(hlist1.get(i).getCContent());
            }
            rdto.setHistoryYear(hy);
            rdto.setHistoryTxt(ht);
            List<String> sy = new ArrayList<>();
            List<String> st = new ArrayList<>();
            List<String> sy1 = new ArrayList<>();
            List<String> st1 = new ArrayList<>();
            for(int i = 0; i < slist.size(); i++) {
                sy.add(slist.get(i).getCSaleDt());
                sy1.add(slist1.get(i).getCSaleDt());
                st.add(slist.get(i).getCPay());
                st1.add(slist1.get(i).getCPay());
            }
            rdto.setSalesYear(sy);
            rdto.setSalesTxt(st);
            List<String> fun = new ArrayList<>();
            fun.add("removeImageFirts()");
            fun.add("removeImage()");
            fun.add("removeImage()");
            model.addAttribute("company", rdto);
            model.addAttribute("fun", fun);
            model.addAttribute("sales", sy1);
            model.addAttribute("historys", hy1);
            model.addAttribute("size", size);
//            model.addAttribute("uid", user_id);
        return "/companyInfo/modify";
    }

    @PostMapping("/companyMD")
    public String companyMD(CompanyRegistDto company,
                        @RequestParam int size) throws Exception {
        System.out.println(company + "1234");

        companyInfoRservice.checkCompanyInfo(company, size);

        return "redirect:/companyInfo/" + company.getId();
    }

}

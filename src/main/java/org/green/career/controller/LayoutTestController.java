package org.green.career.controller;

import org.green.career.dto.jobopen.JobSearchResult;
import org.green.career.service.main.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/test")
public class LayoutTestController {

    private final MainService mainService;

    public LayoutTestController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping
    public String userMain(@RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "search", required = false) String searchText,
                           @RequestParam(value = "skills", required = false) List<String> skills,
                           Model model) {

        JobSearchResult result = mainService.getJobOpeningsWithPaging(searchText, skills, page);

        model.addAttribute("jobList", result.getJobList());
        model.addAttribute("paging", result.getPaging());
        model.addAttribute("skillList", mainService.findSkillList());
        model.addAttribute("searchText", searchText);
        model.addAttribute("skills", skills);

        return "layout-test/main_sample";
    }
    @GetMapping("/main-empty")
    public String mainEmpty() {
        return "layout-test/main_sample_empty";
    }
    @GetMapping("/mypage-empty")
    public String mypageEmpty() {
        return "layout-test/mypage_sample_empty";
    }
}

package org.green.career.controller;

import org.green.career.service.main.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 작성자: 구경림
 * 작성일: 2024-12-01
 * 레이아웃 테스트용으로 작성된 컨트롤러 - 팀원간 협의 이후 삭제하거나 적용 예정
 */
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

    /*    JobSearchResult result = mainService.getJobOpeningsWithPaging(searchText, skills, page,);
        Map<String, Object> skillData = mainService.findSkillList();

        model.addAttribute("skillList", skillData.get("skills"));
        model.addAttribute("categories", skillData.get("categories"));

        model.addAttribute("jobList", result.getJobList());
        model.addAttribute("paging", result.getPaging());*/
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

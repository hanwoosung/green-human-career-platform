package org.green.career.controller.jobseeker.mypage;

import lombok.RequiredArgsConstructor;
import org.green.career.controller.AbstractController;
import org.green.career.dto.jobopen.JobSearchResult;
import org.green.career.service.jobseeker.mypage.stack.StackService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/job-seeker/my-page/stack")
@RequiredArgsConstructor
public class StackController extends AbstractController {

    final private StackService stackService;

    @GetMapping
    public String stack(@RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "search", required = false) String searchText,
                        @RequestParam(value = "skills", required = false) List<String> skills,
                        Model model) throws Exception {

        sessionGoLogin();

        String id = sessionUserInfo("userId");

        JobSearchResult result = stackService.getJobOpeningsWithPaging(searchText, skills, page, id);
        Map<String, Object> skillData = stackService.findSkillList();

        model.addAttribute("skillList", skillData.get("skills"));
        model.addAttribute("categories", skillData.get("categories"));

        model.addAttribute("jobList", result.getJobList());
        model.addAttribute("paging", result.getPaging());
        model.addAttribute("searchText", searchText);
        model.addAttribute("skills", skills);

        return "/jobseeker/mypage/stack";
    }

}

package org.green.career.controller.main;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.controller.AbstractController;
import org.green.career.dto.jobopen.JobSearchResult;
import org.green.career.service.main.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 작성자: 한우성
 * 작성일: 2024-11-30
 * USER_MAIN 컨트롤러
 * 현재는 페이징+ 검색기능 까지 있음
 * TODO: 1.(북마크 ,스크랩 기능 추가필요) 2. (추후 인기순위 ? 등등 추가 될 수있음)  3. 메인쪽 폰트나 텍스트 사이즈 디테일 잡아야함 채용공고 완료 후 진행
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController extends AbstractController {

    private final MainService mainService;

    @GetMapping("/")
    public String userMain(@RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "search", required = false) String searchText,
                           @RequestParam(value = "skills", required = false) List<String> skills,
                           Model model) {

        log.info("userMian" + skills);


        JobSearchResult result = mainService.getJobOpeningsWithPaging(searchText, skills, page, sessionUserInfo("userId"));
        Map<String, Object> skillData = mainService.findSkillList();

        log.info("main" + result);

        model.addAttribute("skillList", skillData.get("skills"));
        model.addAttribute("categories", skillData.get("categories"));

        model.addAttribute("jobList", result.getJobList());
        model.addAttribute("paging", result.getPaging());
        model.addAttribute("searchText", searchText);
        model.addAttribute("skills", skills);

        return "user_main";
    }

    @GetMapping("/company/1")
    public String companyMain(@RequestParam(value = "page", defaultValue = "1") int page, Model model) throws Exception {
//        sessionGoLogin();
//        String id = sessionUserInfo("userId");
//        String id = sessionUserInfo("userType");
        log.info("userMian" + page);

//        JobSearchResult result = mainService.getCompanyOpeningsWithPaging(page, sessionUserInfo("id"));
        JobSearchResult result = mainService.getCompanyOpeningsWithPaging(page, "user2");
        log.info("main" + result);

        model.addAttribute("jobList", result.getJobList());
        model.addAttribute("paging", result.getPaging());

        return "company_main";
    }
}

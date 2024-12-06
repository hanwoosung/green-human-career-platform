package org.green.career.controller.company.jbskMngm;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.controller.AbstractController;
import org.green.career.dto.common.ResponseDto;
import org.green.career.service.company.jbskMngm.jobstackoffer.JobStackOfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/company/jbsk-mngm/job-stack-offer")
@RequiredArgsConstructor
@Slf4j
public class JobStackOfferController extends AbstractController {

    final private JobStackOfferService jobStackOfferService;
    final private HttpSession session;

    @GetMapping
    public String jobStackOffer(@RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "search", defaultValue = "") String search,
                                @RequestParam(value = "stacks", required = false) List<String> stacks,
                                Model model) {

        Map<String, Object> jobStackOfferList = jobStackOfferService.getJobStackOfferList(page, search, stacks);

        if (stacks == null) stacks = new ArrayList<>();

        model.addAttribute("stackList", jobStackOfferList.get("stackList")); // 전체 스택
        model.addAttribute("list", jobStackOfferList.get("list")); // 공고 리스트
        model.addAttribute("paging", jobStackOfferList.get("paging")); // 페이징

        model.addAttribute("stacks", stacks); // 검색 스택
        model.addAttribute("search", search); // 검색 내용


        return "company/jbskMngm/job_stack_offer";
    }

    @PostMapping
    @ResponseBody
    public ResponseDto<Void> sendOffer(@RequestBody Map<String, Object> params) {

        String id = (String) session.getAttribute("userId");
        String uId = (String) params.get("uid");

        jobStackOfferService.saveOffer(id, uId);

        return ResponseDto.ok();
    }

}

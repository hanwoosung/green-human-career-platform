package org.green.career.controller.company.jbskMngm;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.green.career.dto.common.CodeInfoDto;
import org.green.career.service.company.jbskMngm.jobstackoffer.JobStackOfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/company/jbsk-mngm/job-stack-offer")
@RequiredArgsConstructor
@Slf4j
public class JobStackOfferController {

    final private JobStackOfferService jobStackOfferService;

    @GetMapping
    public String jobStackOffer(@RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "search", defaultValue = "") String search,
                                Model model) {

        List<CodeInfoDto> stacks = jobStackOfferService.getJobStackOfferList(page, search);


        model.addAttribute("stacks", stacks);


        return "company/jbskMngm/job_stack_offer";
    }

}

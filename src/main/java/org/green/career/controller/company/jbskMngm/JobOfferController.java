package org.green.career.controller.company.jbskMngm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.service.company.jbskMngm.joboffer.JobOfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/company/jbsk-mngm/job-offer")
@RequiredArgsConstructor
@Slf4j
public class JobOfferController {

    final private JobOfferService jobOfferService;

    @GetMapping
    public String jobOffer(Model model,
                           @RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "search", defaultValue = "") String search) {

        Map<String, Object> result = jobOfferService.getJobOfferList(page, search);

        model.addAttribute("paging", result.get("paging"));
        model.addAttribute("list", result.get("list"));
        model.addAttribute("search", search);

        return "company/jbskMngm/job_offer";
    }

}

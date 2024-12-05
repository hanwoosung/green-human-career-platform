package org.green.career.controller.jobseeker.mypage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.controller.AbstractController;
import org.green.career.service.jobseeker.mypage.offer.SeekerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/job-seeker/my-page/job-offer")
@RequiredArgsConstructor
@Slf4j
public class JobOfferSeekerController extends AbstractController {

    private final SeekerService seekerService;


    @GetMapping
    public String jobOffer(Model model,
                           @RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "search", defaultValue = "") String search) throws Exception {

        sessionGoLogin();

        Map<String, Object> result = seekerService.getJobOfferList(page, search, isSessionCheck());
        System.out.println(result.get("list").toString());
        model.addAttribute("paging", result.get("paging"));
        model.addAttribute("list", result.get("list"));
        model.addAttribute("search", search);

        return "jobseeker/mypage/job_offer_seeker";
    }

}

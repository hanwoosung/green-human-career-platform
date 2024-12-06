package org.green.career.controller.jobseeker.mypage;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.controller.AbstractController;
import org.green.career.dao.jobseeker.mypage.SendResultDao;
import org.green.career.dto.common.RatingRequestDto;
import org.green.career.dto.common.ResponseDto;
import org.green.career.service.jobseeker.mypage.result.SendResultService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/job-seeker/my-page")
@RequiredArgsConstructor
@Slf4j
public class SendResultController extends AbstractController {


    private final SendResultService sendResultService;
    private final SendResultDao sendResultDao;

    @GetMapping("/send-result")
    public String bookmark(Model model, @RequestParam(value = "page", defaultValue = "1") int page) throws Exception {

        sessionGoLogin();

        Map<String, Object> result = sendResultService.selectSendResult(page, isSessionCheck());
        System.out.println(result + "asdafaf");
        model.addAttribute("paging", result.get("paging"));
        model.addAttribute("list", result.get("list"));

        return "/jobseeker/mypage/send_result";
    }

    @PostMapping("/rating")
    @ResponseBody
    public ResponseDto<String> submitRating(@RequestBody RatingRequestDto ratingRequest) {
        sessionApiError();
        isSessionCheck();
        log.info(ratingRequest.toString() + "aa");
        ratingRequest.setId(isSessionCheck());
        sendResultDao.giveRating(ratingRequest);
        return ok("성공");
    }
}
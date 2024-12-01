package org.green.career.controller.jobseeker.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 12-01 (김상준)
 * 설명: 이 클래스는 마이페이지(북마크) 기능을 수행합니다.
 */

@Controller
@RequestMapping("/job-seeker/my-page/bookmark")
public class Bookmark {

    @GetMapping("/{id}")
    public String bookmark(@PathVariable String id, Model model) {
        return "/jobseeker/mypage/bookmark";
    }

}

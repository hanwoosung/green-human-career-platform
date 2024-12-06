package org.green.career.controller.jobseeker.mypage;

import lombok.RequiredArgsConstructor;
import org.green.career.service.jobseeker.mypage.bookmark.BookmarkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 작성자: 김상준
 * 작성일: 2024-12-02
 * 구직자 마이페이지 북마크의 컨트롤러
 */

@Controller
@RequestMapping("/job-seeker/my-page/bookmark")
@RequiredArgsConstructor
public class BookmarkController {

    final private BookmarkService bookmarkService;

    @GetMapping
    public String bookmark(Model model,
                           @RequestParam(value = "page", defaultValue = "1") int page) {

        Map<String, Object> result = bookmarkService.selectAllBookmarks(page);

        model.addAttribute("paging", result.get("paging"));
        model.addAttribute("list", result.get("list"));

        return "/jobseeker/mypage/bookmark";
    }

}

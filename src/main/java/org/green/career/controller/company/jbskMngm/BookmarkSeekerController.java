package org.green.career.controller.company.jbskMngm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.controller.AbstractController;
import org.green.career.service.company.jbskMngm.bookmarkSeeker.BookmarkSeekerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/company/jbsk-mngm/bookmark-seeker")
@RequiredArgsConstructor
@Slf4j
public class BookmarkSeekerController extends AbstractController {

    final private BookmarkSeekerService bookmarkSeekerService;

    @GetMapping
    public String jobOffer(Model model,
                           @RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "search", defaultValue = "") String search) throws Exception {

        sessionGoLogin();

        String id = sessionUserInfo("userId");

        Map<String, Object> result = bookmarkSeekerService.getBookmarkSeekerList(page, search, id);

        model.addAttribute("paging", result.get("paging"));
        model.addAttribute("list", result.get("list"));

        System.out.println(result.get("list"));

        model.addAttribute("search", search);

        return "company/jbskMngm/bookmark_seeker";
    }

}

package org.green.career.controller.regist;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.controller.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SeekerRegistController extends AbstractController {
    @GetMapping("/regist/seeker")
    public String regist() {
        return "seeker_regist";
    }
}

package org.green.career.controller;

import org.green.career.dto.ResponseDto;
import org.green.career.dto.TestDto;
import org.green.career.exception.BaseException;
import org.green.career.type.ResultType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController extends AbstractController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/test")
    public String indext() {
        return "test";
    }

    @PostMapping("/")
    public @ResponseBody ResponseDto<Void> get() {
        throw new BaseException(ResultType.ERROR, "에러");
    }

    @PostMapping("/test")
    public @ResponseBody ResponseDto<TestDto> get2() {
        TestDto a = new TestDto();
        a.setName("나다용");
        a.setAge("1");
        return ok(a);
    }
}

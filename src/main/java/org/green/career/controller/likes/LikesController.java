package org.green.career.controller.likes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.controller.AbstractController;
import org.green.career.dto.common.ResponseDto;
import org.green.career.dto.likes.LikesDto;
import org.green.career.service.likes.LikesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
@Slf4j
public class LikesController extends AbstractController {

    private final LikesService likesService;

    @PostMapping
    public ResponseDto<Void> likes(@RequestBody LikesDto inputs) {

        likesService.setLikes(inputs.getCjNo(),
                inputs.getLGbnCd(),
                inputs.isFlag());

        return ResponseDto.ok();
    }

}

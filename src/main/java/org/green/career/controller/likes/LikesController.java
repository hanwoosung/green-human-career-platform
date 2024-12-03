package org.green.career.controller.likes;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.green.career.controller.AbstractController;
import org.green.career.dto.common.ResponseDto;
import org.green.career.dto.likes.LikesDto;
import org.green.career.service.likes.LikesService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikesController extends AbstractController {

    private final LikesService likesService;

    @PostMapping
    public ResponseDto<Void> likes(@RequestBody LikesDto inputs){

        likesService.setLikes(inputs.getCjNo(),
                              inputs.getLGbnCd(),
                              inputs.isFlag());

        return ResponseDto.ok();
    }

}

package org.green.career.controller.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.controller.AbstractController;
import org.green.career.dto.board.BoardDto;
import org.green.career.dto.board.CommentDto;
import org.green.career.dto.common.ResponseDto;
import org.green.career.service.board.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController extends AbstractController {

    final private BoardService boardService;

    // 리스트 뿌리는 함수
    @GetMapping
    public String board(@RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "search", defaultValue = "") String search,
                        @RequestParam(value = "filter", defaultValue = "R") String filter,
                        Model model) throws Exception {


        String userGbnCd = sessionUserInfo("userType") == null ? "S" : sessionUserInfo("userType");
        String id = sessionUserInfo("userId") == null ? "" : sessionUserInfo("userId");

        Map<String, Object> result = boardService.selectBoardList(page, search, filter, userGbnCd, id);

        model.addAttribute("list", result.get("list"));

        return "board/list";
    }

    //    게시판 상세
    @GetMapping("/{bNo}")
    public String boardDetail(@PathVariable("bNo") Long bNo,
                              Model model) {

        BoardDto result = boardService.selectBoard(bNo);

        model.addAttribute("board", result);

        return "board/detail";
    }

    //    리스트 가져오기 페이징 추가로 가져오는거
    @GetMapping("/list")
    @ResponseBody
    public ResponseDto<Object> boardList(@RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "search", defaultValue = "") String search,
                                         @RequestParam(value = "filter", defaultValue = "R") String filter) {


        String userGbnCd = sessionUserInfo("userType") == null ? "S" : sessionUserInfo("userType");
        String id = sessionUserInfo("userId") == null ? "" : sessionUserInfo("userId");


        Map<String, Object> result = boardService.selectBoardList(page, search, filter, userGbnCd, id);

        return ResponseDto.ok(result);
    }

    //    게시판저장
    @PostMapping
    @ResponseBody
    public ResponseDto<Integer> board(@RequestBody BoardDto boardDto) throws Exception {

        sessionApiError();

        String userGbnCd = sessionUserInfo("userType") == null ? "S" : sessionUserInfo("userType");
        String id = sessionUserInfo("userId") == null ? "" : sessionUserInfo("userId");

        if (userGbnCd.equals("M")) {
            boardDto.setBGbnCd("N");
        } else {
            boardDto.setBGbnCd("F");
        }

        boardDto.setInstId(id);
        boardDto.setUpdtId(id);

        int result = boardService.saveBoard(boardDto);

        return ResponseDto.ok(result);
    }

    //    게시글 수정
    @PutMapping
    @ResponseBody
    public ResponseDto<Integer> boardEdit(@RequestBody BoardDto boardDto) throws Exception {

        sessionApiError();

        String userGbnCd = sessionUserInfo("userType") == null ? "S" : sessionUserInfo("userType");
        String id = sessionUserInfo("userId") == null ? "" : sessionUserInfo("userId");

        System.out.println(userGbnCd);

        if (userGbnCd.equals("M")) {
            boardDto.setBGbnCd("N");
        } else {
            boardDto.setBGbnCd("F");
        }

        boardDto.setInstId(id);
        boardDto.setUpdtId(id);

        int result = boardService.updateBoard(boardDto);

        return ResponseDto.ok(result);
    }

    //    게시글 삭제
    @DeleteMapping("/{bno}")
    @ResponseBody
    public ResponseDto<Void> delBoard(@PathVariable("bno") Long bno) throws Exception {

        sessionApiError();

        int result = boardService.deleteBoard(bno);

        return ResponseDto.ok();
    }

    //    댓글삭제
    @DeleteMapping("/comment/{cmno}")
    @ResponseBody
    public ResponseDto<Void> delComment(@PathVariable("cmno") Long cmno) throws Exception {

        sessionApiError();

        int result = boardService.deleteComment(cmno);

        return ResponseDto.ok();
    }

    //    댓글저장
    @PostMapping("/comment")
    @ResponseBody
    public ResponseDto<Void> saveComment(@RequestBody CommentDto commentDto) throws Exception {

        sessionApiError();

        String id = sessionUserInfo("userId") == null ? "" : sessionUserInfo("userId");

        commentDto.setInstId(id);
        commentDto.setUpdtId(id);

        int result = boardService.saveComment(commentDto);

        return ResponseDto.ok();
    }

    //    게시판 작성 페이지 이동
    @GetMapping("/edit")
    public String insert() throws Exception {

        sessionGoLogin();

        return "board/edit";
    }

    //    게시글 수정 페이지로 이동
    @GetMapping("/edit/{bno}")
    public String edit(@PathVariable("bno") Long bno,
                       Model model) throws Exception {

        sessionGoLogin();

        BoardDto boardDto = boardService.selectBoard(bno);

        model.addAttribute("board", boardDto);

        return "board/edit";
    }

    //    게시판 에디터 파일 업로드시 파일업로드 처리
    @PostMapping("/file")
    @ResponseBody
    public ResponseDto<String> file(@RequestParam("file")
                                    MultipartFile file,
                                    HttpServletRequest request) throws IOException {

        String protocol = request.getScheme(); // "http" 또는 "https"
        String host = request.getServerName(); // 예: "localhost" 또는 "192.168.1.2"
        int port = request.getServerPort(); // 포트 번호 가져오기

        String url = protocol + "://" + host + ":" + port + "/";

        String result = boardService.saveEditorFile(file, url);

        return ResponseDto.ok(result);

    }

}

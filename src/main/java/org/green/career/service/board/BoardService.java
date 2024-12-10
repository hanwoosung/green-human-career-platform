package org.green.career.service.board;

import org.green.career.dto.board.BoardDto;
import org.green.career.dto.board.CommentDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface BoardService {

    String saveEditorFile(MultipartFile file, String url) throws IOException;

    int saveBoard(BoardDto board);

    int updateBoard(BoardDto board);

    Map<String, Object> selectBoardList(int page, String search, String filter, String userGbnCd, String id);

    BoardDto selectBoard(Long bNo);

    List<CommentDto> selectComment(Long bNo);

    int deleteComment(Long cmNo);

    int saveComment(CommentDto comment);

    int deleteBoard(Long bNo);

}

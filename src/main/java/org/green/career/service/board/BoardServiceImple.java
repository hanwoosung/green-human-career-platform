package org.green.career.service.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dao.board.BoardDao;
import org.green.career.dto.board.BoardDto;
import org.green.career.dto.board.CommentDto;
import org.green.career.exception.BaseException;
import org.green.career.service.AbstractService;
import org.green.career.type.ResultType;
import org.green.career.utils.Editor;
import org.green.career.utils.PagingBtn;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImple extends AbstractService implements BoardService {

    final private Editor editor;
    final private BoardDao boardDao;

    @Override
    public String saveEditorFile(MultipartFile file, String url) throws IOException {
        return editor.saveFile(file, url);
    }

    @Override
    public int saveBoard(BoardDto board) {
        System.out.println(board);
        return returnData(() -> {
            int result = 0;

            try {
                boardDao.saveBoard(board);
            } catch (BaseException e) {
                throw new BaseException(ResultType.ERROR, "저장 실패!", e);
            }

            return result;
        });
    }

    @Override
    public int updateBoard(BoardDto board) {

        System.out.printf(board.toString());

        return returnData(() -> {
            int result = 0;

            try {
                boardDao.updateBoard(board);
            } catch (BaseException e) {
                throw new BaseException(ResultType.ERROR, "저장 실패!", e);
            }

            return result;
        });

    }

    @Override
    public Map<String, Object> selectBoardList(int page, String search, String filter, String userGbnCd, String id) {


        Map<String, Object> result = new HashMap<>();

        int pageSize = 10;
        int offset = (page - 1) * pageSize;

        int totalCount = totalCount(search, userGbnCd, id);
        List<BoardDto> boardList = boardListPaging(offset, pageSize, search, filter, userGbnCd, id);

        PagingBtn paging = totalCount > 0
                ? new PagingBtn(totalCount, page, pageSize, 10)
                : new PagingBtn(0, 1, pageSize, 10);

        result.put("list", boardList);
        result.put("paging", paging);

        return result;
    }

    @Override
    public BoardDto selectBoard(Long bNo) {

        return returnData(() -> {
            BoardDto result;

            try {
                boardDao.updateBoardVCnt(bNo);
            } catch (BaseException e) {
                throw new BaseException(ResultType.ERROR, "저장 실패!", e);
            }

            result = boardDao.selectBoard(bNo);

            result.setComments(boardDao.getComments(bNo));

            return result;
        });
    }

    private List<BoardDto> boardListPaging(int offset, int limit, String search, String filter, String userGbnCd, String id) {

        return boardDao.selectBoardList(offset, limit, search, filter, userGbnCd, id);
    }


    private int totalCount(String search, String userGbnCd, String id) {
        return boardDao.getTotalCnt(search, userGbnCd, id);
    }

    @Override
    public List<CommentDto> selectComment(Long bNo) {
        return boardDao.getComments(bNo);
    }

    @Override
    public int deleteComment(Long cmNo) {

        return returnData(() -> {
            int result = 0;

            try {
                boardDao.deleteComment(cmNo);
            } catch (BaseException e) {
                throw new BaseException(ResultType.ERROR, "댓글 삭제 실패!", e);
            }

            return result;
        });

    }

    @Override
    public int saveComment(CommentDto comment) {
        return returnData(() -> {
            int result = 0;

            try {
                boardDao.saveComment(comment);
            } catch (BaseException e) {
                throw new BaseException(ResultType.ERROR, "댓글 저장 실패!", e);
            }

            return result;
        });
    }

    @Override
    public int deleteBoard(Long bNo) {

        int result = 0;

        try {
            result = boardDao.deleteBoard(bNo);
        } catch (BaseException e) {
            throw new BaseException(ResultType.ERROR, "게시글 삭제 실패!", e);
        }

        try {
            result = boardDao.deleteAllComment(bNo);
        } catch (BaseException e) {
            throw new BaseException(ResultType.ERROR, "게시글 댓글 삭제 실패!", e);
        }

        return result;
    }


}

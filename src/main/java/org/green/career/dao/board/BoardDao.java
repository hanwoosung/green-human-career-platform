package org.green.career.dao.board;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.green.career.dto.board.BoardDto;
import org.green.career.dto.board.CommentDto;

import java.util.List;

@Mapper
public interface BoardDao {

    int saveBoard(@Param("board") BoardDto board);

    int updateBoard(@Param("board") BoardDto board);

    List<BoardDto> selectBoardList(@Param("offset") int offset,
                                   @Param("limit") int limit,
                                   @Param("search") String search,
                                   @Param("filter") String filter,
                                   @Param("userGbnCd") String userGbnCd,
                                   @Param("id") String id);

    int getTotalCnt(@Param("search") String search,
                    @Param("userGbnCd") String userGbnCd,
                    @Param("id") String id);

    BoardDto selectBoard(@Param("bNo") Long bNo);

    void updateBoardVCnt(@Param("bNo") Long bNo);

    List<CommentDto> getComments(@Param("bNo") Long bNo);

    int deleteComment(@Param("cmNo") Long cmNo);

    int saveComment(@Param("comment") CommentDto comment);

    int deleteAllComment(Long bNo);

    int deleteBoard(Long bNo);
}

package org.green.career.dto.board;

import lombok.Data;

import java.time.LocalDate;

// 댓글 테이블
@Data
public class CommentDto {

    // 댓글 번호
    private Long cmNo;
    // 게시판
    private Long bNo;
    // 댓글 내용
    private String cmContent;
    // 삭제여부
    private String delYn;
    // 입력자
    private String instId;

    private String name;
    // 입력일시
    private LocalDate instDt;
    // 수정자
    private String updtId;
    // 수정일시
    private LocalDate updtDt;

}
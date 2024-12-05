package org.green.career.dto.board;


import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// 게시판 (문의, 공지) 테이블
@Data
public class BoardDto {
    // 게시판 아이디
    private Long bNo;
    // 조회수
    private Long vCnt;
    // 게시판 제목
    private String title;
    // 게시판 내용
    private String content;
    // 게시판구분(N: 공지, F:문의)
    private String bGbnCd;

    private String bGbnNm;
    // 답변여부(Y, N)
    private String bAnswerYn;
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

    private List<String> files = new ArrayList<>();

    private List<CommentDto> comments = new ArrayList<>();
}

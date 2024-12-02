package org.green.career.dto.likes;

import lombok.Data;

@Data
public class LikesDto {

    // 북마크, 스크랩 번호
    private Long lNo;
    // 아이디
    private String id;
    // 구분 (B: 북마크, S: 스크랩)
    private String lGbnCd;
    // 내가 할 채용공고, 회사ID
    private String cjNo;

    // 삭제, 저장 구분자
    private boolean flag;
}

package org.green.career.type;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 작성자: 한우성
 * 작성일: 2024-11-30
 * response 반환 타입 모음
 */
@Getter
@AllArgsConstructor
public enum ResultType {
    SUCCESS("200", "성공"),
    ERROR("000", "오류"),
    VALIDATION_ERROR("400", "유효성 검증 실패"),
    AUTH_ERROR("401", "권한 없음"),
    SERVER_ERROR("500", "서버 오류"),
    SESSION_ERROR("455", "세션 없음"),
    DUPLiCARTE_ERROR("1062", "중복 데이터 오류");

    private final String code;
    private final String desc;
}
package org.green.career.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultType {
    SUCCESS("200", "성공"),
    ERROR("000", "오류"),
    VALIDATION_ERROR("400", "유효성 검증 실패"),
    AUTH_ERROR("401", "권한 없음"),
    SERVER_ERROR("500", "서버 오류");
    private final String code;
    private final String desc;
}
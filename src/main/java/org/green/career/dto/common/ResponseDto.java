package org.green.career.dto.common;

import lombok.*;
import org.green.career.exception.BaseException;

/**
 * 작성자: 한우성
 * 작성일: 2024-11-30
 * API 응답 데이터를 감싸는 Dto 클래스.
 * 응답 데이터와 결과 상태(ResultObject)를 포함하며,
 * 성공, 데이터 반환, 또는 예외 상황을 처리하기 위한 메서드를 제공한다.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {

    private ResultObject result;

    private T data;

    public ResponseDto(ResultObject result) {
        this.result = result;
    }

    public ResponseDto(T data) {
        this.data = data;
    }

    public static <T> ResponseDto<T> ok() {
        return new ResponseDto<>(ResultObject.getSuccess());
    }

    public static <T> ResponseDto<T> ok(T data) {
        return new ResponseDto<>(ResultObject.getSuccess(), data);
    }

    public static <T> ResponseDto<T> response(T data) {
        return new ResponseDto<>(ResultObject.getSuccess(), data);
    }

    public ResponseDto(BaseException e) {
        this.result = new ResultObject(e);
    }
}
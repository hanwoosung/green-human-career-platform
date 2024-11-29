package org.green.career.dto;

import org.green.career.exception.BaseException;
import org.green.career.type.ResultType;

/**
 * 2024-11-29 한우성
 * 예외 및 결과 정보를 담는 데이터 객체 클래스.
 * 결과 코드, 설명, 메시지를 포함하여 응답 데이터로 사용
 */
public class ResultObject {

    public String code;
    public String desc;
    public String message;

    public ResultObject(ResultType resultType) {
        this.code = resultType.getCode();
        this.desc = resultType.getDesc();
    }

    public ResultObject(ResultType resultTypeCode, String message) {
        this.code = resultTypeCode.getCode();
        this.desc = message;
    }

    public ResultObject(BaseException e) {
        this.code = e.getCode();
        this.desc = e.getDesc();
        this.message = e.getMessage();

    }

    public static ResultObject getSuccess() {
        return new ResultObject(ResultType.SUCCESS);
    }

}